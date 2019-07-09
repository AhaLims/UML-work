package cdd.desk.presenter;

import cdd.desk.contract.deskContract;
import cdd.desk.model.deskModel;
import cdd.desk.view.deskActivity;
/**
 * Creates a presenter for  playing
 */
public class deskPresenter implements deskContract.Presenter {
    private final deskActivity mDeskActivity;
    private final deskContract.Model mModel;

    public deskPresenter(deskActivity deskActivity) {
        this.mDeskActivity = deskActivity;
        mDeskActivity.setPresenter(this);
        mModel = new deskModel(this);
    }

    public void start() {


    }
}
