package cdd.desk.contract;

import android.widget.LinearLayout;

import java.util.List;

import cdd.desk.Card;
import cdd.desk.view.CardsViewGroup;

public interface deskContract {
    interface Model {

    }

    interface View {
        void displayPlayerHandCards(List<Card> playerCards);
        void displayPlayerCards(List<Card> playerCards);
        void displayRobotCards(List<Card> playerCards, int robot);
        void displayIrregularity();
        void showCards();
    }

    interface Presenter {
    }
}
