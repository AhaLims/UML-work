package cdd.login.presenter;

import android.widget.Toast;

import cdd.login.contract.loginContract;
import cdd.login.model.loginModel;
import cdd.login.view.loginActivity;
import cdd.tool.PlayerRepo;

public class loginPresenter implements loginContract.Presenter
{
    private loginActivity mLoginActivity;
    private loginModel mLoginModel;

    public loginPresenter(loginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
        mLoginActivity.setPresenter(this);
        mLoginModel = new loginModel(loginActivity);
    }
    @Override
    public void start() {

    }

    @Override
    public void login(String name) {
        if(mLoginModel.QueryByName(name))
                { mLoginActivity.SkipToMenu(name);}
                else
                    {
                        mLoginModel.insertPlayer(name);
                        mLoginActivity.ToastUserNotExist();
                        mLoginActivity.SkipToMenu(name);
                   }

    }
}
