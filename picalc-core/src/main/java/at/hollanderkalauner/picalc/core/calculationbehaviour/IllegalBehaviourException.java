package at.hollanderkalauner.picalc.core.calculationbehaviour;

/**
 * Thrown to indicate that an invalid behaviour has been passed.
 *
 * @author Paul Kalauner
 * @version 20141212.1
 */
public class IllegalBehaviourException extends IllegalArgumentException {
    public IllegalBehaviourException() {
        super();
    }

    public IllegalBehaviourException(String s) {
        super(s);
    }

    public IllegalBehaviourException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBehaviourException(Throwable cause) {
        super(cause);
    }
}
