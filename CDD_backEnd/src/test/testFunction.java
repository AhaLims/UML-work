package test;
import java.util.ArrayList;
import java.util.List;

import model.card.Card;
import model.card.CardColor;
import model.card.CardsGroup;
import model.game.Game;
import model.role.Role;



public class testFunction{
	public static void main(String[] args) {
		Game game = new Game();
		game.InitGame();
		//进行单元测试 模拟出牌的过程....
		//RoleDeliverCard
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);//每次都默认出单张牌
		int i = 0;
		while(game.end() == -1)//游戏没有结束
		{
			game.RoleDeliverCard( (game.firstTurn + i) % 4,list);
			i++;
		}
		
		
	}
	
	/* 牌的 增加 删除测试
	;*/
	void testCard(Role test) {
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
		test.showDetail();
	}
}