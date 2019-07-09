package cdd.desk.model;

import java.util.List;

import cdd.desk.model.card.Card;

public interface PlayGameCallBack {

    void displayPlayerHandCards(List<Card> playerCards);

    void displayPlayerCards(List<Card> playerCards);

    void displayRobotCards(List<Card> playerCards, int robot);

    void setRobotHandCard(List<Card> robotCards,int robot);

    void onCardsNotValid(String message);//牌不合法时 调用的函数

    void onGameWin(int winnerIndex,int PlayerScore);//游戏获得了胜利 返回获得胜利的role 的编号（玩家为0

    void onRobotPass(int robot);
}
