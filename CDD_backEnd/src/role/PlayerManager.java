package role;

import java.util.ArrayList;
import java.util.List;

import card.Card;

/*
 * 与前端交互的部分：getSelectedCards 获取被选中的牌的index的数组
 * sendCards 用户选中的牌 组织成List的数据类型
 */
public class PlayerManager extends RoleManager{

	//这里为了方便本次测试 先默认只出一张牌 而且出的是最大的牌
	@Override
	public int[] selectCards(List<Card> previous) {
		// TODO Auto-generated method stub
		int[] selectedCards = new int[1];
		int len = cards.size();
		selectedCards[0] =  len - 1;
		return selectedCards;
	}

	//由前端负责给交互 还没写...


	
}