package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculatorRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Registry to register Calculators to where calculations are getting distributed
 *
 * @author Rene Hollander
 * @version 20141213.1
 */
public class CalculatorRegistryService extends UnicastRemoteObject implements CalculatorRegistry {

    private static final Logger LOG = LogManager.getLogger(CalculatorRegistryService.class);

    private transient List<Calculator> calculatorList;

    /**
     * Created a new CalculatorRegistryService
     *
     * @throws java.rmi.RemoteException if failed to export object
     */
    public CalculatorRegistryService() throws RemoteException {
        super();
        this.calculatorList = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Gets the List of the currently connected calculators
     *
     * @return List of the currently connected calculators
     */
    public List<Calculator> getCalculatorList() {
        return calculatorList;
    }

    @Override
    public void registerCalculator(Calculator calculator) throws RemoteException {
        LOG.info("Adding " + calculator + " to service list");
        this.calculatorList.add(calculator);
    }

    @Override
    public void unregisterCalculator(Calculator c) throws RemoteException {
        LOG.info("Removing " + c + " from service list");
        this.calculatorList.remove(c);
    }
}
