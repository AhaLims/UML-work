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
	//判断牌的类型
	public CardsType jugdeType(List<Card> list) {
		int len = list.size();

		//单张牌
		if(len==1)
		{return CardsType.cardSingle;}

		//对子
		if(len==2 && (list.get(0).getPoints() == list.get(1).getPoints()))
		{ return CardsType.cardsCouple;}


		//三张相等
		if(len==3)
		{
			int count=0;//设定一个计数器用来判断几张牌之间相等的次数
			for(int i=1;i<3;i++)
			{
				if (list.get(i).getPoints()==list.get(i-1).getPoints())
				{
					count++;
				}//每当一个牌的数值等于它前一个牌的数值，就代表它们两张牌数值相等，计数器加一
			}
			if(count==2){return CardsType.cards3;}//当计数器达到2时，代表三张牌数值相等，所以时三张相等的牌
			else { return CardsType.card0; }
		}

		//三带一模式
		if(len == 4)
		{
			if(list.get(0).getPoints() == list.get(1).getPoints() && list.get(0).getPoints()!=list.get(3).getPoints())//第一张和第二张牌相等,第一张牌与最后一张牌不相等，3334模式
			{
				if (list.get(2).getPoints()==list.get(1).getPoints()) {return CardsType.cards31;}//当第三张牌也等于第二张牌时，代表三张牌数值相等，所以是三张相等的牌，表示三带一
				else { return CardsType.card0; }
			}

			else
			{
				if (list.get(0).getPoints() != list.get(1).getPoints() && list.get(0).getPoints()!=list.get(3).getPoints())//第一张牌和第二张牌不相等，第一张牌与最后一张牌不相等，3444模式
				{
					int count=0;//设定一个计数器用来判断几张牌之间相等的次数
					for (int i = 2; i < 4;i++)
					{
						if (list.get(i).getPoints() == list.get(i - 1).getPoints()) {
							count++;
						}//每当一个牌的数值等于它前一个牌的数值，就代表它们两张牌数值相等，计数器加一
					}
					if (count == 2) { return CardsType.cards31; }//当计数器达到2时，代表三张牌数值相等，所以是三张相等的牌，表示三带一
					else { return CardsType.card0; }
				}


			}
		}

		//五张牌
		if(len==5)
		{
			int count=0;//定义一个计数器用于判断花色相等的牌有多少张
			for(int i = 1 ;i < 5;i++)
			{
				if (list.get(i).getPoints() == list.get(i - 1).getPoints())
				{ count++; }//与上面判断三张相等牌思路相同
			}
			if(count==4)
			{
				return CardsType.cardsThs;
			}//当计数器值为4时，表示5张牌的color都是相等的，因此是同花顺

			else//如果经过上面的判断后确定不是同花，那么接下来就判断是否是四带一，分33334模式和34444模式
			{
				if(list.get(0).getPoints() == list.get(1).getPoints() && list.get(0).getPoints()!=list.get(4).getPoints())//如果第一张牌和第二张牌数值相等，并且第一张牌和最后一张牌不相等，则是33334模式
				{
					int count1=0;
					for(int i = 1;i < 4; i++)
					{
						if(list.get(i).getPoints()==list.get(i-1).getPoints())
						{count1++;}//与上面判断三张相等牌思路相同
					}
					if(count1 == 3){return CardsType.cards41;}
				}

				if(list.get(0).getPoints()!=list.get(1).getPoints()&& list.get(0).getPoints()!=list.get(4).getPoints())//如果第一张牌和第二张牌数值不相等，则是34444模式
				{
					int count2=0;
					for(int i=2;i<5;i++)
					{
						if(list.get(i).getPoints()==list.get(i-1).getPoints())
						{count2++;}//与上面判断三张相等牌思路相同
					}
					if(count2 == 3)
					{
						return CardsType.cards41;
					}
				}
			}

		}

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