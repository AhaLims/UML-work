/**
 * 计算分数类
 * 用于最后计算分数
 * 参考网站：https://jingyan.baidu.com/article/d621e8daeb8f382865913f2d.html
 */
package score;

import java.util.ArrayList;
import java.util.List;


/*---------ScoreManage 类开始 -----------*/
public class ScoreManager {
	final int PLAYER = 4;

/*--------------属性成员------------------*/
	private int [] score;
	private int [] restCardAmount;
	private int [] scoreHelper;

/**
 * 函数名：ScoreManage
 * 参数：各剩余的牌数
 * 功能：创建一个用于计分的对象
 * 返回参数：无（此为构造函数）
 */

	 public ScoreManager(int [] restCardAmount){
	 	
		 this.score = new int[PLAYER];
	 	for (int i = 0 ; i < PLAYER ; i++)
	 		score[i] = 0;

	 	this.restCardAmount = new int [PLAYER];
	 	for (int i = 0 ; i < PLAYER ; i++)
	 		this.restCardAmount[i] = restCardAmount[i];
	 	this.scoreHelper = new int[PLAYER];
	}

/**
 * 函数名：caculateScore
 * 参数：无
 * 功能：计算各玩家得分
 * 返回参数：各人分数的s 数组 int[]
 */
	public int[] caculateScore(){
		for (int i = 0 ; i < PLAYER ; i++)
		{
			if (restCardAmount[i] < 8)
				scoreHelper[i] = restCardAmount[i];
			else if (restCardAmount[i] < 10) 
				scoreHelper[i] = 2 * restCardAmount[i];
			else if (restCardAmount[i] < 13)
				scoreHelper[i] = 3 * restCardAmount[i];
			else scoreHelper[i] = 4 * restCardAmount[i];
		}

		for (int j = 0; j < PLAYER ; j++) {
			for (int k = 0 ; k < PLAYER ; k ++)
				score[j] += scoreHelper[k] - scoreHelper[j];
		}

		return score;
	}

/*--------------get 函数-----------------*/
	public int [] getScore(){
		return score;
	}

}
/*---------ScoreManage 类结束 -----------*/