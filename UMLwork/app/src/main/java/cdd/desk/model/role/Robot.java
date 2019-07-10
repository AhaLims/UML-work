package cdd.desk.model.role;

import java.util.List;

import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardColor;
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
            switch (previous.getType()) {
                case danzhang: dc = DanPai(previous); break;
                case yidui: dc = YiDui(previous); break;
                case sanzhang:dc = SanZhang(previous); break;
                case zashun:dc = ZaShun(previous); break;
                case wutonghua: dc = WuTongHua(previous); break;
                case sandaier: dc = SanDaiEr(previous); break;
                default:Card c = list.get(0);dc.addCard(c);
            }
        }
        return dc;
    }



    //单牌出牌策略
    private deliveredCardsGroup DanPai(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            if (1 == CurrentCards.getCardByIndex(i).compareTo(previous.getCardByIndex(0))) {
                dc.addCard(CurrentCards.getCardByIndex(i));
            }
        }
        return dc;
    }

    //一对出牌策略
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

    //三张出牌策略
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

    //杂顺出牌策略
    private deliveredCardsGroup ZaShun(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 4 ; i++) {
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

    //五同花出牌策略
    private deliveredCardsGroup WuTongHua(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();

        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            dc.addCard(CurrentCards.getCardByIndex(i));

            for (int j = i + 1; j < CurrentCards.cardsAmount(); j++) {
                int count = 1;
                if ( CurrentCards.getCardByIndex(i).getColor() ==
                CurrentCards.getCardByIndex(j).getColor()) {
                    dc.addCard(CurrentCards.getCardByIndex(j));
                    count++;
                    if (count == 5) {
                        return dc;
                    }
                }
            }
        }
        dc.clear();
        return dc;
    }

    //三带二出牌策略
    private deliveredCardsGroup SanDaiEr(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 4 ; i++) {
            for (int j = 0; j < 5 ; j++) {
                dc.addCard(CurrentCards.getCardByIndex(i+j));
            }
            if (cardsManager.jugdeType(dc.getCardsGroup())== CardsType.sandaier
            && dc.getBiggestValue() > previous.getBiggestValue()) {
                return dc;
            }
        }
        dc.clear();
        return dc;
    }
}

