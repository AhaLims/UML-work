package card;

public class Card implements Comparable<Card>{//实现了Comparable的接口 这样就是可以比较的了
	//包可见 所以不用写import?
	private int points;//点数
	private int weight;//代表牌的真实权重 其中 1->14 2->15
	private CardColor cardColor;
	
	public Card(int p,CardColor color) {
		this.setPoints(p);
		this.setWeight(p);
		this.setCardColor(color);
	}
	//重载compareTo函数 可以用这个函数进行比较
	@Override
	public int compareTo(Card arg0) {
		if(this.getWeight() > arg0.getWeight()) return 1;
		else if(this.getWeight() == arg0.getWeight())
		{
			return this.getCardColor().compareTo(arg0.getCardColor());//枚举类型变量的比较方式
		}
		return -1;//不满足上面的情况 说明card 比传进来的小

	}
	
	//一些无关紧要的set getxxx函数
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
	//通过points设置权重
	public void setWeight(int points) {
		if(points == 1 )
			this.weight = 14;
		else if(points == 2)
			this.weight = 15;
		else this.weight = points;
	}
	
}