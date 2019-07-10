package cdd.desk.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uml.umlwork.R;
import java.io.File;

import cdd.desk.model.role.Player;
import cdd.desk.model.role.PlayerRepo;
import cdd.menu.view.MainActivity;

import static android.os.SystemClock.sleep;

public class rankActivity extends AppCompatActivity {
    private Button btnReturn;
    private MediaPlayer mediaPlayer;//MediaPlayer对象

    PlayerRepo playerRepo = new PlayerRepo(this);
    Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String useName = bundle.getString("useName");
        //数据库问题数据库问题数据库问题数据库问题数据库问题数据库问题数据库问题数据库问题
//        player = playerRepo.getPlayerByName(useName);
        player.player_name="czfg";
        player.score=23;

        EditText user=(EditText)findViewById(R.id.editText1);		//获取显示用户名的TextView组件
        user.setText(player.player_name);		//获取输入的用户名并显示到TextView组件中

        EditText score=(EditText)findViewById(R.id.editText2);		//获取显示用户名的TextView组件
        score.setText(String.valueOf(player.score));		//获取输入的用户名并显示到TextView组件中

        int rankOfUse=1;

//        补充通过数据库查找用户排名的代码
//        补充通过数据库查找用户排名的代码补充通过数据库查找用户排名的代码补充通过数据库查找用户排名的代码

        EditText rank=(EditText)findViewById(R.id.editText3);		//获取显示用户名的TextView组件
        rank.setText(String.valueOf(rankOfUse));		//获取输入的用户名并显示到TextView组件中


        btnReturn  =  findViewById(R.id.button); //绑定返回菜单按钮
        //开始游戏按钮点击事件：跳转到deskActivity
        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(rankActivity.this, "请输入用户名！", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(rankActivity.this , mainActivityczf.class);
                startActivity(intent);
            }
        });
    }
}