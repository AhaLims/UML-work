package cdd.desk.model.role;

import java.util.ArrayList;
import java.util.List;
import cdd.desk.model.card.Card;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.game.Game;

//public class Player{
//	public CardsGroup selectCards( List<int> cardIndex) {
		
//	}
//}
public class Player extends Role{
	//TODO 记得把这个移走


	//TODO 记得这里要改 应该是私有属性 但是有getter和setter
	//TODO 以及 score应该是整型的



	public Player(String name){
		player_name = name;
	}

	//给对应的index 可以找到用户选的一组牌 并且可以组装成deliveredCardsGroup 类型的东西
	public deliveredCardsGroup selectCards(List<Integer> cardsIndex) {
		
		deliveredCardsGroup dc = new deliveredCardsGroup();
		int len = cardsIndex.size();
		Card card;
		for(int i = 0; i < len; i++) {
			card = CurrentCards.getCardByIndex(cardsIndex.get(i).intValue());
			dc.addCard(card);
		}
		return dc;
	}



}