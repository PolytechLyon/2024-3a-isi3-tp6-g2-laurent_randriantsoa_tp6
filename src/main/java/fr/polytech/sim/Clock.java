package fr.polytech.sim;

import java.util.Random;

/**
 * A clock used to synchronize simulations.
 */
public class Clock {
    private final int time = new Random().nextInt(25);
    private static Clock instance;

    private Clock () {
        Clock.instance = this;
    }

    public static Clock getClock() {
        if (Clock.instance == null) {
            new Clock();
        }
        return Clock.instance;
    }

    /**
     * Random integer between 0 and 24 inclusive.
     */
    public int getTime() {
        return this.time;
    }
}
