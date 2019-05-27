
package role;

import java.util.ArrayList;
import java.util.List;

import card.*;
/*
 * RoleManager:playerManager��RobotManager�ĳ�����
 * ʵ�ֵĹ����У�
 * selectCards �麯�� ��Ҫ���ݽ�ɫ�Ĳ�ͬ�ֱ�ʵ��
 * checkCards ��鵱ǰsend�����ܲ��ܱ���
 * showedCards  ����չʾ������Cards  
 * refreshCards �����ƣ��ѳ������Ƴ���  
 * isEnd���жϸ�role�����Ƿ������
 * getCardsAmount ����Ŀǰ�Ƶ�����
 * ����ʵ�ֵĺ���:sendCards
 * 
 */
//��ͬ�� ����Ҫ���� ������ �ж������ֻ��Ǻ���

//һ��С����  List�Ǵ����õ��� ......�ر���CardsManager��order������Ҫע�����.....
public abstract class RoleManager{
	protected List<Card> cards;
	//��ȡ�ƣ������ƽ�������
	public RoleManager(){
		cards = new ArrayList<Card>();//list ��arraylist...???
	}
	//�����߼���������...List/ArrayList/����...???
	//���ر�ѡ�е�index������
	public abstract int[] selectCards(List<Card> previous);//����д��һ����....
	//undo
	public boolean checkCards(List<Card> sendedCards,List<Card> previous){
		
		return false;//������ǲ����ܳ� 
		
	}
	//���ƽ�������
	public void order() {//���ܻ������ ǳ���Ƶ�����
		CardsManager.order(cards);
	}
	/*
	 * ���´���һ������ ���ر����ߵ��Ƹ�ǰ��
	 * ������showCards֮ǰִ�� ����������
	 */
	public List<Card> showCards(int[] removeCardsIndex){
		List<Card> showedcards = new ArrayList<Card>();
		int len = removeCardsIndex.length;
		for(int i = 0; i < len; i++)
		{
			int index = removeCardsIndex[i];
			showedcards.add(cards.get(index));//ͨ��index�ҵ�cards�б�ѡ�е���
		}
		return showedcards;
	}
	//������
	/*
	 * ����������ѡ�е��Ƶ�index
	 * ���ܣ����û�����֮��,������
	 * ���ز���:����֮�����
	 */
	public List<Card> refreshCards(int[] removeCardsIndex) {
		int len = removeCardsIndex.length;
		for(int i = 0; i < len; i++)
		{
			cards.remove(i);//��ԭ���������ߵ�i����
		}
		return cards;
	}
	//�ж��Ƿ��������
	public boolean isEnd() {
		if(cards.size() == 0)
			return true;
		return false;
	}
	
	public List<Card> getCards() {
		return cards;
	}
//һ��ʼ���Ƶ�ʱ������ⲿ�ֵĹ���?
	//�����ǲ���ǳ������....��������?
	public void setCards(List<Card> cs) {
		this.cards = cs;
	}
	public int getCardsAmount()
	{
		return cards.size();//�����Ƶ�����
	}
	
	
}