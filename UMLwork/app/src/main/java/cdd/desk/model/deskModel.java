package cdd.desk.model;

import cdd.desk.contract.deskContract;
import cdd.desk.presenter.deskPresenter;

public class deskModel implements deskContract.Model {
    private final deskPresenter mDeskPresenter;

    public deskModel(deskPresenter mDeskPresenter) {
        this.mDeskPresenter = mDeskPresenter;
    }
}
