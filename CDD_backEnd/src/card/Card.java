package card;

public class Card implements Comparable<Card>{//ʵ����Comparable�Ľӿ� �������ǿ��ԱȽϵ���
	//���ɼ� ���Բ���дimport?
	private int points;//����
	private int weight;//�����Ƶ���ʵȨ�� ���� 1->14 2->15
	private CardColor cardColor;
	
	public Card(int p,CardColor color) {
		this.setPoints(p);
		this.setWeight(p);
		this.setCardColor(color);
	}
	//����compareTo���� ����������������бȽ�
	@Override
	public int compareTo(Card arg0) {
		if(this.getWeight() > arg0.getWeight()) return 1;
		else if(this.getWeight() == arg0.getWeight())
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
	public int getWeight() {
		return weight;
	}
	//ͨ��points����Ȩ��
	public void setWeight(int points) {
		if(points == 1 )
			this.weight = 14;
		else if(points == 2)
			this.weight = 15;
		else this.weight = points;
	}
	
}