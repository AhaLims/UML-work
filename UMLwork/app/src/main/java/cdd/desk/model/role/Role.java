package cdd.desk.model.role;
import java.util.List;

import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardsGroup;
import cdd.desk.model.card.CardsManager;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.card.handCardsGroup;
import cdd.desk.model.game.Game;


public class Role {
	CardsManager cardsManager;
	handCardsGroup CurrentCards;
	//TODO 暂时可能没啥用？
	//CardsGroup LasterCards;
	int score;
	int number;//该局游戏中的编号（也就是第几个出牌）
	protected String player_name;
	//String name;
	//只是为了测试
	public void showDetail() {
		CurrentCards.showDetail();
	}


	public Role() {
		cardsManager = CardsManager.getCardsManager();
		score = 0;
		//name = "testName";
		CurrentCards = new handCardsGroup();
	}



	//在游戏初始阶段 获得单张牌
	public void getSingleCards(Card card) {
		CurrentCards.addCard(card);
	}

	//没用到所以注释了
	//减掉之前出出去的牌
	//deliveredCards 是出出去的牌 要从集合中减去这部分的牌
	/*public void updateCards(CardsGroup deliveredCards) {
		//int len = deliverdCards;
		CurrentCards.deleteCardsGroup(deliveredCards);
	}*/

	//判断是否出完牌了
	/*public boolean ifFinish() {
		if (CurrentCards.cardsAmount() == 0) return true;
		return false;
	}*/

	//deliveredCardsGroup dc  是从交互中获得的 玩家出的牌（已经判断好了牌的value与牌的类型） 接下来交给游戏区检查牌的合法性
	public boolean findCard(int weight) {
		if (CurrentCards.canFindCard(weight) != -1) return true;
		return false;
	}

	//更新牌  就是删除牌的过程
	public void refreshCardsGroup(CardsGroup deletedCardsGroup) {
		CurrentCards.deleteCardsGroup(deletedCardsGroup);
	}

	//将List类型打包
	public deliveredCardsGroup selectCards(List<Integer> cardsIndex) {
		//怎么把cardsIndex组装成 deliveredCardsGroup(List<Card> c) 
		deliveredCardsGroup dc = new deliveredCardsGroup();
		int len = cardsIndex.size();
		Card card;
		for (int i = 0; i < len; i++) {
			card = CurrentCards.getCardByIndex(cardsIndex.get(i).intValue());
			dc.addCard(card);
		}
		return dc;
	}

	////TODO 空函数设置。。并不合理 也许并不合理
	public deliveredCardsGroup deliver(deliveredCardsGroup previous) {
		return null;
	}

	public void setNumber(int n) {
		number = n;
	}

	public boolean win() {//没有牌了
		if (CurrentCards.cardsAmount() == 0) return true;
		return false;
	}

	public handCardsGroup getHandCards(){
	    return CurrentCards;
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
	
