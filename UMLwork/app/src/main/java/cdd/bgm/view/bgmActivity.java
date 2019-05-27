package cdd.bgm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.uml.umlwork.R;

import cdd.bgm.contract.bgmContract;
import cdd.menu.view.MainActivity;

public class bgmActivity extends AppCompatActivity implements bgmContract.View{
    private ImageButton btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgm);

        //绑定返回菜单按钮
        btnReturn  =  findViewById(R.id.returnButton);

        //开始游戏按钮点击事件：跳转到deskActivity
       btnReturn.setOnClickListener(new View.OnClickListener()
       {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(bgmActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void playMusic1(View view)
    {

    }

    public void playMusic2(View view)
    {

    }

    public void playMusic3(View view)
    {

    }
}
