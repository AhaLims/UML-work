package cdd.desk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.util.LinkedList;
import java.util.List;

public class PlayerHandCardsViewGroup extends CardsViewGroup {

    Context context;

    public PlayerHandCardsViewGroup(Context context) {
        super(context);
        this.context = context;
    }

    public PlayerHandCardsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PlayerHandCardsViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void addCardView(cdd.desk.model.card.Card card) {
        int row,col;
        final CardView mCardView = new CardView(context);

        //根据牌的点数和花色找到行和列
        row = card.getRow();
        col = card.getCol();

        //设置图片资源
        mCardView.setImageResource(CardImage.cardImages[row][col]);

        //设置Layout属性
        mCardView.setLayoutParams(params);

        //设置牌点击弹起动作
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

        //显示牌
        addView(mCardView);
    }

    public List<Integer> getSelected()
    {
        List<Integer> selected  = new LinkedList<>();

        for(int i = 0;i<getChildCount();i++)
        {
            CardView temp = (CardView)(getChildAt(i));
            if(temp.getPos() == State.UP)
                selected.add(i);
        }
        return selected;
    }
}
