package test;
import java.util.ArrayList;
import java.util.List;

import card.*;
import game.Game;
import role.Role;


public class testFunction{
	public static void main(String[] args) {
		Game game = new Game();
		game.licensingCards();
		Role test = new Role(game);
		/* 牌的 增加 删除测试
		CardsGroup deletedCards = new CardsGroup();
		Card temp = new Card(1,CardColor.Club);
		deletedCards.addCard(temp);
		test.getSingleCards(temp);
		temp = new Card(1,CardColor.Diamond);
		test.getSingleCards(temp);
		temp = new Card(1,CardColor.Heart);
		test.getSingleCards(temp);
		temp = new Card(1,CardColor.Spade);
		test.getSingleCards(temp);
		test.showDetail();
		
		test.updateCards(deletedCards);
		test.showDetail();*/
		
	}
}