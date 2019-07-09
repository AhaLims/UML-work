package cdd.desk.model.card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cdd.desk.model.PlayGameCallBack;

import static cdd.desk.model.card.CardsType.card0;
//问题：对cards type进行排序行不行 就是判断牌能不能出--------是不是合法的 以及判断类型
/*  测试完成
 * CardsManager:对牌进行管理判断
 * 功能：
 * 1.jugdeType 判断出牌的类型 
 * 2.canDisplay 判断牌是否能被出
 * 3.order 对手牌进行排序  
 * 4.将List类型的CardIndex 打包成为deliveredCardsGroup类型的东西
 */




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
			else { return card0; }
		}

		//三带一模式
		if(len == 4)
		{
			if(list.get(0).getPoints() == list.get(1).getPoints() && list.get(0).getPoints()!=list.get(3).getPoints())//第一张和第二张牌相等,第一张牌与最后一张牌不相等，3334模式
			{
				if (list.get(2).getPoints()==list.get(1).getPoints()) {return CardsType.cards31;}//当第三张牌也等于第二张牌时，代表三张牌数值相等，所以是三张相等的牌，表示三带一
				else { return card0; }
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
					else { return card0; }
				}


			}
		}

		//五张牌
		if(len==5)
		{
			int count=0;//定义一个计数器用于判断花色相等的牌有多少张
			//同花顺 同花的五张牌
			for(int i = 1 ;i < 5;i++)
			{
				if (list.get(i).getColor() == list.get(i - 1).getColor())
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
		return card0;//不是上述的任何一种牌的类型
	}

	//比较牌组的大小
	//要牌型相同并且 value比较大才行
	//TODO 根据网站的规则完善这里的牌比较规则  以及判断牌
	public boolean isPermissible(deliveredCardsGroup previous,deliveredCardsGroup current,PlayGameCallBack playGameCallBack)
	{
		boolean validation = false;
		CardsType currentType = current.getType();
		CardsType previousType = previous.getType();
		if(currentType == card0)
		{
			playGameCallBack.onCardsNotValid("不可以这样出牌哦");
			return false;
		}
		if(currentType != previousType){
			playGameCallBack.onCardsNotValid("与上家的牌不匹配哦");
			return false;
		}
		switch (currentType){
			//单牌 对子 三张一样的牌 判定规则一样，都是看
			case cardSingle://单牌
			case cardsCouple://两张相等的对子
			case cards3://三张一样的
				int size1 = current.getCardsGroup().size();
				int size2 = previous.getCardsGroup().size();
				//比较相同的牌中权值最大的牌
				if(current.getCardsGroup().get(size1 - 1).getWeight() > previous.getCardsGroup().get(size2 - 1).getWeight()) {
					validation = true;
				}
				else{
					playGameCallBack.onCardsNotValid("牌太小啦 换一种出牌方式吧");
					return false;
				}
				break;
			case cards31://三带一
			case cards41://四带一
			case cardsThs://同花顺
				/*int size1 = current.getCardsGroup().size();
				int size2 = previous.getCardsGroup().size();
				//比较相同的牌中权值最大的牌
				if(currentType == previousType && current.getCardsGroup().get(size1 - 1).getWeight() > previous.getCardsGroup().get(size2 - 1).getWeight())
					validation = true;
				break;*/

				playGameCallBack.onCardsNotValid("暂时不支持的牌型 后面完善了规则再补充");
				return false;
		}
		return true;
	}


	//使用Collections排序非常简单，
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