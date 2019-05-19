package com.elkard.asteroidsgame.View.UI;

import com.elkard.asteroidsgame.View.GameRenderer;
import com.elkard.asteroidsgame.View.IButtonClickListener;
import com.elkard.asteroidsgame.Vec2i;

import java.awt.*;
import java.util.ArrayList;

public class ButtonsGroup
{
    private ArrayList<Button> buttons = new ArrayList<>();

    private IButtonClickListener listener = null;
    private boolean isVisible = true;

    public Vec2i position = new Vec2i();

    public String tag = "";

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

    public ButtonsGroup setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
        return this;
    }

    public ButtonsGroup setListener(IButtonClickListener newListener)
    {
        listener = newListener;
        return this;
    }

    public ButtonsGroup setTag(String tag)
    {
        this.tag = tag;
        return this;
    }

    public String getTag()
    {
        return tag;
    }

    public boolean getVisible()
    {
        return isVisible;
    }

    public void draw(Graphics g, GameRenderer gameRenderer)
    {
        for (Button button : buttons)
        {
            button.draw(g, gameRenderer, position);
        }
    }

    public void onClick(int posX, int posY)
    {
        if (listener == null) return;

        posX -= position.x;
        posY -= position.y;

        for (Button button : buttons)
        {
            if (posX >= button.position.x && posX <= button.position.x + button.lastRealSize.x
                && posY >= button.position.y && posY <= button.position.y + button.lastRealSize.y)
            {
                listener.onButtonClicked(this, button);
                break;
            }
        }
    }

    public ButtonsGroup setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        return this;
    }
}
