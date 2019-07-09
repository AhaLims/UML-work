package com.zzw.cdd;

/**
 * Created by 子文 on 2017/6/4.
 */
//怎么还能用interface....不是用枚举类型的呢...
//好像interface的确比枚举类型方便一点?
public interface CardsType {

    int danzhang = 1;
    int yidui = 2;
    int sange = 3;
    int shun = 4;
    int zashun = 5;
    int tonghuashun = 6;
    int tonghuawu = 7;
    int sangedaiyidui = 8;
    int sigedaidanzhang = 9;
    int error = 10;

    int direction_Horizontal = 0;//方向是横向的
    int direction_Vertical = 1;//方向是纵向的
}
