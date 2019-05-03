package com.elkard.asteroidsgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button
{
    public ButtonsGroup group = null;

    public Vec2 position = new Vec2();
    public Vec2 size = new Vec2();

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

    public void draw(Graphics g, Vec2 parentPos)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

        int realSizeX = g.getFontMetrics().stringWidth(text) + 2*boundary;
        int realSizeY = fontSize + 2*boundary;
        int posX = (int) (position.x + boundary + parentPos.x);
        if (centerText)
        {
            //TODO:
            //posX +=
        }
        if (size.x > realSizeX) realSizeX = (int) size.x;
        if (size.y > realSizeY) realSizeY = (int) size.y;

        g.drawRect((int) (position.x + parentPos.x), (int) (position.y + parentPos.y), realSizeX, realSizeY);



        g.drawString(text, posX, (int) (position.y + size.y + boundary + parentPos.y));
    }
}
