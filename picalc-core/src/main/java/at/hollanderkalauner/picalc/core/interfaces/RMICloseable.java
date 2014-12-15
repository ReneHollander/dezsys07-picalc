package at.hollanderkalauner.picalc.core.interfaces;

/**
 * A closeable RMI Application
 *
 * @author Rene Hollander
 * @version 20141215
 */
public interface RMICloseable {

    /**
     * Closes the Application
     *
     * @throws Exception if there was an error closing the application
     */
    public void close() throws Exception;

}
