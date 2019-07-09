//管理手牌 并更新牌 判断牌是不是出完了
//每次出牌之后都需要调用

//貌似这个部分放到 Rolemanager去了
package card;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class handCardsGroup extends CardsGroup{
	public List<Card> getCardsGroup(){//返回目前所有的手牌 List类型的
		return card;
	}
}