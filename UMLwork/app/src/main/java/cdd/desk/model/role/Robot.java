package cdd.desk.model.role;

import java.util.List;

import cdd.desk.model.card.Card;
import cdd.desk.model.card.CardsGroup;
import cdd.desk.model.card.deliveredCardsGroup;
import cdd.desk.model.game.Game;



public class Robot extends Role {
    public Robot(Game g) {
        super(g);
    }

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
                default:
            }
            //dc.addCard(c);
        }
        return dc;
    }

    private deliveredCardsGroup DanPai(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount(); i++) {
            if (1 == CurrentCards.getCardByIndex(i).compareTo(previous.getCardByIndex(0))) {
                dc.addCard(CurrentCards.getCardByIndex(i));
            }
        }
        return dc;
    }

    private deliveredCardsGroup YiDui(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 1; i++) {
            if (CurrentCards.getCardByIndex(i).getPoints() ==
                    CurrentCards.getCardByIndex(i+1).getPoints()) {
                deliveredCardsGroup temp = new deliveredCardsGroup();
                temp.addCard(CurrentCards.getCardByIndex(i));
                temp.addCard(CurrentCards.getCardByIndex(i+1));
                if (temp.getWeight()>previous.getWeight) {
                    dc = temp;
                    return dc;
                }
            }
        }
        return dc;
    }

    private deliveredCardsGroup SanZhang(deliveredCardsGroup previous) {
        deliveredCardsGroup dc = new deliveredCardsGroup();
        for (int i = 0; i < CurrentCards.cardsAmount() - 2; i++) {
            if ((CurrentCards.getCardByIndex(i).getPoints() ==
                    CurrentCards.getCardByIndex(i+1).getPoints()) &&
                    (CurrentCards.getCardByIndex(i+1).getPoints() ==
                            CurrentCards.getCardByIndex(i+2).getPoints())
                    ) {
                deliveredCardsGroup temp = new deliveredCardsGroup();
                temp.addCard(CurrentCards.getCardByIndex(i));
                temp.addCard(CurrentCards.getCardByIndex(i+1));
                if (temp.getWeight()>previous.getWeight()) {
                    dc = temp;
                    return dc;
                }
            }
        }
        return dc;
    }
}

