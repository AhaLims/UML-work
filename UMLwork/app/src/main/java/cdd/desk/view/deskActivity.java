package cdd.desk.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.uml.umlwork.R;
import cdd.desk.contract.*;

public class deskActivity extends AppCompatActivity implements deskContract.View{

    private LinearLayout cardSetLayout;
    private CardViewSet mCardViewSet;
    private Button btn1,btn2;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        context = this;
        mCardViewSet = new CardViewSet();

        //绑定控件
        cardSetLayout = findViewById(R.id.player_cardset_field);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);


        //测试btn功能
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardView mCardView = new CardView(context);
                mCardView.setImageResource(R.drawable.diamond_8);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(100,100);
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.weight = 1;
                mCardView.setLayoutParams(params);

                mCardView.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                       if(mCardView.getPos() == State.DOWN) {
                           v.setTranslationY(-30);
                           mCardView.setPos(State.UP);
                       }
                       else {
                           v.setTranslationY(0);
                           mCardView.setPos(State.DOWN);
                       }
                    }

                });
                cardSetLayout.addView(mCardView);
                mCardViewSet.addCardView(mCardView);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSetLayout.removeAllViews();
                mCardViewSet.clearAllCardView();
            }
        });





    }

}
