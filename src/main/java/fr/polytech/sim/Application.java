package fr.polytech.sim;

import fr.polytech.sim.cycling.Bike;
import fr.polytech.sim.cycling.BikeSimulator;
import fr.polytech.sim.cycling.TagAlongBike;
import fr.polytech.sim.utils.Context;

import java.util.Iterator;

/**
 * Application's main classe.
 */
public class Application {

    /**
     * Entry point.
     * @param args  app's arguments.
     */
    public static void main(String... args) {
        Iterator<Bike> bikeIterator = Context.injectAll(Bike.class);
        //Bike bike = Context.inject(Bike.class);
        new BikeSimulator(bikeIterator).run();
    }
}
