package cdd.desk.model.card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//问题：对cards type进行排序行不行 就是判断牌能不能出--------是不是合法的 以及判断类型
/*  测试完成
 * CardsManager:对牌进行管理判断
 * 功能：
 * 1.jugdeType 判断出牌的类型 
 * 2.canDisplay 判断牌是否能被出
 * 3.order 对手牌进行排序  
 * 4.将List类型的CardIndex 打包成为deliveredCardsGroup类型的东西
 */



//应该都是静态的方法....??
//---------------------“比较牌”的方法应该单独抽象成类----------------------------//
//可以这样做  接口：比较牌的接口 然后有一个函数xxx(card1,card2) return true/false;
//用单实例模式---------------------------//
public class CardsManager {
	private static CardsManager cardsManager;
	public static CardsManager getCardsManager() {
		if(cardsManager == null)
		{
			cardsManager = new CardsManager();
		}
		return cardsManager;
		
	}
	private CardsManager() {
		
	}
	/*
	 * 参数：牌数组
	 * 功能：判断牌的类型 因为之前排序过所以比较好判断
	 * 输出 CardsType
	 */
	//根据收到的牌组 返回牌组的权值（现在只是所有值简单的相加
	public int getCardsGroupValue(List<Card> list)
	{
		int len = list.size();
		int totalWeight = 0;
		for(int i = 0;i<len;i++)
		{
			totalWeight += list.get(i).getWeight();
		}
		return totalWeight;
		
	}
	//判断牌的类型
	public CardsType jugdeType(List<Card> list) {
		int len = list.size();
		
		//只可能是：cardSingle,//单牌。
		//cardsCouple,//对子。
		//cards3,//3不带。
		if(len <= 4)
		{
			//如果第一个和最后个相同，说明全部相同
			if(len > 0 && list.get(0).getPoints() == list.get(len - 1).getPoints())
			{
				switch (len) {
				case 1:
					return CardsType.cardSingle;//单张牌
				case 2:
					return CardsType.cardsCouple;//对子
				case 3:
					return CardsType.cards3;//三张相同的牌
				case 4:
					return CardsType.cards4;//四张相同的牌
				}
			}
	}
		//四张牌 但是最后一张牌与第一张牌不一样 所以是三带一
		if(len == 4 && list.get(0).getPoints() != list.get(len - 1).getPoints())
			return CardsType.cards31;
		//后面可以再根据CardsType来增加 
		return CardsType.card0;//不是上述的任何一种牌的类型
	}
	/*
	 * 描述：在上家的牌与出牌者想要选择的牌的类型一致的情况下，比较牌组的大小以确定出牌者是否能出牌
	 * 参数：牌的类型 上家的牌组 目前的牌组
	 * 功能：判断是否能出牌
	 * 备注：目前只支持在CardsType相同的情况下出牌
	 * 		现在默认目前所有的牌的类型都按照这样的规则来比较
			ps当然我觉得这样做是不行的
	 */
	
	/*public static boolean canDisplay(CardsType type,
			List<Card> previousList,
			List<Card> presentList){
		switch(type) {
		case cardSingle:
		case cardsCouple:
		case cards3:
		case cards4:
		case cardsSequence:
		case cards31:
			if(presentList.get(0).compareTo(previousList.get(0)) > 0)
				return true;
			else return false;
		default:
			return false;
		}
	}*/
	//6.28 按照新的逻辑 canDisplay暂时没用
	

	//能直接在Card类中重载某个参数 实现函数的重载吗......? 这里就直接调用 多好...
	//不要写在这里...太丑了
	//使用Collections排序非常简单，\
	//我们只需要把实现了Comparable接口的类传入里面调用一下Collections.sort() 
	//方法就可以对其进行排序了。
	/*
	 * 参数：牌的数组
	 * 功能：对牌进行排序
	 * 输出：无
	 */
	public void orderCards(List<Card> list){
		
		Collections.sort(list,new Comparator<Card>() {//实现了接口中的compare函数
			//这里是java某种扭曲的(划掉)语法糖
			//@Override
			public int compare(Card card1, Card card2) {
				return card1.compareTo(card2);
			}	
		}
	);
	}

}