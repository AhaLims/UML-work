package cdd.rank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.uml.umlwork.R;
import cdd.desk.model.role.Player;
import cdd.desk.view.mainActivityczf;
import cdd.rank.contract.rankContract;
import cdd.rank.presenter.rankPresenter;
import cdd.tool.PlayerRepo;

public class rankActivity extends AppCompatActivity implements rankContract.View{
    private Button btnReturn;
    private EditText user_tv;		//显示用户名的TextView组件
    private EditText score_tv;		//显示用户分数的TextView组件
    private EditText rank_tv;       //获取显示用户名的TextView组件
    private rankContract.Presenter mPresenter;

    PlayerRepo playerRepo = new PlayerRepo(this);//TODO MODEL
    Player player = new Player();//TODO MODEL

    @Override
    public void setPresenter(rankContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        //绑定控件
        user_tv = findViewById(R.id.editText1);//显示用户名的TextView组件
        score_tv = findViewById(R.id.editText2);//显示用户分数的TextView组件
        rank_tv = findViewById(R.id.editText3);//显示用户排名的TextView组件
        btnReturn  =  findViewById(R.id.button); //绑定返回菜单按钮

        //设置点击事件：跳转到deskActivity
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

        //页面跳转传递信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String useName = bundle.getString("useName");

        //设置presenter
        mPresenter = new rankPresenter(this);

        mPresenter.getPlayer(useName);

    }

    @Override
    public void displayRank(String name, int score, int rank) {
        user_tv.setText(name);		                    //获取输入的用户名并显示到TextView组件中
        score_tv.setText(String.valueOf(score));		//获取输入的用户分数并显示到TextView组件中
        rank_tv.setText(String.valueOf(rank));		    //获取输入的用户排名并显示到TextView组件中
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}