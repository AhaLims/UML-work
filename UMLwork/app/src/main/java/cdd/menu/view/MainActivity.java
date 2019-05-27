package cdd.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.uml.umlwork.R;
import cdd.desk.view.deskActivity;
import cdd.menu.contract.menuContract;

;

public class MainActivity extends AppCompatActivity implements menuContract.View{
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定开始游戏按钮
        btnStartGame  =  findViewById(R.id.start_game);

        //开始游戏按钮点击事件：跳转到deskActivity
        btnStartGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , deskActivity.class);
                startActivity(intent);
            }
        });
    }


}
