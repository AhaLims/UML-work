package role;

import java.util.List;

import card.Card;
import card.CardsGroup;
import card.deliveredCardsGroup;
import game.Game;

public class Robot extends Role{
	public Robot(Game g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	//根据上家的牌决定出牌的策略
	public deliveredCardsGroup deliver(deliveredCardsGroup previous) {
		List<Card> list =  CurrentCards.getCardsGroup();
		deliveredCardsGroup dc = new deliveredCardsGroup();//暂时不出牌
		if(previous.hasCards() == false) {
			//先手出牌
			//暂时默认出最小的那张牌
			//最小的牌
			Card c = list.get(0);
			dc.addCard(c);
		}
		
		return dc;
	}

}