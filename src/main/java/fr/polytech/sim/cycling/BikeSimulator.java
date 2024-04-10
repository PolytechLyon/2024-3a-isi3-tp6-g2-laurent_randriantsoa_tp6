package fr.polytech.sim.cycling;

import fr.polytech.sim.Simulation;
import fr.polytech.sim.log.FileLogger;
import fr.polytech.sim.log.Logger;
import fr.polytech.sim.log.LoggerFactory;
import fr.polytech.sim.log.TimestampedLoggerDecorator;
import fr.polytech.sim.utils.Context;

/**
 * Bike simulation.
 */
public class BikeSimulator implements Simulation {
    private final TimestampedLoggerDecorator logger = new TimestampedLoggerDecorator(LoggerFactory.createLogger("BikeSimulator"));
    private final Bike bike;
    //private final Bike tagAlongBike;

    public BikeSimulator(Bike bike) {
        this.bike = bike;
        //this.tagAlongBike = tagAlongBike;
    }

    public void run() {

        System.out.println("Test simpleBike");
        this.logger.log("Bike's speed %.2f Km/h.", bike.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", bike.getMass());

        /*System.out.println("Test TagAlongBike");
        this.logger.log("Bike's speed %.2f Km/h.", tagAlongBike.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", tagAlongBike.getMass());*/
    }
}
