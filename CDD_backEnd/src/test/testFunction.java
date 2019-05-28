package test;
import java.util.ArrayList;
import java.util.List;

import card.*;
import game.GameManager;


public class testFunction{
    public static void main(String[] args) {
    	/*testCardPackage testCard = new testCardPackage();
    	testCard.addCard(1, 1);
    	testCard.addCard(2, 3);
    	testCard.addCard(5, 4);
    	testCard.addCard(5, 3);
    	testCard.addCard(5, 1);
    	testCard.addCard(5, 2);
    	testCard.addCard(13, 1);
    	System.out.println("before order:\n");
    	testCard.showAllCard();
    	System.out.println("after order:\n");
    	testCard.orderCard();
    	testCard.showAllCard();*/
    	GameManager gameManager = GameManager.getGameManager();
    	gameManager.inGame();
    }
    public void testCardFunc() {
    	testCardPackage testCard = new testCardPackage();
    	testCard.addCard(1, 1);
    	testCard.addCard(2, 3);
    	testCard.addCard(5, 4);
    	testCard.addCard(5, 3);
    	testCard.addCard(5, 1);
    	testCard.addCard(5, 2);
    	testCard.addCard(13, 1);
    	System.out.println("before order:\n");
    	testCard.showAllCard();
    	System.out.println("after order:\n");
    	testCard.orderCard();
    	testCard.showAllCard();
    }

}
class testCardPackage{
	public List<Card> list;
    public testCardPackage(){
    	list = new ArrayList<Card>();
    }
    public void addCard(int point ,int _color)
    {
    	CardColor color;
    	switch(_color) {
    	case 1: color = CardColor.Diamond;
    	break;
    	case 2: color = CardColor.Club;
    	break;
    	case 3: color = CardColor.Heart;
    	break;
    	case 4: color = CardColor.Spade;
    	break;
    	default:color = CardColor.Club;
    	}
    	Card card = new Card(point,color);
    	list.add(card);
    }
    public void orderCard() {
    	CardsManager.order(list);
    }
    public void showAllCard() {
    	int len = list.size();
    	for(int i = 0; i < len; i++)
    	{
    		System.out.print("number " + i + " is :");
    		list.get(i).getPoints();
    		System.out.print(" ");
    		list.get(i).getCardColor();
    		System.out.print("\n");
    	}
    }
}

