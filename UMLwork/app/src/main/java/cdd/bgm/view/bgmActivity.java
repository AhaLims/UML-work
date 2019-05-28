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
    private boolean isPause=false;//是否暂停
    private File file;//要播放的文件

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgm);

        btnReturn  =  findViewById(R.id.returnButton); //绑定返回菜单按钮
       /* final Button btnStart1=(Button)findViewById(R.id.startButton1);//播放1
        final Button btnStart2=(Button)findViewById(R.id.startButton2);//播放2
        final Button btnStart3=(Button)findViewById(R.id.startButton3);//播放3*/

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

        //对MediaPlayer对象添加事件监听，当播放完成时重新开始音乐播放
        /*mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                play();
            }
        });*/
    }

    //播放音乐的方法


    public void playMusic1(View view)//播放按钮1匹配
    {
        try
        {
            mediaPlayer.reset();//从新设置要播放的音乐
            //mediaPlayer.setDataSource(file.getAbsolutePath());
            //mediaPlayer.prepare();//预加载音频
            mediaPlayer = MediaPlayer.create(this, R.raw.leisure);
            mediaPlayer.start();//播放音乐
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("err",e.getMessage());
        }
        return ;
    }

    public void playMusic2(View view)
    {

    }

    public void playMusic3(View view)
    {

    }
}
