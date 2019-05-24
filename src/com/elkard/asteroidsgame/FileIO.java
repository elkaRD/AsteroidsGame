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
/// File: FileIO.java


package com.elkard.asteroidsgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public final class FileIO
{
    public static void save(String fileName, String data)
    {
        try
        {
            FileWriter file = new FileWriter(fileName);
            file.write(data);
            file.flush();
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String read(String fileName)
    {
        String result = null;
        try
        {
            File f = new File(fileName);

            if (!f.exists()) return "";

            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean doesExist(String fileName)
    {
        File f = new File(fileName);
        if (f.isFile())
            return true;

        return false;
    }
}