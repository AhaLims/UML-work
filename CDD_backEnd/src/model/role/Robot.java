package model.role;

import java.util.List;

import model.card.Card;
import model.card.deliveredCardsGroup;
import model.game.Game;



public class Robot extends Role{
	public Robot(Game g) {
		super(g);
		// TODO Auto-generated constructor stub
	}
	//根据上家的牌决定出牌的策略
	public deliveredCardsGroup selectCards(deliveredCardsGroup previous) {
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