package game;

import card.Card;
import card.CardsGroup;
import card.PairCardsGroup;
import role.*;

public class Game{
	private Judger judger;
	private PairCardsGroup AllCards;
	//CardsManager cardsManager;
	private CardsGroup LatestCards;//最新的 出在牌桌上面的牌
	private boolean firstGameTurn;
	private int LatestShowCardsID;
	private int currentTurn;
	private Role[] roles;
	//private int[] nextTurn;
	public Game() {
		judger = new Judger();
		//nextTurn = new int[4];
		AllCards = PairCardsGroup.getPairOfCards();
		roles = new Role[4];
		roles[0] = new Player(this);
		roles[1] = new Player(this);
		roles[2] = new Player(this);
		roles[3] = new Player(this);
		LatestShowCardsID = -1;//一开始没有人出过牌 因此初始化为-1
		//roles[1] = new Robot(this);
		//roles[2] = new Robot(this);
		//roles[3] = new Robot(this);
		
	}
	//暂时不知道写什么	
	//好像没有暂停游戏这个用例？
	public void GameLogic() {
		
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