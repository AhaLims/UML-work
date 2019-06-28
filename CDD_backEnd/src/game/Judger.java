/*
 * judge 判断两个牌组的大小关系（两个牌组的类型以及value已经给出来了)*/
package game;
import card.deliveredCardsGroup;
import card.CardsType;
public class Judger{
	public boolean isBigger(deliveredCardsGroup previous,deliveredCardsGroup current) {
		if(current.getType() == CardsType.card0)return false;//不合法的牌
		//现在只考虑大家都出单张的情况 后面再丰富这里的规则
		if(current.getType() == CardsType.cardSingle)
		{
			if(previous.getType() == CardsType.cardSingle)
			{
				if(current.getValue() > previous.getValue())
					return true;//都是单牌 而且现在的比之前大 才能出
			}
		}
		
		return false;
		
	}
}
 