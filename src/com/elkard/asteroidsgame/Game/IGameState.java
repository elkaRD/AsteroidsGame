package com.elkard.asteroidsgame.Game;

public interface IGameState
{
    void onStateChanged(GameLogic.GameState prevState, GameLogic.GameState curState);
}
