package cdd.desk.model;

import java.util.List;

import cdd.desk.model.card.Card;

public interface PlayGameCallBack {

    void displayPlayerHandCards(List<Card> playerCards);

    void displayPlayerCards(List<Card> playerCards);

    void displayRobotCards(List<Card> playerCards, int robot);

     void setRobotHandCard(List<Card> robotCards,int robot);

     void onCardsNotValid(CharSequence message);
}
