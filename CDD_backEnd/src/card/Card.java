package card;

public class Card implements Comparable<Card>{//ʵ����Comparable�Ľӿ� �������ǿ��ԱȽϵ���
	//���ɼ� ���Բ���дimport?
	private int points;//����
	private CardColor cardColor;
	public Card(int p,CardColor color) {
		this.setPoints(p);
		this.setCardColor(color);
	}
	//����compareTo���� ����������������бȽ�
	@Override
	public int compareTo(Card arg0) {
		if(this.getPoints() > arg0.getPoints()) return 1;
		else if(this.getPoints() == arg0.getPoints())
		{
			return this.getCardColor().compareTo(arg0.getCardColor());//ö�����ͱ����ıȽϷ�ʽ
		}
		return -1;//�������������� ˵��card �ȴ�������С

	}
	
	//һЩ�޹ؽ�Ҫ��set getxxx����
	public CardColor getCardColor() {
		return cardColor;
	}
	public void setCardColor(CardColor cardColor) {
		this.cardColor = cardColor;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
}