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

    public void run() {
        Bike bike = Context.inject(Bike.class);

        // Vérifier si l'injection a réussi
        if (bike == null) {
            this.logger.log("Failed to inject Bike instance.");
            return;
        }

        this.logger.log("Bike's speed %.2f Km/h.", bike.getVelocity());
        this.logger.log("Bike's mass %.2f Kg.", bike.getMass());
    }
}
