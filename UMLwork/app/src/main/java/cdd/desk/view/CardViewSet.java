package cdd.desk.view;

import java.util.LinkedList;
import java.util.List;

public class CardViewSet  {

    private List<CardView> mCard;

    public CardViewSet()
    {
        mCard = new LinkedList<>();
    }

    public void addCardView(CardView child) {
        mCard.add(child);
    }

    public void clearAllCardView()
    {
        mCard.clear();
    }

    /*
    *功能：返回被玩家选中的牌的索引
     */
    public List<Integer> getSelected()
    {
        List<Integer> selected  = new LinkedList<>();
        for(CardView x:mCard)
        {
            if(x.getPos() == State.UP)
            selected.add(mCard.indexOf(x));
        }
        return selected;
    }

}
