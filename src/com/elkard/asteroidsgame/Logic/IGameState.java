package com.elkard.asteroidsgame.Logic;

public interface IGameState
{
    void onStateChanged(GameLogic.GameState prevState, GameLogic.GameState curState);
}
