//管理手牌 并更新牌 判断牌是不是出完了
//每次出牌之后都需要调用

//貌似这个部分放到 Rolemanager去了
package cdd.desk.model.card;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class CardsGroup{
	protected List<Card> card;
	protected CardsManager cardsManager;
	/*
	 * 
	 * 
	 * use only for test
	 */
	public void showDetail() {
		int len = card.size();
		System.out.println("-----------show detail of card(weight)---------------");
		for(int i = 0;i < len ; i++)
		{
			Card temp = card.get(i);
			int weight = temp.getWeight();
			System.out.println(weight);
		}
		System.out.println("-----------end----------------------------------------");
	}
	
	/*
	 * end
	 * 
	 */

	public List<Card> getCardsGroup(){//返回目前所有的手牌 List类型的
		return card;
	}


	public void sort() {
		cardsManager.orderCards(card);
	}
	public CardsType getType() {
		return cardsManager.jugdeType(card);
	}

	public CardsGroup(List<Card> c) {
		if(c != null)
			this.card = c;
		else
			this.card = new ArrayList<Card>();
		cardsManager = CardsManager.getCardsManager();
	}
	public CardsGroup() {
		this.card = new ArrayList<Card>();
		cardsManager = CardsManager.getCardsManager();
	}
	
	//寻找单张牌是否在这个cardGroup中
	public int canFindCard(int point,CardColor color) {
		int len = this.card.size();
		for (int i = 0;i < len; i++) {
			//point 和 color 匹配上了 则说明在这个cardGroup 中有这张牌
			if(this.card.get(i).getPoints() == point && this.card.get(i).getColor() == color)
				return i;
		}
		return -1;
	}
	
	public int canFindCard(int weight) {
		int len = this.card.size();
		for (int i = 0;i < len; i++) {
			//point 和 color 匹配上了 则说明在这个cardGroup 中有这张牌
			if(this.card.get(i).getWeight() == weight)
				return i;
		}
		return -1;
	}
	
	public void addCard(Card c) {
		this.card.add(c);
	}
	private void deleteSingleCard(int point,CardColor color) {
		int index = canFindCard(point,color);
		if(index == -1)return;//这个牌不在这里 所以找不到
		deleteSingleCard(index);
	}
	
	private void deleteSingleCard(int index) {
		this.card.remove(index);//删除对应下标的牌
	}
	
	public void deleteCardsGroup(CardsGroup cg)//cg是需要删除的牌组
	{
		int totalDeleteAmount = cg.cardsAmount();
		for(int i = 0;i < totalDeleteAmount;i++)//遍历 删除牌
		{
			Card tempCard = cg.getCardByIndex(i);
			//System.out.println(i);
			deleteSingleCard(tempCard.getPoints(), tempCard.getColor());
		}
	}
	public int cardsAmount() {
		return card.size();
	}
	public Card getCardByIndex(int index) {
		int size = card.size();
		if(index < size)
		{
			return card.get(index);
		}
		return null;
	}
	public static void orderCards(List<Card> list){
		Collections.sort(list,new Comparator<Card>() {//实现了接口中的compare函数
			//这里是java的语法糖
			//@Override
			public int compare(Card card1, Card card2) {

				return card1.compareTo(card2);
			}	
		}
	);
	}
	//将presenter所传进来的 选中的牌的index数组转换为 deliveredCardsGroup的牌
	//貌似没用上 所以先注释了
	/*public deliveredCardsGroup packCardsGroup(List<Integer> list) {
		deliveredCardsGroup DeliveredCardsGroup = new deliveredCardsGroup();
		for(int i = 0;i < list.size();i++) {
			int index = list.get(i).intValue();
			Card c = card.get(index);//根据下标获取牌
			DeliveredCardsGroup.addCard(c);//将加牌
		}
		return DeliveredCardsGroup;
<<<<<<< HEAD
	}*/
	
=======
	}

	public void clear() {
		card.clear();
	}
>>>>>>> a23d75564082e9e9474c76af1dbf2c9ebbb7fa23

}