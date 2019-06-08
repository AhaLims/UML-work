package com.zzw.cdd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by 子文 on 2017/6/6.
 */
//没有看到重新绘图的函数...?怎么把之前画的给取消掉
public class CardsHolder {
    int value = 0;
    int cardsType = 0;
    int[] cards;
    Bitmap cardImage;
    int playerId;
    Context context;

    public CardsHolder(int[] cards, int id, Context context) {
        this.playerId = id;
        this.cards = cards;
        this.context = context;
        cardsType = CardsManager.getType(cards);
        value = CardsManager.getValue(cards);//用value 就不用定义比较规则了?好像这样子更好...
    }
    //所以这个是绘制出出来的牌??/////////////////////////////////////////////////////////
    //给定绘制的起点 以及绘制的方向(横向or纵向)
    public void paint(Canvas canvas, int left, int top, int dir) {
        Rect src = new Rect();
        Rect des = new Rect();//rect 等会看看是什么
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);//类型为描边
        paint.setColor(Color.BLACK);//画笔的颜色
        paint.setStrokeWidth(1);//stroke意思为 描边

        //设置图形重叠时的显示方式 setXfermode(Xfermode xfermode)
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //SRC_IN:只在源图像和目标图像相交的地方绘制【源图像】
        //为什么只绘制相交的地方....逻辑没懂
        for(int i = 0; i < cards.length; i++) {//应该是一张牌一张牌的绘制
            int row = CardsManager.getImageRow(cards[i]);
            int col = CardsManager.getImageCol(cards[i]);//row col是用来获取图片资源的
            
            /*
            public static Bitmap decodeResource (Resources res, int id, BitmapFactory.Options opts)
            参数
            res     包含图像数据的资源对象  -----------噢....这里携带资源的对象就是之前的context
            id  图像数据的资源的id
            opts    可以为空，控制采样率和实付图像完全被解码的选项，或者仅仅返回大小
            返回值 */
            cardImage = BitmapFactory.decodeResource(context.getResources(),
                    CardImage.cardImages[row][col]);
            if(dir == CardsType.direction_Vertical) {//方向是纵向的
                row = CardsManager.getImageRow(cards[i]);
                col = CardsManager.getImageCol(cards[i]);
                //Rect(int left, int top, int right, int bottom) 
                //left和top属性是rect左上角点的坐标  right和bottom是右下角点的坐标
                src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
                des.set((int) (left * MainActivity.SCALE_HORIAONTAL),//des就不知道在干什么了
                        (int) ((top + i * 15) * MainActivity.SCALE_VERTICAL),
                        (int) ((left + 40) * MainActivity.SCALE_HORIAONTAL),
                        (int) ((top + 60 + i * 15) * MainActivity.SCALE_VERTICAL));
            }
            //具体这些参数(什么15 20 60之类的)也不知道他是怎么来的
            else {//方向是横向的
                row = CardsManager.getImageRow(cards[i]);
                col = CardsManager.getImageCol(cards[i]);
                src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
                des.set((int) ((left + i * 20) * MainActivity.SCALE_HORIAONTAL),
                        (int) (top * MainActivity.SCALE_VERTICAL),
                        (int) ((left + 40 + i * 20) * MainActivity.SCALE_HORIAONTAL),
                        (int) ((top + 60) * MainActivity.SCALE_VERTICAL));
            }
            RectF rectF = new RectF(des);//因为下面 drawRoundRect 必须接受rectF
            canvas.drawRoundRect(rectF, 5, 5, paint);//绘制 圆角矩形 x,y轴的半径都为5

            //void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)
            /*
            参数 
            Rect src：对原图片的裁剪区域 
            RectF dst：将（裁剪完的）原图片绘制到View控件上的区域

            第2个参数：需要将图片哪个区域进行裁剪，如果传 null 或者 bitmap.getWidth/bitmap.getHeight 表示不裁剪，将原图完整绘制 
            第3个参数：处理后的图片需要绘制到View控件上的哪个区域，图片小于指定区域-放大；图片大于指定区域-缩小

            */
            //src 是对图片进行裁剪（相当于把src放到一个小箱子里进行裁剪） des是将裁剪后的图片放上画布上面(绝对定位)
            canvas.drawBitmap(cardImage, src, des, paint);//每次绘制一个?
            //噢....paint 用在下面的canvas画图，成为一个参数了
        }
    }
}
