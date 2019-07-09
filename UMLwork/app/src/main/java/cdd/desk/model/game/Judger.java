/*
 * judge 判断两个牌组的大小关系（两个牌组的类型以及value已经给出来了)*/
package cdd.desk.model.game;

import cdd.desk.model.card.CardsType;
import cdd.desk.model.card.deliveredCardsGroup;

public class Judger{

	//判断在有上家的情况下牌是不是合法的
	//如果没有上家 previous 传空指针
	public boolean isPermissible(deliveredCardsGroup previous,deliveredCardsGroup current)
	{
		boolean validation = false;
		if(current.getType() == CardsType.card0)validation = false;//不合法的牌

		//现在只考虑大家都出单张的情况 后面再丰富这里的规则

		if(previous == null)validation =  true;//没有上家的情况下可以随便出牌
		if(current.getType() == CardsType.cardSingle)
		{
			if(previous.getType() == CardsType.cardSingle)
			{
				if(current.getValue() > previous.getValue())//或者这里
					validation = true;//都是单牌 而且现在的比之前大 才能出
			}
		}
		
		//return validation;

		//初步测试阶段忽略规则 默认为合法
		return true;
		
	}

}
 