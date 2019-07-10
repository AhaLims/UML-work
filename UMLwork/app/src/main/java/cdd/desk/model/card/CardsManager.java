package cdd.desk.model.card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cdd.desk.model.PlayGameCallBack;

import static cdd.desk.model.card.CardsType.danzhang;
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

	/*
	 * 参数：牌
	 * 功能：不考虑花色，返回牌的权重，3返回3，A返回14,2返回15
	 * 输出：牌的权重
	 */
    private static int getCardWeight(Card card)
	{
		return card.getRow()+3;
	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是顺子
	 * 输出：若牌组是顺子 返回true
	 */
	private static boolean isShun(List<Card> cards) {
		for(int i = 0; i < cards.size()-1; i++) {
			if ((getCardWeight(cards.get(i+1))-getCardWeight(cards.get(i)))%13!=1) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是同花顺
	 * 输出：若牌组是同花顺 返回true
	 */
	private static boolean isTongHuaShun(List<Card> cards) {
		//不是顺子返回flase
		if(!isShun(cards))
			return false;

		//要求所有牌的花色鄙俗相同，否则返回flase
		for(int i = 0; i < cards.size() - 1; i++) {
			if(cards.get(i).getColor() != cards.get(i+1).getColor())
				return false;
		}

		return true;
	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是杂顺
	 * 输出：若牌组是杂顺 返回true
	 */
	private static boolean isZaShun(List<Card> cards) {
		if (!isShun(cards))
			return false;

		return !isTongHuaShun(cards);
	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是同花五
	 * 输出：若牌组是同花五 返回true
	 */
	private static boolean isTongHuaWu(List<Card> cards) {

		//要求任意两张牌花色相同，否则返回false
		for(int i = 0; i < cards.size() - 1; i++) {
			if(cards.get(i).getColor() != cards.get(i+1).getColor())
				return false;
		}
		return true;
	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是三带一对
	 * 输出：若牌组是三带一对 返回true
	 */
	private static boolean isSanDaiEr(List<Card> cards) {

		if(cards.get(0).getPoints() == cards.get(2).getPoints()
				&& cards.get(3).getPoints() == cards.get(4).getPoints()) {
			return true;
		}

		if(cards.get(0).getPoints() == cards.get(1).getPoints()
				&& cards.get(2).getPoints() == cards.get(4).getPoints()) {
			return true;
		}

		return false;

	}

	/*
	 * 参数：牌数组
	 * 功能：判断是否是四带一张
	 * 输出：若牌组是四带一张 返回true
	 */
	private static boolean isSiDaiYi(List<Card> cards) {

		if(cards.get(0).getPoints() == cards.get(3).getPoints()
				|| cards.get(1).getPoints() == cards.get(4).getPoints())
			return true;

		return false;
	}


	//判断牌的类型
	public CardsType jugdeType(List<Card> cards) {
		int len = cards.size();

		//当牌数量为1时，单牌
		if(len == 1) {
            System.out.println("是单牌");
			return danzhang;
		}

		//当牌数量为2是，一对
		if(len == 2) {
			if(cards.get(0).getPoints() == cards.get(1).getPoints()) {
                System.out.println("是一对");
				return CardsType.yidui;
			}
		}

		// 当牌数为3时,三个
		if (len == 3) {
			if (cards.get(0).getPoints() == cards.get(2).getPoints()) {
                System.out.println("是三张");
				return CardsType.sanzhang;
			}
		}

		//当排数为5时，可能为顺、杂顺、同花顺、同花五、三带一对、四带一张、同花五
		if(len == 5) {
			if (isTongHuaShun(cards)) {
                System.out.println("是同花顺");
                return CardsType.tonghuashun;
            }

			if (isZaShun(cards)) {
                System.out.println("是杂顺");
                return CardsType.zashun;
            }

			if(isSanDaiEr(cards)) {
                System.out.println("是三代二");
				return CardsType.sandaier;
			}

			if(isTongHuaWu(cards)) {
				System.out.println("是五同花");
				return CardsType.wutonghua;
			}

			if(isSiDaiYi(cards)) {
                System.out.println("是四带一");
                return CardsType.sidaiyi;
            }
		}
        System.out.println("什么牌型都不是");
		return CardsType.card0;//不是上述任何一种牌型
	}


	/*
	 * 描述：在上家的牌与出牌者想要选择的牌的类型一致的情况下，比较牌组的大小以确定出牌者是否能出牌
	 * 参数：牌的类型 上家的牌组 目前的牌组
	 * 功能：判断是否能出牌
	 * 备注：目前只支持在CardsType相同的情况下出牌
	 * 		现在默认目前所有的牌的类型都按照这样的规则来比较
			ps当然我觉得这样做是不行的
	 */

//TODO 要补充牌型的判断part...
	public boolean isPermissible(deliveredCardsGroup previous,deliveredCardsGroup current,PlayGameCallBack playGameCallBack)
	{

		System.out.println("现在应该判断牌的合法性了");
		boolean validation = false;
		CardsType currentType = current.getType();

		//先看看有没有上家
		if(previous.hasCards() == false) {//说明没有上家
			switch (currentType){
				case card0:
					playGameCallBack.onCardsNotValid("不可以这样出牌哦");
					return false;
				case danzhang:
				case yidui://两张相等的对子
				case sanzhang://三张一样的
					return true;
				case sandaier://三带一
				case sidaiyi://四带一
				case tonghuashun://同花顺
					playGameCallBack.onCardsNotValid("暂时不支持的牌型 但是后面会补充");
					return false;
				default:
					return false;//要加一个default
			}
		}

		CardsType previousType = previous.getType();
		if(currentType != previousType){
			playGameCallBack.onCardsNotValid("与商家的牌不匹配哦");
		}
		switch (currentType){
			case card0:
				validation = false;
				playGameCallBack.onCardsNotValid("不可以这样出牌哦");
				System.out.println("这个时候的牌是不合法的");
				return false;
			//单牌 对子 三张一样的牌 判定规则一样，都是看
			case danzhang://单牌
			case yidui://两张相等的对子
			case sanzhang://三张一样的
				int size1 = current.getCardsGroup().size();
				int size2 = previous.getCardsGroup().size();
				//比较相同的牌中权值最大的牌
				if(current.getCardsGroup().get(size1 - 1).getWeight() > previous.getCardsGroup().get(size2 - 1).getWeight()) {
					validation = true;
				}
				else{
					playGameCallBack.onCardsNotValid("牌太小啦 换一种出牌方式吧");
				}
				System.out.println("单张或者两张或者三张");
				break;
			case sandaier://三带一
			case sidaiyi://四带一
			case tonghuashun://同花顺
         /*int size1 = current.getCardsGroup().size();
         int size2 = previous.getCardsGroup().size();
         //比较相同的牌中权值最大的牌
         if(currentType == previousType && current.getCardsGroup().get(size1 - 1).getWeight() > previous.getCardsGroup().get(size2 - 1).getWeight())
            validation = true;
         break;*/
				System.out.println("三带一或者四带一或者同花顺");
				validation = false;
				playGameCallBack.onCardsNotValid("暂时不支持的牌型 后面完善了规则再补充");
		}
		return validation;
	}
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
			//这里是java的(划掉)语法糖
			//@Override
			public int compare(Card card1, Card card2) {
				return card1.compareTo(card2);
			}	
		}
	);
	}

}