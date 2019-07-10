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
        System.out.print("应该出的牌的权重为");
        for(int i = 0;i < playerCards.size();i++){
            System.out.println(playerCards.get(i).getWeight());
        }
        mDeskActivity.displayPlayerCards(playerCards);
       // mDeskActivity.removeShowedCards(1);
    }

    @Override
    public void displayRobotCards(List<Card> robotCards, int robot) {
        mDeskActivity.displayRobotCards(robotCards,robot);
       // mDeskActivity.removeShowedCards((robot+1)%4);
    }

    @Override
    public void setRobotHandCard(List<Card> robotCards,int robot){
        mDeskActivity.setRobotHandCard(robotCards, robot);
    }

    @Override
    public void onCardsNotValid(String message) {
        mDeskActivity.displayIrregularity(message);
    }

    //TODO 不出牌
    @Override
    public void playerPass(){
        mGame.turn(null,this);
    }


    @Override
    public void onGameEnd(int winnerIndex,int score){
        mDeskActivity.popResultDialog(winnerIndex,score);
    }
    //TODO 思考是不是需要分成两个 胜利和失败各一个函数
/////游戏结束的时候的时候调用

    public void playerShowCards(List<Integer> cards){
       mGame.turn(cards,this);
    }

    @Override
    public void onRolePass(int role) {
        mDeskActivity.displayPass(role);
    }

    @Override
    public void escape()
    {
        //mGame.escape
        //游戏需要处理分数事件....要扣分
    }

    @Override
    public void onNext(int role) {
        mDeskActivity.startTimer(role);
    }

    @Override
    public boolean isFirstHand(int role) {
        return mGame.IsFirstHand(role);
    }

}
