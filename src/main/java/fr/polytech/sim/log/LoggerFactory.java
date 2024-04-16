package fr.polytech.sim.log;

public interface LoggerFactory {
    static Logger createLogger(String name) {
        return new ConsoleLogger(name);
    }
}