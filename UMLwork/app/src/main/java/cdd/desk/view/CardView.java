package cdd.desk.view;

import android.content.Context;

enum State  {UP, DOWN}

public class CardView extends android.support.v7.widget.AppCompatImageView{

    private State pos;

    public CardView(Context context)
    {
        super(context);
        pos = State.DOWN;
    }

    public State getPos()
    {
        return pos;
    }

    public void setPos(State s)
    {
        pos = s;
    }
}
