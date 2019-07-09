//这里是 用户点击的牌组 或者是出出去的牌组 暂时不写成这样子吧....先用CardsManager来顶着就好了
package cdd.desk.model.card;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

//可以提供牌的值以及牌的类型
//每次牌一张一张的加进deliveredCardsGroup
public class deliveredCardsGroup extends CardsGroup {
	private CardsType type;

	public deliveredCardsGroup(List<Card> c) {
		super(c);
	}

	public deliveredCardsGroup() {
		super();
	}

	public void calculateAttribute() {

	}

	//总权值
	public int getTotolValue() {
		return  this.cardsManager.getCardsGroupValue(this.card);
	}

	//类型
	public CardsType getType() {
		type = this.cardsManager.jugdeType(this.card);
		return type;
	}
	public int getBiggestValue(){
		//返回 其中权值最大的那张牌 的权值
		int size = card.size();
		int maxValue = card.get(0).getWeight();
		for(int i = 1; i < size ;i++){
			if(maxValue < card.get(i).getWeight())
				maxValue = card.get(i).getWeight();//更新最大牌权值
		}
		return maxValue;
	}

	public boolean hasCards() {
			//判断是否出了牌 如果没有出牌 返回false
			if (this.card.size() == 0) return false;
			return true;
	}
}

	
	
