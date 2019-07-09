package cdd.desk.model.game;

import java.util.List;

import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
import cdd.desk.model.card.PairCardsGroup;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.role.Player;
import cdd.desk.model.role.Role;



public class Game{
	private Judger judger;
	private PairCardsGroup AllCards;
	//CardsManager cardsManager;
	private deliveredCardsGroup LatestCards;//最新的 出在牌桌上面的牌
	private int currentTurn;
	public int firstTurn;//其实不应该public的....后面再改吧
	private int turnTime;//轮数
	private Role[] roles;
	private boolean[] IsLatestShow;//用来判断最新是否出了牌
	//private int[] nextTurn;
	public Game() {
		judger = new Judger();
		//nextTurn = new int[4];
		AllCards = PairCardsGroup.getPairOfCards();
		roles = new Role[4];
		IsLatestShow = new boolean[4];
		for(int i = 0;i < 4; i++) {
			roles[i] = new Player(this);
			IsLatestShow[i] = false;//一开始大家都没有出牌
		}

		//roles[1] = new Robot(this);
		//roles[2] = new Robot(this);
		//roles[3] = new Robot(this);

	}
	private boolean IsFirstHand(int index) {//用来判断是不是先手
		for(int i = 0;i < 4;i++) {
			if (i == index)continue;
			else {
				if(IsLatestShow[i] == false)return false;
			}
		}
		return true;
	}


	//出牌的函数
	//传入当前的index.....
	//以及准备出的牌
	//返回boolean值代表出的牌是不是合法的
	//也许应该返回String 比较合适？ 如果是合法 则String为空     //不合法 String为相应的错误提示

	public boolean RoleDeliverCard(int index,List<Integer> list) {
		boolean validation = false;
		currentTurn = index;
		if(currentTurn == firstTurn)
		{
			turnTime++;
		}//进行轮的更新

		deliveredCardsGroup currentCardsGroup = roles[index].selectCards(list);
		//将转换成deleiverCardsGroup类型的
		if(firstTurn == index && turnTime == 1)//第一轮的先手
		{
			LatestCards = new deliveredCardsGroup();//先手 LatestCards 应该是新new的
			//必须有方块三
			if(currentCardsGroup.canFindCard(3, CardColor.Diamond) != -1)
			{
				roles[index].refreshCardsGroup(currentCardsGroup);//更新牌
				validation = true;
			}
		}
		if(IsFirstHand(index) == true)//非第一轮的先手
		{
			LatestCards = new deliveredCardsGroup();//先手 LatestCards 应该是新new的
			if(currentCardsGroup.hasCards() == false)//TODO 这里是说明先手不出牌 是不合法的
			{
				validation = false;
				//应该返回给前端提示 说明这是不合法的行为
			}
			//有选中的牌了
			else {
				if(judger.isPermissible(null,currentCardsGroup) == true)
				{
					roles[index].refreshCardsGroup(currentCardsGroup);//更新牌
					validation = true;
				}
				//TODO 合法的出牌 这个时候应该考虑怎么更新牌
			}
		}
		else {//后手 需要参考上家的牌
			if( judger.isPermissible(LatestCards,currentCardsGroup) == true)
			{	//后手出牌合法
				//更新牌
				roles[index].refreshCardsGroup(currentCardsGroup);//更新牌
				validation = true;
			}
		}

		System.out.print("现在游戏进行到第 ");
		System.out.print(turnTime);
		System.out.println("轮");
		System.out.print("当前出牌的Index为");
		System.out.println(index);
		System.out.print("他选择");
		if(validation == true)
		{
			System.out.print("【出牌】，并且为\n");
			currentCardsGroup.showDetail();//展示牌组细节的数组
			LatestCards = currentCardsGroup;//更新牌桌上的最后一组牌
		}

		else
			System.out.println("不出牌");

		return validation;
	}


	public void InitGame() {
		AllCards.shuffleCards();//洗牌
		licensingCards();//发牌
		turnTime = 0;
	}
	//更改这个就可以让游戏顺时针or逆时针
	/*public void InitTurn(int start) {
		currentTurn = start;//实际上应该改成找到方块三的role
		for(int i = 0;i < 4;i++) {
			nextTurn[(start + i) % 4] = (1 + i)%4;
		}
		nextTurn[start] = 1;
		nextTurn[(start + 1)] = 2;
		nextTurn[2] = 3;
		nextTurn[3] = 0;
	}*/
	//发牌
	private void licensingCards() {
		for(int i = 0;i < 52;i++)
		{
			Card card = AllCards.getCardByIndex(i);
			roles[i % 4].getSingleCards(card);
		}
		//for(int i = 0;i<4;i++) {
		//	roles[i].showDetail();
		//}
		//AllCards.showDetail();
		//找出拥有方块3role的ID   方块3的weight为4
		for(int i = 0;i < 4;i++) {
			if(roles[i].findCard(4) == true)
			{
				currentTurn = i;
				firstTurn = i;
				break;
			}
		}
		//确定顺序
		for(int i = 0;i < 4;i++) {
			roles[ (currentTurn + i) % 4].setNumber(i);
		}
	}
	//由presenter来调用这个函数
	//两个参数分别为:前端传来的牌的数组 以及presenter自己
	public void turn(List<Integer> list) {
		turnTime++;
		deliveredCardsGroup currentCardsGroup = roles[0].selectCards(list);

	}
	//虽然好像怪怪的....不应该调用...?
	public int end() {//返回胜利者的编号 或者-1
		for(int i = 0;i<4;i++)
		{
			if(roles[i].win()) {
				System.out.println("游戏结束");
				return i;
			}
		}
		return -1;
	}
	private int getNextTurn() {
		return (currentTurn + 1) % 4;
	}


}