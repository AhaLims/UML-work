package cdd.bgm.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.uml.umlwork.R;

import java.io.File;

import cdd.bgm.contract.bgmContract;
import cdd.menu.view.MainActivity;

public class bgmActivity extends AppCompatActivity implements bgmContract.View{
    private ImageButton btnReturn;
    private MediaPlayer mediaPlayer;//MediaPlayer对象

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgm);

        btnReturn  =  findViewById(R.id.returnButton); //绑定返回菜单按钮

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

    //播放音乐的方法
    public void playMusic1(View v)//播放按钮1匹配
    {
            if(mediaPlayer==null)//判断当前播放器是否在工作，若没有工作则开始播放，若正在工作则把当前音乐停止之后换成music 1
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.m1);
                mediaPlayer.start();//播放音乐
                mediaPlayer.setLooping(true);//这是播放器循环播放当前music的方法
            }
            else
            {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(this, R.raw.m1);
                mediaPlayer.start();//播放音乐
                mediaPlayer.setLooping(true);
            }

    }

    public void playMusic2(View v)//匹配播放按钮2
    {
        if(mediaPlayer==null)//判断当前播放器是否在工作，若没有工作则开始播放，若正在工作则把当前音乐停止之后换成music 2
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.m2);
            mediaPlayer.start();//播放音乐
            mediaPlayer.setLooping(true);
        }
        else
        {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.m2);
            mediaPlayer.start();//播放音乐
            mediaPlayer.setLooping(true);
        }
    }

    public void playMusic3(View v)//匹配播放按钮3
    {
        if (mediaPlayer==null)//判断当前播放器是否在工作，若没有工作则开始播放，若正在工作则把当前音乐停止之后换成music 3
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.m3);
            mediaPlayer.start();//播放音乐
            mediaPlayer.setLooping(true);
        }
        else
        {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.m3);
            mediaPlayer.start();//播放音乐
            mediaPlayer.setLooping(true);
        }
    }
}
