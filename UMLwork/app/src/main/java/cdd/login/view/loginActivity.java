package cdd.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uml.umlwork.R;

import cdd.login.contract.loginContract;
import cdd.login.presenter.loginPresenter;
import cdd.tool.PlayerRepo;
import cdd.desk.model.role.Player;
import cdd.menu.view.MainActivity;

public class loginActivity extends AppCompatActivity implements loginContract.View{
    private Button btnStartGame;
    private loginContract.Presenter mPresenter;

    //TODO MODEL
    PlayerRepo playerRepo=new PlayerRepo (this);
    //Player player=new Player();

    @Override
    public void setPresenter(loginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set presenter
        mPresenter = new loginPresenter(this);

        //显示界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainczf);

        //bind button
        btnStartGame  =  findViewById(R.id.button);

        //开始游戏按钮点击事件：跳转到deskActivity
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useName=((EditText)findViewById(R.id.useName)).getText().toString();	//获取输入的用户
                if(android.text.TextUtils.isEmpty(useName)  )
                {
                    Toast.makeText(loginActivity.this, "请输入用户名！", Toast.LENGTH_LONG).show();

                }
                else
                {
                    mPresenter.login(useName);

//TODO NOT MVP
//                    if(playerRepo.QueryByName(useName))
//                    {
//                        Intent intent=new Intent(mainActivityczf.this, MainActivity.class);
//                        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
//                        bundle.putCharSequence("useName", useName);	//保存用户名
//                        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
//                        startActivity(intent);	//启动新的Activity
//                    }
//                    else
//                    {
//                        Toast.makeText(mainActivityczf.this, "用户名未创建，将为你自动创建并进入主页面！", Toast.LENGTH_LONG).show();
//                        player.player_name=useName;
//                        player.score=0;
//                        playerRepo.insert(player);
//                        Intent intent=new Intent(mainActivityczf.this, MainActivity.class);
//                        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
//                        bundle.putCharSequence("useName", useName);	//保存用户名
//                        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
//                        startActivity(intent);	//启动新的Activity
//                    }
                    finish();
                }
            }
        });
    }

    public void SkipToMenu(String name)
    {
        Intent intent=new Intent(loginActivity.this, MainActivity.class);
        Bundle bundle=new Bundle();	//创建并实例化一个Bundle对象
        bundle.putCharSequence("useName", name);	//保存用户名
        intent.putExtras(bundle);	//将Bundle对象添加到Intent对象中
        startActivity(intent);	//启动新的Activity
    }

    public void ToastUserNotExist()
    {
        Toast.makeText(loginActivity.this, "用户名未创建，将为你自动创建并进入主页面！", Toast.LENGTH_LONG).show();
    }
}