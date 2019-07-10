package cdd.login.contract;

import cdd.BasePresenter;
import cdd.BaseView;

public interface loginContract {
    interface Model {
        boolean QueryByName(String name);
    }

    interface View extends BaseView<Presenter> {
        void SkipToMenu(String name);
    }

    interface Presenter extends BasePresenter {
       void  login(String name);
    }
}
