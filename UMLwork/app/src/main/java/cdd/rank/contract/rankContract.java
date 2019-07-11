package cdd.rank.contract;
import cdd.BasePresenter;
import cdd.BaseView;


public interface rankContract {
    interface Model {

    }

    interface View extends BaseView<rankContract.Presenter> {
        void displayRank(String name, int score, int rank,String ary[][]);
    }

    interface Presenter extends BasePresenter {
      void getPlayer(String name);
    }
}
