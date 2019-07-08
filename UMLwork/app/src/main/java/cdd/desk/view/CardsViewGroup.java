package cdd.desk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import cdd.desk.Card;

public abstract  class CardsViewGroup extends FrameLayout {

    public CardsViewGroup.LayoutParams params;//牌的布局属性参数

    public CardsViewGroup(Context context) {
        super(context);
        //初始化Layout属性
        params = new CardsViewGroup.LayoutParams(100,100);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //params.weight = 1;
    }

    public CardsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化Layout属性
        params = new CardsViewGroup.LayoutParams(100,100);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //params.weight = 1;
    }

    public CardsViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化Layout属性
        params = new CardsViewGroup.LayoutParams(100,100);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //params.weight = 1;
    }

    protected abstract void addCardView(Card card);

    public void displayCards(List<Card> playerCards) {
        //先把牌清空
        removeAllViews();

        //获取牌的张数
        int length = playerCards.size();

        //显示每一张牌
        for(int i = 0;i < length;i++)
            addCardView(playerCards.get(i));
    }
    /**
     *函数名：onLayout
     * 功能：第一次完成layout函数后，调用onLayout以达到扑克牌重叠的效果
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int layoutWidth = getMeasuredWidth();//获取容器宽度
        //int layoutHeight = getMeasuredHeight();

        //对于每一个子View
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);

            //计算重叠

            //获取卡牌的高度和宽度
            float viewWidth = view.getMeasuredWidth();
            float viewHeight = view.getMeasuredHeight();

            //卡牌的露出的宽度（硬编码）
            float offset = (float) (viewWidth*0.35);

            //计算这组牌显示的宽度
            float totalWidth = (getChildCount() - 1)*offset + viewWidth;

            //第一张卡牌开始的位置
            float startLoc = (layoutWidth - totalWidth)/2;

            //调整卡牌的位置
            view.layout((int)(startLoc+i*offset),30,(int)(startLoc+i*offset+viewWidth),(int)(viewHeight));
        }
    }



    //下面这两个函数是为了onLayout函数中能正确获取子控件的宽度而设置的
    //在网上找的，不清楚原理
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * @param child                   子View
     * @param parentWidthMeasureSpec  宽度测量规格
     * @param widthUsed               父view在宽度上已经使用的距离
     * @param parentHeightMeasureSpec 高度测量规格
     * @param heightUsed              父view在高度上已经使用的距离
     */
    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }
}
