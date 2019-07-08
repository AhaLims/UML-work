package card;

import java.util.ArrayList;
import java.util.List;
//继承自CardsGroup的 是整个游戏有的一副牌
public class PairCardsGroup extends CardsGroup{
	private static PairCardsGroup AllCards;
	
	public static PairCardsGroup getPairOfCards(){
		if(AllCards == null)
		{
			List<Card> cardList = new ArrayList<Card>();
			for(int i = 0;i < 13; i++)
			{
				cardList.add(new Card(i + 1,CardColor.Diamond));
				cardList.add(new Card(i + 1,CardColor.Club));
				cardList.add(new Card(i + 1,CardColor.Heart));
				cardList.add(new Card(i + 1,CardColor.Spade));
			}
			AllCards = new PairCardsGroup(cardList);
		}
		return AllCards;
	}
	
	public PairCardsGroup(List<Card> cardList) {
		super(cardList);
	}
	
	public void shuffleCards(int time) {
		for(int i = 0;i < time; i++)
		{
			//掉换牌的位置
			//暂时不知道 list要怎么洗牌.....
		}
	}
	public void shuffleCards() {
		shuffleCards(1000);
	}


	
	/*
	 *  just for debug
	 */



}