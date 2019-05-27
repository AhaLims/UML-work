package game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import card.Card;

import card.*;

import role.*;
/*
 * ������ҵ� �� ������ �����߼� �Ƿ��������
 * ���� ���� ������
 * GameInit  ÿһ����Ϸ�Ŀ�ʼ ��һЩ��Ϸ�ĳ�ʼ������ undo 
 * inGame ��Ϸ�� undo
 * nextTurn() ��һ�ֳ��� undo
 * isEnd() �ж���Ϸ�ǲ��ǽ����� 
 * changeTurn() �ı�turn ���¼ҳ���
 */
//Ӧ��ӵ�����ֽ�ɫ?
public  class GameManager{
	public static GameManager gameManager;//�ǻ��Զ���ʼ��Ϊ��ָ��İ�....
	final static int CardAmount = 52;
	public 
	RoleManager[] role;
	int turn;
	public GameManager getGameManager()//����ģʽ
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
		turn = 0;//Ĭ�ϴ�player ��ʼ����
	}

	public void GameInit()
	{
		//��ʼ����
		Card[] AllCards =  new Card[CardAmount];
		for(int i = 0; i < 13; i++)
		{
				AllCards[i * 4] = new Card(i + 1,CardColor.Diamond);
				AllCards[i * 4 + 1] = new Card(i + 1,CardColor.Club);
				AllCards[i * 4 + 2] = new Card(i + 1,CardColor.Heart);
				AllCards[i * 4 + 3] = new Card(i + 1,CardColor.Spade);
		}
		
		//����˳�� ͨ�������Ƶķ�ʽ����
		for(int i = 0; i < 200; i++){
			Random random = new Random();
			int a = random.nextInt(CardAmount) + 1;
			int b = random.nextInt(CardAmount) + 1;
			Card k = AllCards[a];
			AllCards[a] = AllCards[b];
			AllCards[b] = k;
		}
		//����
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
	public void inGame()//��Ϸ�е���ѭ��
	{
		GameInit();
		while(!isEnd())
		{
			//��ͣ�Ľ�����һ�ֵ��ж� ����Ŀǰ��û�����ô��
			//role[turn].xxxxx();//role[turn]����ĳЩ����...???�������ﻹû�ж����¼�Ѽ....
			changeTurn();
		}
	}
	//��һ�ֳ���
	private void nextTurn()
	{
		//��֪��Ҫ��ʲô��....
	}
	//�ж���Ϸ�Ƿ����
	private boolean isEnd()
	{
		for(int i = 0;i<4;i++)
		{
			if(role[i].isEnd() == true) return true;
		}
		return false;
	}
	//�ı�turn ���¼ҳ���
	private int changeTurn(){
		turn = (turn+1) % 4;
		return turn;
	}
	
}

