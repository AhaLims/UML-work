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
	//-------------------从多态的角度出发 cards不应该在这里定义排序规则--------------------------//
	//-------------------职责不对--------------------------------------------------------//
	//信息专家原则:
	//问题：谁比较卡片的大小？
	//条件：card类拥有card的color,point等信息，
	//方案就是 把比较卡片的大小的职责分配给 card
	//缺点：不支持高内聚，也不支持多态
	//方案：捏造出一个 比较的类来专门执行这一个职责
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
		//测试相关的代码
		if(cardColor == CardColor.Diamond)System.out.print("Diamond");
		else if(cardColor == CardColor.Club)System.out.print("Club");
		else if(cardColor == CardColor.Heart)System.out.print("Heart");
		else if(cardColor == CardColor.Spade)System.out.print("Spade");
		
		return cardColor;
	}
	public void setCardColor(CardColor cardColor) {
		this.cardColor = cardColor;
	}
	public int getPoints() {
		//测试相关的代码
		System.out.println(points);
		
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