package game;

import java.util.List;

import card.Card;
import card.CardsGroup;
import card.PairCardsGroup;
import card.deliveredCardsGroup;
import role.*;

public class Game{
	private Judger judger;
	private PairCardsGroup AllCards;
	//CardsManager cardsManager;
	private deliveredCardsGroup LatestCards;//最新的 出在牌桌上面的牌
	private int currentTurn;
	private Role[] roles;
	private boolean[] IsLatestShow;//用来判断最新一轮是否出了牌
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
	
	public void GameLogic() {
		
	}
	//出牌的函数
	//传入当前的index
	//这个函数又presentation来调用
	
	
	
	public boolean RoleDeliverCard(int index,List<Integer> list) {
		deliveredCardsGroup currentCardsGroup = roles[index].selectCards(list);
		//将转换成deleiverCardsGroup类型的
		if(IsFirstHand(index) == true)//s先手
		{
			if(currentCardsGroup == null)//TODO 这里是说明先手不出牌 是不合法的
			{
				return false;
				//应该返回给前端提示 说明这是不合法的行为
			}
			//出牌
			else {
				if(judger.isPermissible(null,currentCardsGroup) == true)
					return true;
				//TODO 合法的出牌 这个时候应该考虑怎么更新牌
			}
		}
		else {//后手 需要参考上家的牌
			if( judger.isPermissible(LatestCards,currentCardsGroup) == true)
			{//后手出牌合法
				//更新牌
				return false;
			}
		}
		return false;
		
	}

	
	public void InitGame() {
		AllCards.shuffleCards();//洗牌
		licensingCards();//发牌
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
	public void licensingCards() {
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
				break;
			}
		}
	}

	public void inGame() {
		//进行游戏的循环
		InitGame();
	}
	public int getNextTurn() {
		return (currentTurn + 1) % 4;
	}
	
	
}