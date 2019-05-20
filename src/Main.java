import com.elkard.asteroidsgame.Controller.AsteroidsGame;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Title: Asteroids Logic");
        System.out.println("PL: Projekt PROZ (Programowanie Zdarzeniowe) na PW WEiTI 19L");
        System.out.println("EN: Project for Event-Driven Programming lectures on Warsaw University of Technology");
        System.out.println("Copyright (C) 2019 Robert Dudzinski");

        AsteroidsGame game = new AsteroidsGame();

        game.run();
    }
}