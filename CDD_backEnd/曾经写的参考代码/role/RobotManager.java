//机器人的逻辑处理
package role;

import java.util.List;

import card.Card;

public class RobotManager extends RoleManager{

	//这里也是先默认只出一张牌 而且是最大的牌
	@Override
	public int[] selectCards(List<Card> previous) {
		// TODO Auto-generated method stub
		//if (previous == null){ xxxxx}
		int[] selectedCards = new int[1];
		int len = cards.size();
		selectedCards[0] =  len - 1;
		return selectedCards;
	}
	
}