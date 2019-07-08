package cdd.desk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cdd.desk.Card;

public class ShowedCardsViewGroup extends CardsViewGroup {
    public ShowedCardsViewGroup(Context context) {
        super(context);
        this.context = context;
    }

    public ShowedCardsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ShowedCardsViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    Context context;

    @Override
    protected void addCardView(Card card) {
        int row,col;
        final CardView mCardView = new CardView(context);

        //根据牌的点数和花色找到行和列
        row = card.getRow(card);
        col = card.getCol(card);

        //设置图片资源
        mCardView.setImageResource(CardImage.cardImages[row][col]);

        //设置Layout属性
        mCardView.setLayoutParams(params);

        //显示牌
        addView(mCardView);
    }
}
