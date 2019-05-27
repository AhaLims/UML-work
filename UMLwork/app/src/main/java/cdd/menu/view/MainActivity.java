package cdd.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.uml.umlwork.R;

import cdd.bgm.view.bgmActivity;
import cdd.desk.view.deskActivity;
import cdd.menu.contract.menuContract;

;

public class MainActivity extends AppCompatActivity implements menuContract.View{
    private Button btnStartGame;
    private Button btnSetBgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定按钮
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
    }


}
