package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.IGameController;
import com.elkard.asteroidsgame.View.UI.IButtonsManager;

public interface IInputHandler
{
    void onKeyPressed(int key, boolean isPressed);
    void handleInput(IGameController asteroidsGame);
    void handleInput(IButtonsManager buttonsManager);
    void refresh();
}
