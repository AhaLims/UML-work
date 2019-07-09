package cdd.desk.contract;

import java.util.List;
import cdd.BasePresenter;
import cdd.BaseView;
import cdd.desk.model.card.Card;
/**
 * This specifies the contract between the view and the presenter.
 */
public interface deskContract {
    interface Model {

    }

    interface View extends BaseView<Presenter> {
        void displayPlayerHandCards(List<Card> playerCards);
        void displayPlayerCards(List<Card> playerCards);
        void displayRobotCards(List<Card> playerCards, int robot);
        void displayIrregularity(CharSequence message);
        void showCards();
    }

    interface Presenter extends BasePresenter{
        void playerPass();
        void playerShowCards(List<Integer> cards);
    }
}
