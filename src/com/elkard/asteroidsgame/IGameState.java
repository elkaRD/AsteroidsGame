package com.elkard.asteroidsgame;

public interface IGameState
{
    public GameLogic.GameState getNextState(AsteroidsGame.MenuButton a);
}
