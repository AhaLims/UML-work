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
	//表名
	public static final String TABLE = "Player";

	//表的属性
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";

	public String player_name;
	public double score;

	public Player(){}

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