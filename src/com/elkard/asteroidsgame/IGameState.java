package com.elkard.asteroidsgame;

public interface IGameState
{
    //public GameLogic.GameState getNextState(AsteroidsGame.MenuButton a);
    void onStateChanged(GameLogic.GameState prevState, GameLogic.GameState curState);
}
