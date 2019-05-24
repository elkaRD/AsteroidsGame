///
/// EN: Project for the Event-Driven Programming course
///     Warsaw University of Technology
///     Java Asteroids Game
///
/// PL: Projekt PROZ (Programowanie zdarzeniowe)
///     PW WEiTI 19L
///     Gra typu Asteroids napisana w Javie
///
/// Copyright (C) Robert Dudzinski 2019
///
/// File: MenuManager.java


package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.IGameState;
import com.elkard.asteroidsgame.View.UI.Button;
import com.elkard.asteroidsgame.View.UI.ButtonsGroup;

public class MenuManager implements IButtonClickListener, IGameState
{
    private static MenuManager instance = new MenuManager();

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

    private GameLogic gameLogic;

    private ButtonsGroup buttonsMain;
    private ButtonsGroup buttonsPause;
    private ButtonsGroup buttonsGameover;

    public static MenuManager getInstance()
    {
        return instance;
    }

    private MenuManager()
    {
        prepareButtons();
    }

    public void setGameLogic(GameLogic gameLogic)
    {
        this.gameLogic = gameLogic;
        gameLogic.setStateChangedListener(this);
    }

    private void prepareButtons()
    {
        prepareMainMenuButtons();
        preparePauseMenuButtons();
        prepareGameoverMenuButtons();
    }

    private void prepareMainMenuButtons()
    {
        buttonsMain = new ButtonsGroup()
                .setPosition(550,300)
                .setListener(this)
                .setTag(TAG_MENU_MAIN)
                .setVisible(false);

        buttonsMain.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_MAIN_PLAY));

        //TODO: future feature
//        buttonsMain.add(new Button()
//                .setPosition(0, 60)
//                .setSize(200,20)
//                .setText(BUTTON_MAIN_HIGHSCORES));

        buttonsMain.add(new Button()
                .setPosition(0, 60)
                .setSize(200,20)
                .setText(BUTTON_MAIN_EXIT));
    }

    private void preparePauseMenuButtons()
    {
        buttonsPause = new ButtonsGroup()
                .setPosition(400,500)
                .setListener(this)
                .setTag(TAG_MENU_PAUSE)
                .setVisible(false);

        buttonsPause.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_PAUSE_RESUME));

        buttonsPause.add(new Button()
                .setPosition(240, 0)
                .setSize(200,20)
                .setText(BUTTON_PAUSE_MENU));
    }

    private void prepareGameoverMenuButtons()
    {
        buttonsGameover = new ButtonsGroup()
                .setPosition(400,500)
                .setListener(this)
                .setTag(TAG_MENU_GAMEOVER)
                .setVisible(false);

        buttonsGameover.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_GAMEOVER_AGAIN));

        buttonsGameover.add(new Button()
                .setPosition(240, 0)
                .setSize(200,20)
                .setText(BUTTON_GAMEOVER_MENU));
    }

    @Override
    public void onButtonClicked(ButtonsGroup group, Button clicked)
    {
        if (gameLogic == null) return;

        if (group.getTag().equals(TAG_MENU_MAIN))
        {
            if (clicked.getText().equals(BUTTON_MAIN_PLAY))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.MAIN_PLAY);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_HIGHSCORES))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.MAIN_HIGHSCORES);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_EXIT))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.MAIN_EXIT);
            }

            return;
        }

        if (group.getTag().equals(TAG_MENU_PAUSE))
        {
            if (clicked.getText().equals(BUTTON_PAUSE_RESUME))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.PAUSE_RESUME);
            }
            else if  (clicked.getText().equals(BUTTON_PAUSE_MENU))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.PAUSE_MENU);
            }

            return;
        }

        if (group.getTag().equals(TAG_MENU_GAMEOVER))
        {
            if (clicked.getText().equals(BUTTON_GAMEOVER_AGAIN))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.GAMEOVER_AGAIN);
            }
            else if  (clicked.getText().equals(BUTTON_GAMEOVER_MENU))
            {
                gameLogic.menuButtonClicked(GameLogic.MenuButton.GAMEOVER_RETURN);
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
