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
            //最小的牌
            Card c = list.get(0);
            dc.addCard(c);
        }
        else {
            //TODO 需要在这里补充机器人的策略
            //为了测试 这里不是先手也除牌了
            //Card c = list.get(0);

        }
        return dc;
    }


    //单牌出牌策略，测试通过
    private deliveredCardsGroup DanPai(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            if (1 == CurrentCards.getCardByIndex(i).compareTo(previous.getCardByIndex(0))) {
                dc.addCard(CurrentCards.getCardByIndex(i));
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //一对出牌策略，测试通过
    private deliveredCardsGroup YiDui(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 1; i++) {
            dc.addCard(CurrentCards.getCardByIndex(i));
            dc.addCard(CurrentCards.getCardByIndex(i + 1));
            if (cardsManager.jugdeType(dc.getCardsGroup()) == CardsType.yidui
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //三张出牌策略，测试通过
    private deliveredCardsGroup SanZhang(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 2; i++) {
            for (int j = 0; j < 3 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i+j));
            }
            if (cardsManager.jugdeType(dc.getCardsGroup())== CardsType.sanzhang
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //杂顺出牌策略,测试通过
    private deliveredCardsGroup ZaShun(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 4 ; i++) {
            dc.clear();
            for (int j = 0; j < 5 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i+j));
            }
            if (cardsManager.jugdeType(dc.getCardsGroup())== CardsType.zashun
                    && dc.getBiggestValue() > previous.getBiggestValue()) {
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //五同花出牌策略，测试通过
    private deliveredCardsGroup WuTongHua(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();

        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            dc.clear();
            dc.addCard(CurrentCards.getCardByIndex(i));
            int count = 1;
            for (int j = i + 1; j < CurrentCards.cardsAmount(); j++) {
                if ( CurrentCards.getCardByIndex(i).getColor() ==
                CurrentCards.getCardByIndex(j).getColor()) {
                    dc.addCard(CurrentCards.getCardByIndex(j));
                    count++;
                    System.out.println("Count: " + count);
                    System.out.println("机器人手牌权值 " + dc.getBiggestValue());
                    System.out.println("上家人手牌权值 " + previous.getBiggestValue());
                    if (count == 5 && dc.getBiggestValue() > previous.getBiggestValue()) {
                        return dc;
                    }
                }
            }
        }
        dc.clear();
        return dc;
    }

    //三带二出牌策略，测试通过
    private deliveredCardsGroup SanDaiEr(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        int base = 0;
        for (int k = 0; k < CurrentCards.cardsAmount()- 4; k++) {

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
                    break;
                }
            }
            //把首次符合条件的三张牌记下来
            dc.addCard(CurrentCards.getCardByIndex(base));
            dc.addCard(CurrentCards.getCardByIndex(base + 1));
            dc.addCard(CurrentCards.getCardByIndex(base + 2));
            System.out.println("当前得到的三张牌");
            dc.showDetail();

            //找两张牌
            for (int i = k; (i < CurrentCards.cardsAmount() - 1); i++) {
                if (i < base || i > base + 2) {
                    deliveredCardsGroup temp = new deliveredCardsGroup();
                    temp.addCard(CurrentCards.getCardByIndex(i));
                    temp.addCard(CurrentCards.getCardByIndex(i + 1));
                    if (cardsManager.jugdeType(temp.getCardsGroup()) == CardsType.yidui) {
                        base = i;
                        break;
                    }
                }
            }
            //首次符合两张牌取出来
            dc.addCard(CurrentCards.getCardByIndex(base));
            dc.addCard(CurrentCards.getCardByIndex(base + 1));
            System.out.println("当前得到的五张牌");
            dc.showDetail();

            if (dc.getBiggestValue() > previous.getBiggestValue()) {
                return dc;
            }
        }
        dc.clear();
        return dc;
    }

    //四带一出牌策略，测试通过
    private deliveredCardsGroup SiDaiYi(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        int base = 0;
        for (int k = 0; k < CurrentCards.cardsAmount()- 4; k++) {

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

