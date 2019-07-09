package cdd.desk.model.card;

public class Card implements Comparable<Card>{//实现了Comparable的接口 这样就是可以比较的了
	private int points;//点数
	private int weight;//代表牌的真实权重 其中 1->14 2->15
	private CardColor cardColor;

	public Card(int _weight) {
		int c = _weight % 4;
		weight = _weight;
		int p = weight / 4;
		if(p == 12) points = 1;
		else if(p ==13) points = 2;
		else points = p + 2;
		switch(c) {
		case 0:
			cardColor = CardColor.Diamond;
			break;
		case 1:
			cardColor = CardColor.Club;
			break;
		case 2:
			cardColor = CardColor.Heart;
			break;
		case 3:
			cardColor = CardColor.Spade;
			break;
		}
	}
	
	
	public Card(int p,CardColor color) {
		points = p;
		this.setWeight(p,color);
		this.cardColor = color;
	}

	public int getPoints() {
		//测试相关的代码
		//System.out.println(points);
		
		return points;
	}
	public CardColor getColor() {
		return cardColor;
	}
	public int getWeight() {
		return weight;
	}
	//通过points设置权重
	public void setWeight(int points,CardColor color) {
		
		//点数的权值
		if(points == 1 )
			this.weight = 12;
		else if(points == 2)
			this.weight = 13;
		else this.weight = points - 2;
		this.weight *= 4;
		
		//颜色的权值
		switch(color) {
		case Diamond:
			break;
		case Club:
			this.weight += 1;
			break;
		case Heart:
			this.weight += 2;
			break;
		case Spade:
			this.weight += 3;
			break;
		default:
			break;
		}
			
	}
	@Override
	public int compareTo(Card arg0) {
		if(this.getWeight() > arg0.getWeight()) return 1;
		else if(this.getWeight() == arg0.getWeight())return 0;
		
		return -1;//不满足上面的情况 说明card 比传进来的小
	}

    public int getRow() {
		int point = getPoints();
		int row ;
        switch(point)
		{
			case 1:
				row = 11;
				break;
			case 2:
				row = 12;
				break;
			default:
				row = point - 3;
		}
        return row;
    }

    public int getCol() {
        int col = getColor().ordinal();
        return  col;
    }
}