
package role;

import java.util.ArrayList;
import java.util.List;

import card.*;
/*
 * RoleManager:playerManager和RobotManager的抽象父类
 * 实现的功能有：
 * selectCards 虚函数 需要根据角色的不同分别实现
 * checkCards 检查当前send的牌能不能被出
 * showedCards  返回展示出来的Cards  
 * refreshCards 更新牌（把出的牌移除）  
 * isEnd：判断该role的牌是否出完了
 * getCardsAmount 返回目前牌的数量
 * 必须实现的函数:sendCards
 * 
 */
//共同点 ：需要出牌 更新牌 判断是先手还是后手

//一个小问题  List是传引用的吗 ......特别在CardsManager的order那里需要注意这点.....
public abstract class RoleManager{
	protected List<Card> cards;
	//获取牌，并对牌进行排序
	public RoleManager(){
		cards = new ArrayList<Card>();//list 和arraylist...???
	}
	//可能逻辑上有问题...List/ArrayList/数组...???
	//返回被选中的index的数组
	public abstract int[] selectCards(List<Card> previous);//这样写不一定好....
	//undo
	public boolean checkCards(List<Card> sendedCards,List<Card> previous){
		
		return false;//检查牌是不是能出 
		
	}
	//对牌进行排序
	public void order() {//可能会有深复制 浅复制的问题
		CardsManager.order(cards);
	}
	/*
	 * 重新创建一个数组 返回被移走的牌给前端
	 * 必须在showCards之前执行 否则会出问题
	 */
	public List<Card> showCards(int[] removeCardsIndex){
		List<Card> showedcards = new ArrayList<Card>();
		int len = removeCardsIndex.length;
		for(int i = 0; i < len; i++)
		{
			int index = removeCardsIndex[i];
			showedcards.add(cards.get(index));//通过index找到cards中被选中的牌
		}
		return showedcards;
	}
	//更新牌
	/*
	 * 参数：所有选中的牌的index
	 * 功能：在用户出牌之后,更新牌
	 * 返回参数:更新之后的牌
	 *  
	 */
	public List<Card> refreshCards(int[] removeCardsIndex) {
		int len = removeCardsIndex.length;
		for(int i = 0; i < len; i++)
		{
			cards.remove(i);//从原数组中移走第i张牌
		}
		return cards;
	}
	//判断是否出完了牌
	public boolean isEnd() {
		if(cards.size() == 0)
			return true;
		return false;
	}
	
	public List<Card> getCards() {
		return cards;
	}
//一开始出牌的时候进行这部分的工作?
	//这里是不是浅复制了....会有问题?
	public void setCards(List<Card> cs) {
		this.cards = cs;
	}
	public int getCardsAmount()
	{
		return cards.size();//返回牌的张数
	}
	
	
}