package fr.polytech.sim.log;

/**
 * Name logger that is supposed to log its name along with each log entry, to
 * facilitate tracing.
 */
public abstract class NamedLogger implements Logger {
    final protected String name;

    /**
     * Constructor.
     *
     * @param name logger name.
     */
    protected NamedLogger(String name) {
        this.name = name;
    }

    @Override
    public void log(String format, Object... args) {
        String entry = String.format(format, args);
        String message = formatMessage(entry);
        writeMessage(message);
    }

    /**
     * Format the message to be logged.
     */
    protected String formatMessage(String entry) {
        return String.format("%s\t%s\n", this.name, entry);
    }

    /**
     * Write the message to the log according to subclass implementation.
     */
    protected abstract void writeMessage(String message);

}
