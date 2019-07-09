package cdd.desk.presenter;

import java.util.List;

import cdd.desk.contract.deskContract;
import cdd.desk.model.PlayGameCallBack;
import cdd.desk.model.card.Card;
import cdd.desk.model.game.Game;
import cdd.desk.view.deskActivity;
/**
 * Creates a presenter for  playing
 */
public class deskPresenter implements deskContract.Presenter,PlayGameCallBack {
    private final deskActivity mDeskActivity;
    private final cdd.desk.model.game.Game mGame;

    public deskPresenter(deskActivity deskActivity) {
        this.mDeskActivity = deskActivity;
        mDeskActivity.setPresenter(this);
        mGame = new Game();
    }

    public void start() {
        mGame.InitGame(this);
}

    @Override
    public void displayPlayerHandCards(List<Card> playerCards) {
        mDeskActivity.displayPlayerHandCards(playerCards);
    }

    @Override
    public void displayPlayerCards(List<Card> playerCards) {
        mDeskActivity.displayPlayerCards(playerCards);
        mDeskActivity.removeShowedCards(3);
    }

    @Override
    public void displayRobotCards(List<Card> robotCards, int robot) {
        mDeskActivity.displayRobotCards(robotCards,robot);
        mDeskActivity.removeShowedCards((robot-1)%4);
    }

    @Override
    public void setRobotHandCard(List<Card> robotCards,int robot){
        mDeskActivity.setRobotHandCard(robotCards, robot);
    }

    @Override
    public void onCardsNotValid(CharSequence message) {
        mDeskActivity.displayIrregularity(message);
    }

    @Override
    public void onRobotPass(int robot) {

    }
}
