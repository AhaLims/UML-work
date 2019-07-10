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
	/*//表名
	public static final String TABLE = "Player";

	//表的属性
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";*/

	//TODO 记得这里要改 应该是私有属性 但是有getter和setter
	//TODO 以及 score应该是整型的
	private String player_name;
	private int score;

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
	public String getPlayerName(){
		return player_name;
	}
	public void setPlayerName(String Name){
		player_name = Name;
	}
	public int getScore(){
		return  score;
	}
	public void setScore(int s){
		score = s;
	}


}