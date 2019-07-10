package cdd.desk.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.uml.umlwork.R;
import java.util.List;
import cdd.desk.model.card.Card;
import cdd.desk.contract.deskContract;
import cdd.desk.presenter.deskPresenter;
import cdd.menu.view.MainActivity;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class deskActivity extends AppCompatActivity implements deskContract.View{

    private ImageButton btnShowCards;
    private ImageButton btnSkip;
    private ImageButton btnReSelect;
    private ImageButton btnExitGame;
    private Button tiaozhuan;
    private PlayerHandCardsViewGroup playerCardSetLayout;
    private ShowedCardsViewGroup playerShowCardsLayout;
    private ShowedCardsViewGroup leftRobotShowCardsLayout;
    private ShowedCardsViewGroup middleRobotShowCardsLayout;
    private ShowedCardsViewGroup rightRobotShowCardsLayout;
    private TextView leftRobotRemainTextView;
    private TextView middleRobotRemainTextView;
    private TextView rightRobotRemainTextView;
    private TextView timer0TextView;
    private TextView timer1TextView;
    private TextView timer2TextView;
    private TextView timer3TextView;
    private Context context;

    //由于不知道怎么在ui层判断轮次 所以只能写4个timer了
    private CountDownTimer timer0 = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer0TextView.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            timer0TextView.setText("");
            if(mPresenter.isFirstHand(0)) {
                playerCardSetLayout.selectFirstCard();
                showCards();
            }
            else
                mPresenter.playerPass();
        }
    };
    //TODO 记得改回来
    private CountDownTimer timer1 = new CountDownTimer(1000000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer1TextView.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            timer1TextView.setText("");
        }
    };
    private CountDownTimer timer2 = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer2TextView.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            timer2TextView.setText("");
        }
    };
    private CountDownTimer timer3 = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer3TextView.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            timer3TextView.setText("");
        }
    };

    private deskContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        context = this;

        //绑定控件
        btnShowCards = findViewById(R.id.show_cards);
        btnSkip = findViewById(R.id.skip);
        btnReSelect = findViewById(R.id.reselect);
        btnExitGame = findViewById(R.id.exit_game_btn);
        btnExitGame.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
        tiaozhuan = findViewById(R.id.tiaozhuan);
        playerCardSetLayout = findViewById(R.id.player_cardset_field);
        playerShowCardsLayout = findViewById(R.id.player_showcards_field);
        leftRobotShowCardsLayout = findViewById(R.id.robot1_cardset_field);
        middleRobotShowCardsLayout = findViewById(R.id.robot2_cardset_field);
        rightRobotShowCardsLayout = findViewById(R.id.robot3_cardset_field);
        leftRobotRemainTextView = findViewById(R.id.robot_remain1);
        middleRobotRemainTextView = findViewById(R.id.robot_remain2);
        rightRobotRemainTextView = findViewById(R.id.robot_remain3);
        timer0TextView = findViewById(R.id.timer0);
        timer1TextView = findViewById(R.id.timer1);
        timer2TextView = findViewById(R.id.timer2);
        timer3TextView = findViewById(R.id.timer3);

        //设置button监听事件
        btnExitGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        btnExitGame.setImageDrawable(getDrawable(R.drawable.exit_game_foucused));
                        btnExitGame.setScaleType(ImageView.ScaleType.CENTER_INSIDE);//ImageView.ScaleType.FIT_CENTER
                        popEscapeDialog();
                        break;

                    case MotionEvent.ACTION_UP:
                        btnExitGame.setImageDrawable(getDrawable(R.drawable.exit_game));
                        btnExitGame.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                }
                return true;
            }
        });

        btnExitGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popEscapeDialog();
            }
        });

        btnShowCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCards();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.playerPass();
            }
        });

        btnReSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerCardSetLayout.reSelect();
            }
        });

        tiaozhuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popResultDialog(0,0);
            }
        });

        //设置presenter
        mPresenter = new deskPresenter(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            popEscapeDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(@NonNull deskContract.Presenter presenter) {
            mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    //打印玩家的手牌
    @Override
    public void displayPlayerHandCards(List<Card> playerCards) {
        playerCardSetLayout.displayCards(playerCards);
    }

    //打印玩家出的牌
    @Override
    public void displayPlayerCards(List<Card> playerCards) {
        playerShowCardsLayout.displayCards(playerCards);
        timer0.cancel();
        //System.out.print("这个时候应该显示 出的牌");
    }

    @Override
    public void displayRobotCards(List<Card> robotCards,int robot) {
        switch (robot)
        {
            case 1:
                leftRobotShowCardsLayout.displayCards(robotCards);
                timer1.cancel();
                break;

            case 2:
                middleRobotShowCardsLayout.displayCards(robotCards);
                timer2.cancel();
                break;

            case 3:
                rightRobotShowCardsLayout.displayCards(robotCards);
                timer3.cancel();
                break;

            default:
                throw new IllegalArgumentException("收到了不存在的机器人");
        }
    }

    @Override
    public void displayIrregularity(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showCards() {
        List<Integer> arr;
        arr = playerCardSetLayout.getSelected();
        if(arr.isEmpty()) {
            displayIrregularity("请选择要出的牌");
            return ;
        }
        mPresenter.playerShowCards(arr);

        System.out.print("选中的牌的index：");
        for(Integer i:arr) {
            System.out.print( i );
        }
        System.out.println();
    }

    public void setRobotHandCard(List<Card> robotCards,int robot)
    {
        //注意：setText如果参数是int类型会被当成resourcesID使用
        switch (robot)
        {
            case 1:
                leftRobotRemainTextView.setText("剩余:"+ robotCards.size());
                break;

            case 2:
                middleRobotRemainTextView.setText("剩余:"+ robotCards.size());
                break;

            case 3:
                rightRobotRemainTextView.setText("剩余:"+ robotCards.size());
                break;

            default:
                throw new IllegalArgumentException("收到了不存在的机器人");
        }
    }

    public void displayPass(int role)
    {
        switch (role)
        {
            case 0:
                timer0.cancel();
                playerShowCardsLayout.displayPass();
            case 1:
                timer1.cancel();
                leftRobotShowCardsLayout.displayPass();
                break;
            case 2:
                timer2.cancel();
                middleRobotShowCardsLayout.displayPass();
                break;
            case 3:
                timer3.cancel();
                rightRobotShowCardsLayout.displayPass();
                break;
            default:
        }
    }

    public void popResultDialog(int winner,int score)
    {
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.game_end_dialog_view, null);   // 布局文件，自定义
        TextView winner_tv = view.findViewById(R.id.winner_tv);
        TextView player_score_tv = view.findViewById(R.id.player_score_tv);

        switch (winner)
        {
            case 0:
                winner_tv.setText("赢家:玩家");
                break;
            case 1:
                winner_tv.setText("赢家:机器人1");
                break;
            case 2:
                winner_tv.setText("赢家:机器人2");
                break;
            case 3:
                winner_tv.setText("赢家:机器人3");
                break;
            default:
        }
        player_score_tv.setText("玩家得分:"+score);

        builder.setTitle("游戏结果");
        builder.setIcon(R.mipmap.ic_launcher);//设置对话框icon

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"再来一把", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"不了不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(deskActivity.this , MainActivity.class);
                startActivity(intent);
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    public void popEscapeDialog()
    {
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.escape_dialog_view, null);   // 布局文件，自定义

        builder.setTitle("逃跑");
        builder.setIcon(R.mipmap.ic_launcher);//设置对话框icon

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"继续游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"逃跑", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               mPresenter.escape();

               Intent intent = new Intent(deskActivity.this , MainActivity.class);
               startActivity(intent);
               dialog.dismiss();//关闭对话框
            }
        });
        dialog.show();

    }

    public void startTimer(int role)
    {
        switch (role)
        {
            case 0:
                timer0.start();
                break;
            case 1:
                timer1.start();
                break;
            case 2:
                timer2.start();
                break;
            case 3:
                timer3.start();
                break;
            default:
        }
    }

    /*
    *函数名：removeShowedCards
    * 功能：清除玩家或机器人出牌区的牌
    * 参数：0.玩家 1.左边机器人 2.中间机器人 3.右边机器人
    * */
    public void removeShowedCards(int select)
    {
        switch (select)
        {
            case 0:
                playerShowCardsLayout.removeAllViews();
                break;
            case 1:
                leftRobotShowCardsLayout.removeAllViews();
                break;
            case 2:
                middleRobotShowCardsLayout.removeAllViews();
                break;
            case 3:
                rightRobotShowCardsLayout.removeAllViews();
            default:
        }
    }

}
