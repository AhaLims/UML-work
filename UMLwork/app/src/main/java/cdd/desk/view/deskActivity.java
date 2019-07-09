package cdd.desk.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.uml.umlwork.R;
import java.util.ArrayList;
import java.util.List;
import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
import cdd.desk.contract.deskContract;
import cdd.desk.presenter.deskPresenter;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class deskActivity extends AppCompatActivity implements deskContract.View{

    private Button btnShowCards;
    private PlayerHandCardsViewGroup playerCardSetLayout;
    private ShowedCardsViewGroup playerShowCardsLayout;
    private ShowedCardsViewGroup leftRobotShowCardsLayout;
    private ShowedCardsViewGroup middleRobotShowCardsLayout;
    private ShowedCardsViewGroup rightRobotShowCardsLayout;
    private TextView leftRobotRemainTextView;
    private TextView middleRobotRemainTextView;
    private TextView rightRobotRemainTextView;
    private Context context;

    private deskContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        context = this;

        //绑定控件
        btnShowCards = findViewById(R.id.show_cards);
        playerCardSetLayout = findViewById(R.id.player_cardset_field);
        playerShowCardsLayout = findViewById(R.id.player_showcards_field);
        leftRobotShowCardsLayout = findViewById(R.id.robot1_cardset_field);
        middleRobotShowCardsLayout = findViewById(R.id.robot2_cardset_field);
        rightRobotShowCardsLayout = findViewById(R.id.robot3_cardset_field);
        leftRobotRemainTextView = findViewById(R.id.robot_remain1);
        middleRobotRemainTextView = findViewById(R.id.robot_remain2);
        rightRobotRemainTextView = findViewById(R.id.robot_remain3);

        //设置button监听事件
        btnShowCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCards();
            }
        });

        //设置presenter
        mPresenter = new deskPresenter(this);

        //以下用于测试
        /*
        Card card = new Card(15,CardColor.Diamond);
        Card card1 = new Card(14,CardColor.Diamond);
        Card card2 = new Card(13,CardColor.Diamond);
        Card card3 = new Card(12,CardColor.Diamond);
        Card card4 = new Card(11,CardColor.Diamond);
        Card card5 = new Card(10,CardColor.Diamond);
        Card card6 = new Card(9,CardColor.Diamond);
        Card card7 = new Card(8,CardColor.Diamond);

        List<Card> cards = new ArrayList<>();
        List<Card> empty_cards = new ArrayList<>();
        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);

        displayPlayerCards(cards);
        displayPlayerHandCards(cards);
        displayRobotCards(cards,1);
        displayRobotCards(cards,2);
        displayRobotCards(cards,3);*/
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
    }

    @Override
    public void displayRobotCards(List<Card> robotCards,int robot) {
        switch (robot)
        {
            case 1:
                leftRobotShowCardsLayout.displayCards(robotCards);
                break;

            case 2:
                middleRobotShowCardsLayout.displayCards(robotCards);
                break;

            case 3:
                rightRobotShowCardsLayout.displayCards(robotCards);
                break;

            default:
                throw new IllegalArgumentException("收到了不存在的机器人");
        }
    }

    @Override
    public void displayIrregularity(CharSequence message) {
        Toast.makeText(this, "不合法", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showCards() {
        List<Integer> arr;
        arr = playerCardSetLayout.getSelected();
        if(arr.isEmpty()) {
            displayIrregularity("请选择要出的牌");
            return ;
        }
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
                leftRobotRemainTextView.setText("剩余:"+robotCards.size());
                break;

            case 2:
                middleRobotRemainTextView.setText("剩余:"+robotCards.size());
                break;

            case 3:
                rightRobotRemainTextView.setText("剩余:"+robotCards.size());
                break;

            default:
                throw new IllegalArgumentException("收到了不存在的机器人");
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
