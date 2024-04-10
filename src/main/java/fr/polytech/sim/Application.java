package fr.polytech.sim;

import fr.polytech.sim.cycling.Bike;
import fr.polytech.sim.cycling.BikeSimulator;
import fr.polytech.sim.cycling.TagAlongBike;
import fr.polytech.sim.utils.Context;

/**
 * Application's main classe.
 */
public class Application {

    /**
     * Entry point.
     * @param args  app's arguments.
     */
    public static void main(String... args) {

        Bike bike = Context.inject(Bike.class);
        //Bike tagAlongBike = Context.inject(Bike.class);

        new BikeSimulator(bike).run();
    }
}
