package com.elkard.asteroidsgame;

import java.awt.*;
import java.util.ArrayList;

public class ButtonsGroup
{
    private ArrayList<Button> buttons = new ArrayList<>();

    private IButtonClickListener listener = null;
    private boolean isVisible = true;

    public ButtonsGroup()
    {
        ButtonsManager.getInstance().registerGroup(this);
    }

    public void add(Button newButton)
    {
        if (newButton.group != null)
        {
            newButton.group.remove(newButton);
        }
        newButton.group = this;
        buttons.add(newButton);

    }

    public void remove(Button toRemove)
    {
        toRemove.group = null;
        buttons.remove(toRemove);
    }

    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public void setListener(IButtonClickListener newListener)
    {
        listener = newListener;
    }

    public boolean getVisible()
    {
        return isVisible;
    }

    public void draw(Graphics g)
    {
        for (Button button : buttons)
        {
            button.draw(g);
        }
    }

    public void onClick(int posX, int posY)
    {
        if (listener == null) return;

        for (Button button : buttons)
        {
            if (posX >= button.position.x && posX <= button.position.x + button.size.x
                && posY >= button.position.y && posY <= button.position.y + button.size.y)
            {
                listener.onButtonClicked(button);
                break;
            }
        }
    }
}
