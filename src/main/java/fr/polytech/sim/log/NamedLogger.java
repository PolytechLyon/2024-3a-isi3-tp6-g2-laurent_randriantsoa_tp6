package fr.polytech.sim.log;

/**
 * Name logger that is supposed to log its name along with each log entry, to
 * facilitate tracing.
 */
public abstract class NamedLogger implements Logger {
    final protected String name;

    protected String message;

    /**
     * Constructor.
     *
     * @param name  logger name.
     */
    protected NamedLogger(String name) {
        this.name = name;
    }

    @Override
    public void log(String format, Object... args) {
        String entry = String.format(format, args);
        this.message = String.format("%s\t%s\n", this.name, entry);
    }

}
