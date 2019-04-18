package com.elkard.asteroidsgame;

public class Debug
{
    private static String data = new String();

    public static void Log(String str)
    {
//        System.out.println(str);
        data += str + "    ";
        //System.out.println("data size: " + data.length());
    }

    public static void FlushLog()
    {
        System.out.println("trying to print all data");
        if (data.length() > 0) {
            System.out.println("trying to print all data");
            System.out.println(data);
            System.out.println("Printed all data ");
            data = "";
        }
    }
}
