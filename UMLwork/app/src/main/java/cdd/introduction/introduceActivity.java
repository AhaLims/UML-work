package cdd.introduction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uml.umlwork.R;

import cdd.menu.view.MainActivity;

public class introduceActivity extends AppCompatActivity {
//    private MediaPlayer mediaPlayer;//MediaPlayer对象
private Button btnReturn;
//    PlayerRepo playerRepo = new PlayerRepo(this);
//    Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        btnReturn  = findViewById(R.id.button1212); //绑定返回菜单按钮
        //开始游戏按钮点击事件：跳转到deskActivity
        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(introduceActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });


    }
}