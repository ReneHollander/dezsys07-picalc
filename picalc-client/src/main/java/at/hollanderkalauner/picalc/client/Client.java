package at.hollanderkalauner.picalc.client;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.interfaces.RMIStartable;
import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client
 *
 * @author Paul Kalauner
 * @version 20141212.1
 */
public class Client implements RMIStartable.Client {
    private static final Logger LOG = LogManager.getLogger(Client.class);

    private Calculator service;

    /**
     * Sends a request to calculate Pi
     *
     * @param decimalPlaces the decimalPlaces used to calculate Pi
     * @return BigDecimal pi
     * @throws RemoteException if the calculation failed
     */
    public BigDecimal calc(int decimalPlaces) throws RemoteException {
        return service.pi(decimalPlaces);
    }

    @Override
    public void start(String hostname, int port) throws Exception {
        LOG.info("Starting Client");
        RMIUtil.setupPolicy();
        Registry registry = LocateRegistry.getRegistry(hostname, port);
        service = (Calculator) registry.lookup(Static.CALCULATOR_SERVICE_NAME);
        LOG.info("Successfully started Client");
    }

    @Override
    public void start() throws Exception {
        this.start("localhost", 1099);
    }
}
