package cdd.rank.presenter;

import android.util.Log;

import cdd.tool.DbCallBack;
import cdd.tool.PlayerRepo;
import cdd.rank.contract.rankContract;
import cdd.rank.view.rankActivity;


public class rankPresenter implements rankContract.Presenter,DbCallBack.RankCallBack {
    private rankActivity mRankActivity;
    private PlayerRepo mRepo;

    public rankPresenter(rankActivity rankActivity) {
        this.mRankActivity = rankActivity;
        mRankActivity.setPresenter(this);
        mRepo = new PlayerRepo(rankActivity);
    }

    @Override
    public void start() {

    }


    @Override
    public void dispalyRank(String name, int score, int rank,String ary[][]) {
        mRankActivity.displayRank(name,score,rank,ary);
        Log.e("", "dispalyRank: name"+ name );
    }

    @Override
    public void getPlayer(String name) {
       mRepo.getPlayerByName(name,this);
    }
}
