package fr.polytech.sim.cycling;

import fr.polytech.sim.Simulation;
import fr.polytech.sim.log.FileLogger;
import fr.polytech.sim.log.Logger;
import fr.polytech.sim.log.LoggerFactory;
import fr.polytech.sim.log.TimestampedLoggerDecorator;
import fr.polytech.sim.utils.Context;

import java.util.Iterator;

/**
 * Bike simulation.
 */
public class BikeSimulator implements Simulation {
    private final TimestampedLoggerDecorator logger = new TimestampedLoggerDecorator(LoggerFactory.createLogger("BikeSimulator"));
    private final Bike bike;
    private final Bike tag;

    public BikeSimulator(Iterator<Bike> bikeIterator) {
        if (bikeIterator.hasNext()) {
            this.bike = bikeIterator.next();
        } else {
            throw new IllegalArgumentException("Il n'y a pas assez de vélos dans l'itérateur.");
        }

        if (bikeIterator.hasNext()) {
            this.tag = bikeIterator.next();
        } else {
            throw new IllegalArgumentException("Il n'y a pas assez de vélos dans l'itérateur.");
        }
    }

    public void run() {
        System.out.println("Test simpleBike");
        this.logger.log("Bike's speed %.2f Km/h.", bike.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", bike.getMass());

        System.out.println("Test TagAlongBike");
        this.logger.log("Bike's speed %.2f Km/h.", this.tag.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", this.tag.getMass());
    }
}
