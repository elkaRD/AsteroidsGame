package com.elkard.asteroidsgame;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GameRenderer extends JFrame
{

    private static final int screenWidth = 1280;
    private static final int screenHeight = 720;

    private final AsteroidsGame gameEngine;
    //private KeyboardListener keyboardListener;

    public final InputHandler inputHandler;

    private final KeyCheck keyCheck;

    JButton playButton;
    JButton exitButton;

    public Boolean isKeyPressed = false;

    private Image strona;
    private Graphics g_str;

    public GameRenderer(AsteroidsGame ag)
    {
        gameEngine = ag;
        inputHandler = new InputHandler();
        keyCheck = new KeyCheck(this);
        buildWindow(screenWidth, screenHeight);

        //keyboardListener = new KeyboardListener(gameEngine);

//        keyboardListener =

//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new KeyboardListener(gameEngine);
//            }
//        });

        int w = getWidth();
        int h = getHeight();



    }

//    int counter = 0;

    public void onUpdate(float deltaTime)
    {
//        counter++;
//        if(counter == 20) {
//            revalidate();
//            repaint();
//            counter = 0;
//        }

        revalidate();
        repaint();
    }

    public void paint(Graphics g) {
        //super.paint(g);  // fixes the immediate problem.

        if (strona == null)
        {
            strona = createImage(getWidth(), getHeight());
            g_str=strona.getGraphics();

            System.out.println("object strona was null");
        }

        Vec2 pos = gameEngine.getGameLogic().getPlayer().getPosition();
        float rot = gameEngine.getGameLogic().getPlayer().getRotation();

        g_str.clearRect(0, 0, getWidth(), getHeight());
        Graphics g2 = g_str;

//        Vec2[] p = gameEngine.getGameLogic().getPlayer().getRenderPoints();
//
//        for (int i = 0; i < p.length; i++)
//        {
//            int j = (i+1) % p.length;
//            g2.drawLine((int) p[i].x, (int) p[i].y, (int) p[j].x, (int) p[j].y);
//        }
//
//        Vec2[] b = gameEngine.getGameLogic().getBulletsRenderPoints();
//
//        for (int i = 0; i < b.length; i += 2)
//        {
//            g2.drawLine((int) b[i].x, (int) b[i].y, (int) b[i+1].x, (int) b[i+1].y);
//        }

        Line[] p = gameEngine.getGameLogic().getPlayer().getRenderLines();

        for (Line line : p)
        {
            g2.drawLine((int) line.b.x, (int) line.b.y, (int) line.e.x, (int) line.e.y);
        }

        Line[] b = gameEngine.getGameLogic().getBulletsRenderLines();

        for (Line line : b)
        {
            g2.drawLine((int) line.b.x, (int) line.b.y, (int) line.e.x, (int) line.e.y);
        }

        g.drawImage(strona, 0, 0, this);
    }

    public ArrayList<String> list = new ArrayList<String>();

    private JLabel debugBox;

    public void printMsg(String msg)
    {
        debugBox.setText("msg: " + msg);
    }

    private void buildWindow(int w, int h)
    {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(w, h);

        debugBox = new JLabel("DebugBox: ");
        getContentPane().add(debugBox);


        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClicked();
            }
        } );
        panel.add(playButton);

//        addKeyListener(keyboardListener);
        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);

        addKeyListener(new KeyListener() {



            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_F11 && event.isAltDown()) {
//                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    System.out.println("pressed");
                }

                getOuter().inputHandler.onKeyPressed(event.getKeyChar(), true);
                //inputHandler.handleInput(gameEngine);
                //GameRenderer.this.keyPressed(event.getKeyChar());
                getOuter().list.add("new");

                getOuter().isKeyPressed = true;
            }

            @Override
            public void keyReleased(KeyEvent event) {
                System.out.println("released");
                inputHandler.onKeyPressed(event.getKeyChar(), false);

                //getOuter().isKeyPressed = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("typed");
            }
        });



        //addKeyListener(new KeyCheck(this));
        //addKeyListener(keyCheck);



    }

    public GameRenderer getOuter()
    {
        return this;
    }

    private void keyPressed(char key)
    {
        inputHandler.onKeyPressed(key, true);
        Debug.Log("pressed1: " + inputHandler.acceleratePressed);
        inputHandler.handleInput(gameEngine);
        Debug.Log("pressed2: " + inputHandler.acceleratePressed);
    }

    public void handleInput()
    {
        inputHandler.handleInput(gameEngine);
//        if (inputHandler.acceleratePressed) Debug.Log("wtf1");

//        Debug.Log("someone called me");

        //keyCheck.handleInput();

        //Debug.Log("Size of the list: " + list.size());

        if (isKeyPressed) Debug.Log("key is pressed");
    }

    public void showWindow()
    {
        setVisible(true);
    }

    public void hideWindow()
    {
        setVisible(false);
    }

    private void renderPlayer()
    {

    }

    private void renderAsteroids()
    {

    }

    private void renderBullets()
    {

    }

    private void renderUI()
    {

    }

    private void showMainMenu()
    {

    }

    private void hideMainMenu()
    {

    }

    private void playClicked()
    {
        gameEngine.menuButtonClicked(AsteroidsGame.MenuButton.PLAY);
    }

    private void exitClicked()
    {

    }

    class KeyCheck implements KeyListener
    {
        private GameRenderer gameRenderer;

        private InputHandler inha;

        public KeyCheck(GameRenderer gr)
        {
            gameRenderer = gr;
            inha = new InputHandler();
        }


        @Override
        public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_F11 && event.isAltDown()) {
//                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.out.println("pressed");
        }

//        gameRenderer.inputHandler.onKeyPressed(event.getKeyChar(), true);
        //gameRenderer.inputHandler.handleInput(gameEngine);
        //GameRenderer.this.keyPressed(event.getKeyChar());

            inha.onKeyPressed(event.getKeyChar(), true);
    }

        @Override
        public void keyReleased(KeyEvent event) {
        System.out.println("released");
        gameRenderer.inputHandler.onKeyPressed(event.getKeyChar(), false);
    }

        @Override
        public void keyTyped(KeyEvent e) {
        System.out.println("typed");
    }

        public void handleInput()
        {
//            gameRenderer.inputHandler.handleInput(gameEngine);
//            if (gameRenderer.inputHandler.acceleratePressed)
//                Debug.Log("seems to be ok");

            inha.handleInput(gameEngine);
        }
    }
}