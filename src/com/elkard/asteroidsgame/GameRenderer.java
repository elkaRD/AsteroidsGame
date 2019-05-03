package com.elkard.asteroidsgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameRenderer extends JFrame implements IButtonClickListener, IGameState
{

    private static final int screenWidth = 1280;
    private static final int screenHeight = 720;

    private static final String TAG_MENU_MAIN = "mainMenu";
    private static final String TAG_MENU_PAUSE = "pauseMenu";
    private static final String TAG_MENU_GAMEOVER = "gameoverMenu";

    private static final String BUTTON_MAIN_PLAY = "PLAY";
    private static final String BUTTON_MAIN_HIGHSCORES = "HI-SCORES";
    private static final String BUTTON_MAIN_EXIT = "EXIT";
    
    private static final String BUTTON_PAUSE_RESUME = "RESUME";
    private static final String BUTTON_PAUSE_MENU = "EXIT";

    private static final String BUTTON_GAMEOVER_AGAIN = "AGAIN";
    private static final String BUTTON_GAMEOVER_MENU = "RETURN";

    private final AsteroidsGame gameEngine;
    //private KeyboardListener keyboardListener;

    public final InputHandler inputHandler;

    private final KeyCheck keyCheck;

//    JButton playButton;
//    JButton exitButton;

    public Boolean isKeyPressed = false;

    private Image strona;
    private Graphics g_str;

    private Image image;

    private ButtonsGroup buttonsMain;
    private ButtonsGroup buttonsPause;
    private ButtonsGroup buttonsGameover;

//    private JButton buttonMainPlay = new JButton("PLAY");
//    private JButton buttonMainExit = new JButton("EXIT");
//    private JButton buttonMainBoard = new JButton("HIGHSCORES");
//    private JButton buttonGameoverAgain = new JButton("AGAIN");
//    private JButton buttonGameoverReturn = new JButton("RETURN");

    public GameRenderer(AsteroidsGame ag)
    {
        gameEngine = ag;
        inputHandler = new InputHandler();
        keyCheck = new KeyCheck(this);
        buildWindow(screenWidth, screenHeight);

        //addMouseListener(this);
        addMouseListener(ButtonsManager.getInstance());

        gameEngine.getGameLogic().setStateChangedListener(this);

//        buttonMainPlay.addActionListener(this);
//        buttonMainPlay.setBounds(40, 40, 400, 200);
//        buttonMainPlay.setVisible(true);
        //add(buttonMainPlay);

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

        loadImages();
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

        g_str.clearRect(0, 0, getWidth(), getHeight());
        Graphics g2 = g_str;

        GameLogic.GameState curState = gameEngine.getGameLogic().getState();

        if (curState == GameLogic.GameState.GAMEPLAY || curState == GameLogic.GameState.PAUSED || curState == GameLogic.GameState.GAMEOVER)
        {
            drawGameplay(g2);
            drawUI(g2);
        }

        if (curState == GameLogic.GameState.PAUSED)
        {
            drawPauseMenu(g2);
        }

        if (curState == GameLogic.GameState.GAMEOVER)
        {
            drawGameoverMenu(g2);
        }

        if (curState == GameLogic.GameState.MAINMENU)
        {
            drawMainMenu(g2);
        }

        ButtonsManager.getInstance().draw(g2);
        g.drawImage(strona, 0, 0, this);

    }

    private void drawPauseMenu(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("PAUSED", 450, 400);
    }

    private void drawGameoverMenu(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("GAME OVER", 400, 400);
    }

    private void drawMainMenu(Graphics g)
    {

    }

    private void drawGameplay(Graphics g)
    {
        drawLines(g, gameEngine.getGameLogic().getPlayerRenderLines());
        drawLines(g, gameEngine.getGameLogic().getBulletsRenderLines());
        drawLines(g, gameEngine.getGameLogic().getAsteroidsRenderLines());
    }

    private void drawLines(Graphics g, Line[] lines)
    {
        for (Line line : lines)
            g.drawLine((int) line.b.x, (int) line.b.y, (int) line.e.x, (int) line.e.y);
    }

    private void drawUI(Graphics g)
    {
        int lives = gameEngine.getGameLogic().getRemainingLives();

        for (int i = 0; i < lives; i++)
            g.drawImage(image, 50 + 60*i, 50, this);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawString("SCORE: " + gameEngine.getGameLogic().getCurScore(), screenWidth/2, 80);
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


//        playButton = new JButton("Play");
//        playButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                playClicked();
//            }
//        } );
//        panel.add(playButton);

//        addKeyListener(keyboardListener);
        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);

        addKeyListener(new KeyListener() {



            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_F11 && event.isAltDown()) {
//                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    //System.out.println("pressed");
                }

                getOuter().inputHandler.onKeyPressed(event.getKeyChar(), true);
                //inputHandler.handleInput(gameEngine);
                //GameRenderer.this.keyPressed(event.getKeyChar());
                getOuter().list.add("new");

                getOuter().isKeyPressed = true;
            }

            @Override
            public void keyReleased(KeyEvent event) {
                //System.out.println("released");
                inputHandler.onKeyPressed(event.getKeyChar(), false);

                //getOuter().isKeyPressed = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //awwSystem.out.println("typed");
            }
        });

        prepareButtons();

        //addKeyListener(new KeyCheck(this));
        //addKeyListener(keyCheck);
    }

    public GameRenderer getOuter()
    {
        return this;
    }

    private void prepareButtons()
    {
        buttonsMain = new ButtonsGroup()
                .setPosition(300,300)
                .setListener(this)
                .setTag(TAG_MENU_MAIN)
                .setVisible(false);

        buttonsMain.add(new Button()
                .setPosition(0, 0)
                .setSize(100,20)
                .setText(BUTTON_MAIN_PLAY));

        buttonsMain.add(new Button()
                .setPosition(0, 60)
                .setSize(100,20)
                .setText(BUTTON_MAIN_HIGHSCORES));

        buttonsMain.add(new Button()
                .setPosition(0, 120)
                .setSize(100,20)
                .setText(BUTTON_MAIN_EXIT));


        buttonsPause = new ButtonsGroup()
                .setPosition(300,300)
                .setListener(this)
                .setTag(TAG_MENU_PAUSE)
                .setVisible(false);

        buttonsPause.add(new Button()
                .setPosition(0, 0)
                .setSize(100,20)
                .setText(BUTTON_PAUSE_RESUME));

        buttonsPause.add(new Button()
                .setPosition(0, 200)
                .setSize(100,20)
                .setText(BUTTON_PAUSE_MENU));


        buttonsGameover = new ButtonsGroup()
                .setPosition(300,300)
                .setListener(this)
                .setTag(TAG_MENU_GAMEOVER)
                .setVisible(false);

        buttonsGameover.add(new Button()
                .setPosition(0, 0)
                .setSize(100,20)
                .setText(BUTTON_GAMEOVER_AGAIN));

        buttonsGameover.add(new Button()
                .setPosition(0, 200)
                .setSize(100,20)
                .setText(BUTTON_GAMEOVER_MENU));
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

//    private void playClicked()
//    {
//        gameEngine.menuButtonClicked(AsteroidsGame.MenuButton.MAIN_PLAY);
//    }
//
//    private void exitClicked()
//    {
//
//    }

    private void loadImages()
    {
        try {
            BufferedImage temp = ImageIO.read(new File("heart2.png"));
            image = temp.getScaledInstance(48, 48, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            System.out.println("Can't load the image");
            ex.printStackTrace();
        }
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

    @Override
    public void onButtonClicked(ButtonsGroup group, Button clicked)
    {
        if (group.getTag().equals(TAG_MENU_MAIN))
        {
            if (clicked.getText().equals(BUTTON_MAIN_PLAY))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_PLAY);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_HIGHSCORES))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_HIGHSCORES);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_EXIT))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_EXIT);
            }
            
            return;
        }
        
        if (group.getTag().equals(TAG_MENU_PAUSE))
        {
            if (clicked.getText().equals(BUTTON_PAUSE_RESUME))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.PAUSE_RESUME);
            }
            else if  (clicked.getText().equals(BUTTON_PAUSE_MENU))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.PAUSE_MENU);
            }
            
            return;
        }

        if (group.getTag().equals(TAG_MENU_GAMEOVER))
        {
            if (clicked.getText().equals(BUTTON_GAMEOVER_AGAIN))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.GAMEOVER_AGAIN);
            }
            else if  (clicked.getText().equals(BUTTON_GAMEOVER_MENU))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.GAMEOVER_RETURN);
            }

            return;
        }
    }

    @Override
    public void onStateChanged(GameLogic.GameState prevState, GameLogic.GameState curState)
    {
        System.out.println("State changed from " + prevState + " to " + curState);

        switch (prevState)
        {
            case MAINMENU:
                buttonsMain.setVisible(false);
                break;

            case PAUSED:
                buttonsPause.setVisible(false);
                break;

            case GAMEOVER:
                buttonsGameover.setVisible(false);
                break;
        }

        switch (curState)
        {
            case MAINMENU:
                buttonsMain.setVisible(true);
                break;

            case PAUSED:
                buttonsPause.setVisible(true);
                break;

            case GAMEOVER:
                buttonsGameover.setVisible(true);
                break;
        }
    }
}