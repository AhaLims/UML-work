package cdd.desk.presenter;

import cdd.desk.contract.deskContract;
import cdd.desk.model.deskModel;
import cdd.desk.view.deskActivity;

public class deskPresenter implements deskContract.Presenter {
    private final deskActivity mDeskActivity;
    private final deskContract.Model mModel;

    public deskPresenter(deskActivity deskActivity) {
        this.mDeskActivity = deskActivity;
        mModel = new deskModel(this);
    }

}
