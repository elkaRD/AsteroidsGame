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

    public Button setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
        return this;
    }

    public void draw(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

        int realSizeX = g.getFontMetrics().stringWidth(text) + 20;
        int realSizeY = fontSize + 20;
        if (size.x > realSizeX) realSizeX = (int) size.x;
        if (size.y > realSizeY) realSizeY = (int) size.y;

        g.drawRect((int) position.x, (int) position.y, realSizeX, realSizeY);

        g.drawString(text, (int) position.x + 10, (int) position.y + 10);
    }
}
