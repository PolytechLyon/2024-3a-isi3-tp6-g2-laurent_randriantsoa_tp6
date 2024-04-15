package fr.polytech.sim.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampedLoggerDecorator implements Logger {

    protected Logger logger;

    public TimestampedLoggerDecorator(Logger logger) {
        this.logger = logger;
    }

    private String addTimestamp(String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "[" + formattedTime + "] " + message;
    }

    @Override
    public void log(String format, Object... args) {
        String timestampedMessage = addTimestamp(this.logger.message);
        this.logger.log(timestampedMessage, args);
    }
}