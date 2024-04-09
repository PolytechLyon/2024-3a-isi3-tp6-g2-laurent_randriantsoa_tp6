package fr.polytech.sim.cycling;

import fr.polytech.sim.Simulation;
import fr.polytech.sim.log.FileLogger;
import fr.polytech.sim.log.Logger;
import fr.polytech.sim.log.LoggerFactory;
import fr.polytech.sim.utils.Context;

/**
 * Bike simulation.
 */
public class BikeSimulator implements Simulation {
    private final Logger logger = LoggerFactory.createLogger("BikeSimulator");

    public void run() {
        Bike bike = Context.inject(SimpleBike.class);

        this.logger.log("Bike's speed %.2f Km/h.", bike.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", bike.getMass());

        System.out.println("Test TagAlongBike");
        TagAlongBike tag = Context.inject(TagAlongBike.class);
        this.logger.log("Bike's speed %.2f Km/h.", tag.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", tag.getMass());
    }
}
