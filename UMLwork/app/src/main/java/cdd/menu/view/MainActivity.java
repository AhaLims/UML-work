package cdd.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.uml.umlwork.R;

import cdd.desk.model.role.Player;
import cdd.tool.DbCallBack;
import cdd.tool.PlayerRepo;
import cdd.bgm.view.bgmActivity;
import cdd.desk.view.deskActivity;
import cdd.introduction.introduceActivity;
import cdd.rank.view.rankActivity;
import cdd.menu.contract.menuContract;


public class MainActivity extends AppCompatActivity implements menuContract.View{
    private ImageButton btnStartGame;
    private ImageButton btnShowRank;
    private ImageButton btnIntroduce;
    private ImageButton btnSetBgm;

    PlayerRepo playerRepo=new PlayerRepo (this);
    Player player=new Player("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        final String useName=bundle.getString("useName");
        player=playerRepo.getPlayerByName(useName, new DbCallBack.RankCallBack() {
            @Override
            public void dispalyRank(String name, int score, int rank) {

            }
        });

        //绑定按钮
        btnStartGame  =  findViewById(R.id.start_game);
        btnSetBgm = findViewById(R.id.set_bgm);
        btnShowRank  =  (ImageButton)findViewById(R.id.show_rank);
        btnIntroduce  =  findViewById(R.id.introduce);

        //image resource
        btnStartGame.setImageDrawable(getDrawable(R.drawable.playgame));
        btnSetBgm.setImageDrawable(getDrawable(R.drawable.bgmsetting));
        btnShowRank.setImageDrawable(getDrawable(R.drawable.rank));
        btnIntroduce.setImageDrawable(getDrawable(R.drawable.introduction));

        //开始游戏按钮点击事件：跳转到deskActivity
        btnStartGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        btnStartGame.setImageDrawable(getDrawable(R.drawable.playgamepush));
                        btnStartGame.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
                        Intent intent = new Intent(MainActivity.this , deskActivity.class);
                        Bundle bundle1=new Bundle();
                        bundle1.putCharSequence("useName",useName);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        break;

                    case MotionEvent.ACTION_UP:
                        btnStartGame.setImageDrawable(getDrawable(R.drawable.playgame));
                        btnStartGame.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                }
                return true;
            }
        });

        //音效设置按钮事件，跳转到bgmactivity
        btnSetBgm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        btnSetBgm.setImageDrawable(getDrawable(R.drawable.bgmsettingpush));
                        btnSetBgm.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
                        Intent intent = new Intent(MainActivity.this , bgmActivity.class);
                        startActivity(intent);
                        break;

                    case MotionEvent.ACTION_UP:
                        btnSetBgm.setImageDrawable(getDrawable(R.drawable.bgmsetting));
                        btnSetBgm.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                }
                return true;
            }
        });


        btnShowRank.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        btnShowRank.setImageDrawable(getDrawable(R.drawable.rankpush));
                        btnShowRank.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
                        Intent intent=new Intent(MainActivity.this, rankActivity.class);
                        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
                        bundle.putCharSequence("useName", player.getPlayerName());	//保存用户名
                        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
                        startActivity(intent);	//启动新的Activity
                        break;

                    case MotionEvent.ACTION_UP:
                        btnShowRank.setImageDrawable(getDrawable(R.drawable.rank));
                        btnShowRank.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                }
                return true;
            }
        });


        //开始游戏按钮点击事件：跳转到deskActivity
        btnIntroduce.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        btnIntroduce.setImageDrawable(getDrawable(R.drawable.introductionpush));
                        btnIntroduce.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
                        Intent intent = new Intent(MainActivity.this , introduceActivity.class);
                        startActivity(intent);
                        break;

                    case MotionEvent.ACTION_UP:
                        btnIntroduce.setImageDrawable(getDrawable(R.drawable.introduction));
                        btnIntroduce.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                }
                return true;
            }
        });

    }
}