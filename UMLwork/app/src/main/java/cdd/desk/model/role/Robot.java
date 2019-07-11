package cdd.desk.model.role;

import java.util.List;

import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardsType;
import cdd.desk.model.card.deliveredCardsGroup;



public class Robot extends Role {


    //根据上家的牌决定出牌的策略
    public deliveredCardsGroup deliver(deliveredCardsGroup previous) {
        List<Card> list = CurrentCards.getCardsGroup();
        deliveredCardsGroup dc = new deliveredCardsGroup();//暂时不出牌
        if (previous.hasCards() == false) {
            //先手出牌
            //暂时默认出最小的那张牌
            if (list.size() != 0) {
                Card c = list.get(0);
                dc.addCard(c);
            }
        }
        else {
            //前面玩家出的牌
            CardsType previousType = previous.getType();
            //TODO 需要在这里补充机器人的策略
            switch (previousType){
                case danzhang://单张牌
                    dc = DanPai(previous);
                    break;
                case yidui://两张相等的一对牌
                    dc = YiDui(previous);
                    break;
                case sanzhang://3张一样的牌
                    dc = SanZhang(previous);
                    break;
                case zashun://杂顺
                    dc = ZaShun(previous);
                    break;
                case wutonghua://五同花 注意必须是五张牌 必须是同花色的
                    dc = WuTongHua(previous);
                    break;
                case sandaier://三带二
                    dc = SanDaiEr(previous);
                    break;
                case sidaiyi://四带一
                    dc = SiDaiYi(previous);
                    break;
                case tonghuashun://同花顺
                    dc = TongHuaShun(previous);
                    break;
                case card0://不能出牌
                    break;
                default:
                        break;
            }

        }
        return dc;
    }


    //单牌出牌策略，测试通过
    private deliveredCardsGroup DanPai(deliveredCardsGroup previous) {
        System.out.println("这里在寻找单张");
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            dc.addCard(CurrentCards.getCardByIndex(i));
            if (dc.getBiggestValue() > previous.getBiggestValue()) {//比较牌的大小
                System.out.println("寻找到的单张");
                dc.showDetail();
                return dc;
            }
            dc.clear();
        }
        dc.clear();
        return dc;
    }

    //一对出牌策略，测试通过
    private deliveredCardsGroup YiDui(deliveredCardsGroup previous) {
        System.out.println("这里在寻找一对");
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 1; i++) {
            dc.addCard(CurrentCards.getCardByIndex(i));
            dc.addCard(CurrentCards.getCardByIndex(i + 1));
            if (cardsManager.jugdeType(dc.getCardsGroup()) == CardsType.yidui
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("寻找到的一对为");
                dc.showDetail();
                return dc;
            }
            dc.clear();
        }
        return dc;
    }

    //三张出牌策略，测试通过
    private deliveredCardsGroup SanZhang(deliveredCardsGroup previous) {
        System.out.println("这里在寻找三张");
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 2; i++) {
            for (int j = 0; j < 3 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i + j));
            }
            if (cardsManager.jugdeType(dc.getCardsGroup()) == CardsType.sanzhang
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("寻找到的三张牌为");
                dc.showDetail();
                return dc;
            }
            dc.clear();
        }
        return dc;
    }

    //杂顺出牌策略,测试通过
    private deliveredCardsGroup ZaShun(deliveredCardsGroup previous) {
        System.out.println("这里在寻找杂顺");
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 5 ; i++) {
            for (int j = 0; j < 5 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i +j ));
            }
            if (cardsManager.jugdeType(dc.getCardsGroup()) == CardsType.zashun
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("寻找到的杂顺为");
                dc.showDetail();
                return dc;
            }
            dc.clear();
        }
        dc.clear();
        return dc;
    }

    //五同花出牌策略，测试通过
    private deliveredCardsGroup WuTongHua(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        System.out.println("这里在寻找五同花");
        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {

            dc.addCard(CurrentCards.getCardByIndex(i));
            int count = 1;
            for (int j = i + 1; j < CurrentCards.cardsAmount(); j++) {
                if ( CurrentCards.getCardByIndex(i).getColor() ==
                CurrentCards.getCardByIndex(j).getColor()) {
                    dc.addCard(CurrentCards.getCardByIndex(j));
                    count++;
                    //System.out.println("Count: " + count);
                    //System.out.println("机器人手牌权值 " + dc.getBiggestValue());
                    //System.out.println("上家人手牌权值 " + previous.getBiggestValue());
                    if (count == 5 && dc.getBiggestValue() > previous.getBiggestValue()) {
                        System.out.println("寻找到的五同花为");
                        dc.showDetail();
                        return dc;
                    }
                }
            }
            dc.clear();
        }
        dc.clear();
        return dc;
    }

    //三带二出牌策略，测试通过
    private deliveredCardsGroup SanDaiEr(deliveredCardsGroup previous) {
        System.out.println("这里在寻找三带二");
        deliveredCardsGroup dc = new deliveredCardsGroup();
        int base = 0;
        boolean flag1 = false;//代表 找到了三张牌
        boolean flag2 = false;
        for (int k = 0; k < CurrentCards.cardsAmount()- 5; k++) {

            //找三张牌
            for (int i = k; i < CurrentCards.cardsAmount() - 2; i++) {
                deliveredCardsGroup temp = new deliveredCardsGroup();
                temp.addCard(CurrentCards.getCardByIndex(i));
                for (int j = 1; j < 3; j++) {
                    temp.addCard(CurrentCards.getCardByIndex(i + j));
                }
                System.out.println("临时牌");
                temp.showDetail();
                if (cardsManager.jugdeType(temp.getCardsGroup()) == CardsType.sanzhang) {
                    base = i;
                    flag1 = true;
                    break;
                }
            }
            //把首次符合条件的三张牌记下来
            if(flag1 == true) {
                dc.addCard(CurrentCards.getCardByIndex(base));
                dc.addCard(CurrentCards.getCardByIndex(base + 1));
                dc.addCard(CurrentCards.getCardByIndex(base + 2));
                System.out.println("当前得到的三张牌");
                dc.showDetail();
            }

            //找两张牌
            for (int i = k; (i < CurrentCards.cardsAmount() - 1); i++) {
                if (i < base || i > base + 2) {
                    deliveredCardsGroup temp = new deliveredCardsGroup();
                    temp.addCard(CurrentCards.getCardByIndex(i));
                    temp.addCard(CurrentCards.getCardByIndex(i + 1));
                    if (cardsManager.jugdeType(temp.getCardsGroup()) == CardsType.yidui) {
                        base = i;
                        flag2 = true;
                        break;
                    }
                }
            }
            if(flag2 == true) {
                //首次符合两张牌取出来
                dc.addCard(CurrentCards.getCardByIndex(base));
                dc.addCard(CurrentCards.getCardByIndex(base + 1));
                System.out.println("当前得到的五张牌");
                dc.showDetail();
            }

            if (flag1 == true && flag2 == true && dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("三带二");
                dc.showDetail();
                return dc;
            }
            dc.clear();
        }
        dc.clear();
        return dc;
    }

    //还没改
    //四带一出牌策略，测试通过
    private deliveredCardsGroup SiDaiYi(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        int base = 0;
        for (int k = 0; k < CurrentCards.cardsAmount()- 4; k++) {
            dc.clear();
            //找四张牌
            for (int i = k; i < CurrentCards.cardsAmount() - 3; i++) {
                deliveredCardsGroup temp = new deliveredCardsGroup();
                temp.addCard(CurrentCards.getCardByIndex(i));
                for (int j = 1; j < 4; j++) {
                    temp.addCard(CurrentCards.getCardByIndex(i + j));
                }
                System.out.println("临时牌");
                temp.showDetail();
                if (temp.getCardByIndex(0).getPoints() == temp.getCardByIndex(3).getPoints()) {
                    base = i;
                    break;
                }
            }

            //把首次符合条件的四张牌记下来
            dc.addCard(CurrentCards.getCardByIndex(base));
            dc.addCard(CurrentCards.getCardByIndex(base + 1));
            dc.addCard(CurrentCards.getCardByIndex(base + 2));
            dc.addCard(CurrentCards.getCardByIndex(base + 3));

            //找一张牌
            for (int i = k; (i < CurrentCards.cardsAmount()); i++) {
                if (i < base || i > base + 3) {
                    base = k;
                    break;
                }
            }
            //首次符合一张牌取出来
            dc.addCard(CurrentCards.getCardByIndex(base));
            if (dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("四带一");
                dc.showDetail();
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //同花顺出牌策略，测试通过
    private deliveredCardsGroup TongHuaShun(deliveredCardsGroup previous) {

        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 4 ; i++) {
            dc.clear();
            dc.addCard(CurrentCards.getCardByIndex(i));
            for (int j = 1; j < 5 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i+j));
            }
            System.out.println("临时牌");
            dc.showDetail();
            if (cardsManager.jugdeType(dc.getCardsGroup())== CardsType.tonghuashun
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                System.out.println("结构");
                dc.showDetail();
                return dc;
            }
        }
        dc.clear();
        return dc;
    }
}

