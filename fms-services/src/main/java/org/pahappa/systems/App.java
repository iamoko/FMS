package org.pahappa.systems;

import org.pahappa.systems.core.API.Withdrawing;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Withdrawing withdrawing = new Withdrawing(100, "256705531898", "AMoko Ivan");
        withdrawing.start();
        System.out.println("Complete");
    }
}
