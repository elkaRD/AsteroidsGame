package com.elkard.asteroidsgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button
{
    public ButtonsGroup group = null;

    public Vec2i position = new Vec2i();
    public Vec2i size = new Vec2i();
    public Vec2i lastRealSize = new Vec2i();

    public String text = "";
    public int fontSize = 20;
    public int boundary = 10;

    public boolean centerText = false;

    public String tag = "";

    public Button()
    {

    }

    public Button setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        return this;
    }

    public Button setSize(int x, int y)
    {
        size.x = x;
        size.y = y;
        return this;
    }

    public Button setText(String text)
    {
        this.text = text;
        return this;
    }

    public String getText()
    {
        return text;
    }

    public Button setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
        return this;
    }

    public Button setBoundary(int boundary)
    {
        this.boundary = boundary;
        return this;
    }

    public Button setCenter(boolean isCenter)
    {
        centerText = isCenter;
        return this;
    }

    public Button setTag(String tag)
    {
        this.tag = tag;
        return this;
    }

    public String getTag()
    {
        return tag;
    }

    public void draw(Graphics g, GameRenderer gameRenderer, Vec2i parentPos)
    {
        float factor = Math.min(gameRenderer.getScreenWidthFactor(), gameRenderer.getScreenHeightFactor());
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (fontSize * factor)));

        int realSizeX = g.getFontMetrics().stringWidth(text) + 2*boundary;
        int realSizeY = fontSize + 2*boundary;
        int posX = (int) (position.x + boundary + parentPos.x);
        int posY = (int) (position.y + size.y + boundary + parentPos.y);
        if (centerText)
        {
            //TODO:
            //posX +=
        }
        if (size.x > realSizeX) realSizeX = (int) size.x;
        if (size.y > realSizeY) realSizeY = (int) size.y;

        lastRealSize.x = realSizeX;
        lastRealSize.y = realSizeY;

        Vec2i newPosition = new Vec2i();
        newPosition.x = position.x + parentPos.x;
        newPosition.y = position.y + parentPos.y;

        newPosition.x *= gameRenderer.getScreenWidthFactor();
        newPosition.y *= gameRenderer.getScreenHeightFactor();
        realSizeX *= gameRenderer.getScreenWidthFactor();
        realSizeY *= gameRenderer.getScreenHeightFactor();
        posX *= gameRenderer.getScreenWidthFactor();
        posY *= gameRenderer.getScreenHeightFactor();

        g.drawRect(newPosition.x, newPosition.y, realSizeX, realSizeY);
        g.drawString(text, posX, posY);
    }
}
