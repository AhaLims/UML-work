package cdd.desk.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uml.umlwork.R;

import java.util.ArrayList;
import java.util.List;

import cdd.desk.Card;
import cdd.desk.CardColor;
import cdd.desk.contract.deskContract;

public class deskActivity extends AppCompatActivity implements deskContract.View{

    private Button btnShowCards;
    private LinearLayout playerCardSetLayout;
    private LinearLayout playerShowCardsLayout;
    private LinearLayout leftRobotShowCardsLayout;
    private LinearLayout middleRobotShowCardsLayout;
    private LinearLayout rightRobotShowCardsLayout;
    private CardViewSet mCardViewSet;
    private Context context;
    LinearLayout.LayoutParams params;//牌的布局属性参数

    private deskContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        context = this;
        mCardViewSet = new CardViewSet();

        //初始化Layout属性
        params = new LinearLayout.LayoutParams(100,100);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.weight = 1;

        //绑定控件
        btnShowCards = findViewById(R.id.show_cards);
        playerCardSetLayout = findViewById(R.id.player_cardset_field);
        playerShowCardsLayout = findViewById(R.id.player_showcards_field);
        leftRobotShowCardsLayout = findViewById(R.id.robot1_cardset_field);
        middleRobotShowCardsLayout = findViewById(R.id.robot2_cardset_field);
        rightRobotShowCardsLayout = findViewById(R.id.robot3_cardset_field);

        //设置button监听事件
        btnShowCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCards();
            }
        });






        //以下用于测试
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
        displayRobotCards(cards,3);

    }

    public int getRow(Card card) {
        int row = card.getWeight() - 3;
        return row;
    }

    public int getCol(Card card) {
        int col = card.getCardColor().ordinal();
        return  col;
    }

    //打印玩家的手牌
    @Override
    public void displayPlayerHandCards(List<Card> playerCards) {
        //先把牌清空
        playerCardSetLayout.removeAllViews();
        mCardViewSet.clearAllCardView();

        //获取牌的张数
        int length = playerCards.size();

        //对于每一张牌
        for(int i = 0;i < length;i++)
            displayCard(playerCards.get(i),playerCardSetLayout);
    }

    //打印玩家出的牌
    @Override
    public void displayPlayerCards(List<Card> playerCards) {
        //先把牌清空
        playerShowCardsLayout.removeAllViews();

        //获取牌的张数
        int length = playerCards.size();

        //显示每一张牌
        for(int i = 0;i < length;i++)
            displayCard(playerCards.get(i),playerShowCardsLayout);
    }

    /*
    *函数名：displayRobotCards
    * 参数：机器人要出的牌集合，机器人的标识
    * 功能：将机器人出的牌打印到对的位置
     */
    @Override
    public void displayRobotCards(List<Card> robotCards,int robot) {
        LinearLayout layout;

        switch (robot)
        {
            case 1:
                layout = leftRobotShowCardsLayout;
                break;

            case 2:
                layout = middleRobotShowCardsLayout;
                break;

            case 3:
                layout = rightRobotShowCardsLayout;
                break;

            default:
                throw new IllegalArgumentException("收到了不存在的机器人");
        }
        //先把牌清空
        layout.removeAllViews();

        //获取牌的张数
        int length = robotCards.size();

        //显示每一张牌
        for(int i = 0;i < length;i++)
            displayCard(robotCards.get(i),layout);
    }


    /*
    *函数名：showCard
    * 参数：牌 和 需要输出到的layout
    * 功能：将牌添加到layout的最后， 并设置牌的UI属性
     */
    @Override
    public void displayCard(Card card, LinearLayout layout) {
        int row,col;
        final CardView mCardView = new CardView(context);

        //根据牌的点数和花色找到行和列
        row = getRow(card);
        col = getCol(card);

        //设置图片资源
        mCardView.setImageResource(CardImage.cardImages[row][col]);

        //设置Layout属性
        mCardView.setLayoutParams(params);

        //如果是玩家的手牌，设置牌的点击事件
        if(layout == playerCardSetLayout) {
            mCardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //改变牌的位置
                    if (mCardView.getPos() == State.DOWN) {
                        v.setTranslationY(-30);
                        mCardView.setPos(State.UP);
                    } else {
                        v.setTranslationY(0);
                        mCardView.setPos(State.DOWN);
                    }
                }

            });
        }

        //把牌插入集合
        layout.addView(mCardView);
        if(layout == playerCardSetLayout)
            mCardViewSet.addCardView(mCardView);
    }

    @Override
    public void displayIrregularity() {
        Toast.makeText(this, "不合法", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCards() {
        List<Integer> arr;
        arr = mCardViewSet.getSelected();
        System.out.print("选中的牌的index：");
        for(Integer i:arr) {
            System.out.print( i );
        }
        System.out.println();
    }


}
