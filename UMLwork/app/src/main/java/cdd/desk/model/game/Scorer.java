package cdd.desk.model.game;
import java.util.ArrayList;
import java.util.List;
import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
import cdd.desk.model.card.handCardsGroup;

//在游戏之后调用 来计算分数
public class Scorer {
    //需要知道winner 的ID
    //每个人的牌
    public int getScore(int winnerIndex, handCardsGroup hd[]) {//返回四个role的分数
        int[] scores = new int[4];
        for (int i = 0; i < 4; i++) {
            scores[i] = hd[i].getCardsGroup().size();//返回剩下的牌组的数量
            if (scores[i] >= 8 && scores[i] < 10) {
                scores[i] *= 2;
            } else {
                //如果游戏结束时，手上还有 8 张或更多的牌，同时有黑桃 2 ，牌分还要再乘以 2
                if (hd[i].canFindCard(2, CardColor.Spade) != -1)
                    scores[i] *= 2;
                if (scores[i] >= 10 && scores[i] < 13) {
                    scores[i] *= 3;
                } else if (scores[i] == 13) {
                    scores[i] *= 4;
                }
            }
        }
        int PlayerScore = scores[1] + scores[2] + scores[3] - 3 * scores[0];
        //A 的得分 =(B 的牌分 -A 的牌分 )+(C 的牌分 -A 的牌分 )+(D 的牌分 -A 的牌分 )
        return PlayerScore;


    }
}
