package cdd.rank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uml.umlwork.R;

import java.util.HashMap;
import java.util.List;

import cdd.desk.model.role.Player;
import cdd.rank.contract.rankContract;
import cdd.rank.presenter.rankPresenter;
import cdd.tool.PlayerRepo;

public class rankActivity extends AppCompatActivity implements rankContract.View{
    private Button btnReturn;
    private TextView user_tv;		//显示用户名的TextView组件
    private TextView score_tv;		//显示用户分数的TextView组件
    private TextView rank_tv;       //获取显示用户名的TextView组件

    private TextView btn12;
    private TextView btn13;
    private TextView btn22;
    private TextView btn23;
    private TextView btn32;
    private TextView btn33;
    private TextView btn42;
    private TextView btn43;
    private TextView btn52;
    private TextView btn53;
    private TextView btn62;
    private TextView btn63;
    private TextView btn72;
    private TextView btn73;
    private TextView btn82;
    private TextView btn83;
    private TextView btn92;
    private TextView btn93;
    private TextView btn102;
    private TextView btn103;

    private rankContract.Presenter mPresenter;

    PlayerRepo playerRepo = new PlayerRepo(this);//TODO MODEL
   // Player player = new Player();//TODO MODEL

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

        btn12=findViewById(R.id.textView12);
        btn13=findViewById(R.id.textView13);
        btn22=findViewById(R.id.textView22);
        btn23=findViewById(R.id.textView23);
        btn32=findViewById(R.id.textView32);
        btn33=findViewById(R.id.textView33);
        btn42=findViewById(R.id.textView42);
        btn43=findViewById(R.id.textView43);
        btn52=findViewById(R.id.textView52);
        btn53=findViewById(R.id.textView53);
        btn62=findViewById(R.id.textView62);
        btn63=findViewById(R.id.textView63);
        btn72=findViewById(R.id.textView72);
        btn73=findViewById(R.id.textView73);
        btn82=findViewById(R.id.textView82);
        btn83=findViewById(R.id.textView83);
        btn92=findViewById(R.id.textView92);
        btn93=findViewById(R.id.textView93);
        btn102=findViewById(R.id.textView102);
        btn103=findViewById(R.id.textView103);

        //设置点击事件：跳转到deskActivity
        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
//                Intent intent = new Intent(rankActivity.this , MainActivity.class);
//                startActivity(intent);
            }
        });

        //页面跳转传递信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String useName = bundle.getString("useName");

        //设置presenter
        mPresenter = new rankPresenter(this);

        mPresenter.getPlayer(useName);
        Log.e("", "onCreate: usename" + useName );

    }

    @Override
    public void displayRank(String name, int score, int rank) {
        user_tv.setText(name);		                    //获取输入的用户名并显示到TextView组件中
        score_tv.setText(String.valueOf(score));		//获取输入的用户分数并显示到TextView组件中
        rank_tv.setText(String.valueOf(rank));		    //获取输入的用户排名并显示到TextView组件中



        PlayerRepo playerRepo=new PlayerRepo(this);
        List<HashMap<String,String>> list =playerRepo.getPlayerScore();
        String ary[][]=new String[200][2];
        for(int i=0;i<=9;i++)
        {
            ary[i][0]="***";
            ary[i][1]="0";
        }
        int num=0;
        for(HashMap<String,String>item : list) {
            ary[num][0]=item.get("name");
            ary[num][1]=item.get("score");
            num++;
        }
        for(int i=0;i<=num-2;i++)
        {
            int temp=i;
            for(int j=i+1;j<=num-1;j++)
            {
                if(Integer.parseInt(ary[temp][1])<Integer.parseInt(ary[j][1]))
                {
                    temp=j;
                }
            }
            if(temp!=i)
            {
                String temp_s1=ary[i][0];
                String temp_s2=ary[i][1];
                ary[i][0]=ary[temp][0];
                ary[i][1]=ary[temp][1];
                ary[temp][0]=temp_s1;
                ary[temp][1]=temp_s2;
            }
        }

        btn12.setText(ary[0][0]);
        btn13.setText(ary[0][1]);
        btn22.setText(ary[1][0]);
        btn23.setText(ary[1][1]);
        btn32.setText(ary[2][0]);
        btn33.setText(ary[2][1]);
        btn42.setText(ary[3][0]);
        btn43.setText(ary[3][1]);
        btn52.setText(ary[4][0]);
        btn53.setText(ary[4][1]);
        btn62.setText(ary[5][0]);
        btn63.setText(ary[5][1]);
        btn72.setText(ary[6][0]);
        btn73.setText(ary[6][1]);
        btn82.setText(ary[7][0]);
        btn83.setText(ary[7][1]);
        btn92.setText(ary[8][0]);
        btn93.setText(ary[8][1]);
        btn102.setText(ary[9][0]);
        btn103.setText(ary[9][1]);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}