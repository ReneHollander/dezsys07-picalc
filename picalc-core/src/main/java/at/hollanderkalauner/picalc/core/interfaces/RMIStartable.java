package at.hollanderkalauner.picalc.core.interfaces;

/**
 * A RMI Application that can be started
 *
 * @author Rene Hollander
 * @version 20141215.1
 */
public interface RMIStartable {

    /**
     * A startable Service
     */
    public interface Service {

        /**
         * Start the Application and binds it to the given port
         *
         * @param port Port to bind to
         * @throws Exception if there was an error starting the application
         */
        public void start(int port) throws Exception;

        /**
         * Starts the Application and binds it to the default port
         *
         * @throws Exception if there was an error starting the application
         */
        public void start() throws Exception;

    }


    /**
     * A startable Client
     */
    public interface Client {

        /**
         * Start the Application
         *
         * @param hostname Hostname to connect to
         * @param port     Port to connect to
         * @throws Exception if there was an error starting the application
         */
        public void start(String hostname, int port) throws Exception;

        /**
         * Starts the Application and connects to the standard hostname and port
         *
         * @throws Exception if there was an error starting the application
         */
        public void start() throws Exception;
    }

}
