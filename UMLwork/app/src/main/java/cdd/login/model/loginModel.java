package cdd.login.model;

import android.content.Context;

import cdd.desk.model.role.Player;
import cdd.login.contract.loginContract;
import cdd.tool.PlayerRepo;

public class loginModel implements loginContract {
    private PlayerRepo mRepo;
    private Context context;
    public loginModel (Context context)
    {
        this.context = context;
        mRepo = new PlayerRepo(context);
    }
    public boolean QueryByName(String name)
    {
        return mRepo.QueryByName(name);
    }

    public void insertPlayer(String name)
    {
        Player player = new Player();
        player.setPlayerName(name);
        player.setScore(0);
        mRepo.insert(player);
    }
}
