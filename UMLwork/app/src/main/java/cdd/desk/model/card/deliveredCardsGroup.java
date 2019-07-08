//这里是 用户点击的牌组 或者是出出去的牌组 暂时不写成这样子吧....先用CardsManager来顶着就好了
package card;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

//可以提供牌的值以及牌的类型
//每次牌一张一张的加进deliveredCardsGroup
public class deliveredCardsGroup extends CardsGroup{
	private CardsType type;
	private int value;
	public deliveredCardsGroup(List<Card> c) {
		super(c);
	}
	public deliveredCardsGroup() {
		super();
	}
	public void calculateAttribute() {
		type = this.cardsManager.jugdeType(this.card);
		value = this.cardsManager.getCardsGroupValue(this.card);
	}
	public int getValue() {
		calculateAttribute();
		return value;
	}
	public CardsType getType(){
		calculateAttribute();
		return type;
	}

	
	
	
}