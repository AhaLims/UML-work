package card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//���⣺��cards type���������в��� �����ж����ܲ��ܳ�
/*
 * CardsManager:���ƽ��й����ж�
 * ���ܣ�
 * 1.jugdeType �жϳ��Ƶ�����
 * 2.canDisplay �ж����Ƿ��ܱ���
 * 3.order �����ƽ�������
 */

//Ӧ�ö��Ǿ�̬�ķ���....??
public class CardsManager{
	/*
	 * ������������
	 * ���ܣ��ж��Ƶ����� ��Ϊ֮ǰ��������ԱȽϺ��ж�
	 * ��� CardsType
	 */
	public static CardsType jugdeType(List<Card> list) {
		int len = list.size();
		//ֻ�����ǣ�cardSingle,//���ơ�
		//cardsCouple,//���ӡ�
		//cards3,//3������
		if(len <= 4)
		{
			//�����һ����������ͬ��˵��ȫ����ͬ
			if(len > 0 && list.get(0).getPoints() == list.get(len - 1).getPoints())
			{
				switch (len) {
				case 1:
					return CardsType.cardSingle;//������
				case 2:
					return CardsType.cardsCouple;//����
				case 3:
					return CardsType.cards3;//������ͬ����
				case 4:
					return CardsType.cards4;//������ͬ����
				}
			}
	}
		//������ �������һ�������һ���Ʋ�һ�� ����������һ
		if(len == 4 && list.get(0).getPoints() != list.get(len - 1).getPoints())
			return CardsType.cards31;
		//��������ٸ���CardsType������ 
		return CardsType.card0;//�����������κ�һ���Ƶ�����
	}
	/*
	 * ���������ϼҵ������������Ҫѡ����Ƶ�����һ�µ�����£��Ƚ�����Ĵ�С��ȷ���������Ƿ��ܳ���
	 * �������Ƶ����� �ϼҵ����� Ŀǰ������
	 * ���ܣ��ж��Ƿ��ܳ���
	 * ��ע��Ŀǰֻ֧����CardsType��ͬ������³���
	 * 		����Ĭ��Ŀǰ���е��Ƶ����Ͷ����������Ĺ������Ƚ�
			ps��Ȼ�Ҿ����������ǲ��е�
	 */
	
	public static boolean canDisplay(CardsType type,
			List<Card> previousList,
			List<Card> presentList){
		switch(type) {
		case cardSingle:
		case cardsCouple:
		case cards3:
		case cards4:
		case cardsSequence:
		case cards31:
			if(presentList.get(0).compareTo(previousList.get(0)) > 0)
				return true;
			else return false;
		default:
			return false;
		}
	}
	

	//��ֱ����Card��������ĳ������ ʵ�ֺ�����������......? �����ֱ�ӵ��� ���...
	//��Ҫд������...̫����
	//ʹ��Collections����ǳ��򵥣�\
	//����ֻ��Ҫ��ʵ����Comparable�ӿڵ��ഫ���������һ��Collections.sort() 
	//�����Ϳ��Զ�����������ˡ�
	/*
	 * �������Ƶ�����
	 * ���ܣ����ƽ�������
	 * �������
	 */
	public static void order(List<Card> list){
		
		Collections.sort(list,new Comparator<Card>() {//ʵ���˽ӿ��е�compare����
			//������javaĳ��Ť����(����)�﷨��
			//@Override
			public int compare(card.Card card1, card.Card card2) {
				// TODO Auto-generated method stub
				return card1.compareTo(card2);
			}	
		}
	);
	}
}