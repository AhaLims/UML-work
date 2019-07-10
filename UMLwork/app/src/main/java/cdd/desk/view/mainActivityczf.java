package cdd.desk.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uml.umlwork.R;

import cdd.desk.model.role.PlayerRepo;
import cdd.desk.model.role.Player;
import cdd.desk.model.role.PlayerRepo;
import android.content.Context;
import cdd.desk.view.deskActivity;
import cdd.menu.contract.menuContract;
import cdd.menu.view.MainActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class mainActivityczf extends AppCompatActivity {
    private Button btnStartGame;
    PlayerRepo playerRepo=new PlayerRepo (this);
    Player player=new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainczf);


        btnStartGame  =  findViewById(R.id.button);

        //开始游戏按钮点击事件：跳转到deskActivity
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useName=((EditText)findViewById(R.id.useName)).getText().toString();	//获取输入的用户
                if(android.text.TextUtils.isEmpty(useName)  )
                {
                    Toast.makeText(mainActivityczf.this, "请输入用户名！", Toast.LENGTH_LONG).show();

                }
                else
                {
                    if(playerRepo.QueryByName(useName))
                    {
                        Intent intent=new Intent(mainActivityczf.this, MainActivity.class);
                        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
                        bundle.putCharSequence("useName", useName);	//保存用户名
                        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
                        startActivity(intent);	//启动新的Activity
                    }

                    {
                        Toast.makeText(mainActivityczf.this, "用户名未创建，将为你自动创建并进入主页面！", Toast.LENGTH_LONG).show();
                        player.player_name=useName;
                        player.score=0;
                        playerRepo.insert(player);
                        Intent intent=new Intent(mainActivityczf.this, MainActivity.class);
                        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
                        bundle.putCharSequence("useName", useName);	//保存用户名
                        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
                        startActivity(intent);	//启动新的Activity
                    }
                }
            }
        });
    }
}