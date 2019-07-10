package cdd.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    private Button btnStartGame;
    private Button btnShowRank;
    private Button btnIntroduce;
    private Button btnSetBgm;

    PlayerRepo playerRepo=new PlayerRepo (this);
    Player player=new Player();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        String useName=bundle.getString("useName");
        player=playerRepo.getPlayerByName(useName, new DbCallBack.RankCallBack() {
            @Override
            public void dispalyRank(String name, int score, int rank) {

            }
        });
        //绑定开始游戏按钮
        btnStartGame  =  findViewById(R.id.start_game);
        btnSetBgm=findViewById(R.id.set_bgm);
        //开始游戏按钮点击事件：跳转到deskActivity
        btnStartGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , deskActivity.class);
                startActivity(intent);
            }
        });

        //音效设置按钮事件，跳转到bgmactivity

        btnSetBgm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , bgmActivity.class);
                startActivity(intent);
            }
        });

        //绑定查看排名按钮
        btnShowRank  =  (Button)findViewById(R.id.show_rank);
        //开始游戏按钮点击事件：跳转到deskActivity
        //开始游戏按钮点击事件：跳转到deskActivity
        btnShowRank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, rankActivity.class);
                Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
                bundle.putCharSequence("useName", player.player_name);	//保存用户名
                intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
                startActivity(intent);	//启动新的Activity
            }
        });
        btnIntroduce  =  findViewById(R.id.introduce);
        //开始游戏按钮点击事件：跳转到deskActivity
        btnIntroduce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , introduceActivity.class);
                startActivity(intent);
            }
        });
    }
}