package game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import card.Card;

import card.*;

import role.*;
/*
 * 处理玩家的 牌 更新牌 出牌逻辑 是否出完了牌
 * 发牌 出牌 更新牌
 * GameInit  每一局游戏的开始 做一些游戏的初始化工作 undo 
 * inGame 游戏中 undo
 * nextTurn() 下一轮出牌 undo
 * isEnd() 判断游戏是不是结束了 
 * changeTurn() 改变turn 换下家出牌
 */
//应该拥有四种角色?
public  class GameManager{
	public static GameManager gameManager;//是会自动初始化为空指针的吧....
	final static int CardAmount = 52;
	public 
	RoleManager[] role;
	int turn;
	public GameManager getGameManager()//单例模式
	{
		if(gameManager == null)
		{
			gameManager = new GameManager();
		}
		return gameManager;
	}

	private GameManager()
	{
		/*PlayerManager player = new PlayerManager();
		RobotManager robot1 = new RobotManager();
		RobotManager robot2 = new RobotManager();*/
		role = new RoleManager[4];
		role[0] = new PlayerManager();
		for(int i = 1;i<4;i++)
		{
			role[i] = new RobotManager();
		}
		turn = 0;//默认从player 开始出牌
	}

	public void GameInit()
	{
		//初始化牌
		Card[] AllCards =  new Card[CardAmount];
		for(int i = 0; i < 13; i++)
		{
				AllCards[i * 4] = new Card(i + 1,CardColor.Diamond);
				AllCards[i * 4 + 1] = new Card(i + 1,CardColor.Club);
				AllCards[i * 4 + 2] = new Card(i + 1,CardColor.Heart);
				AllCards[i * 4 + 3] = new Card(i + 1,CardColor.Spade);
		}
		
		//打乱顺序 通过交换牌的方式打乱
		for(int i = 0; i < 200; i++){
			Random random = new Random();
			int a = random.nextInt(CardAmount) + 1;
			int b = random.nextInt(CardAmount) + 1;
			Card k = AllCards[a];
			AllCards[a] = AllCards[b];
			AllCards[b] = k;
		}
		//发牌
		for(int i = 0;i < CardAmount;i++)
		{
			role[i % 3].getCards().add(AllCards[i]);
		}
		for(int i = 0; i < 3;i++)
		{
			role[i].order();
		}
		turn = 0;
	}
	public void inGame()//游戏中的主循环
	{
		GameInit();
		while(!isEnd())
		{
			//不停的进行下一轮的判断 这里目前还没想好怎么做
			//role[turn].xxxxx();//role[turn]进行某些动作...???但你这里还没判断上下家鸭....
			changeTurn();
		}
	}
	//下一轮出牌
	private void nextTurn()
	{
		//不知道要干什么的....
	}
	//判断游戏是否结束
	private boolean isEnd()
	{
		for(int i = 0;i<4;i++)
		{
			if(role[i].isEnd() == true) return true;
		}
		return false;
	}
	//改变turn 换下家出牌
	private int changeTurn(){
		turn = (turn+1) % 4;
		return turn;
	}
	
}

