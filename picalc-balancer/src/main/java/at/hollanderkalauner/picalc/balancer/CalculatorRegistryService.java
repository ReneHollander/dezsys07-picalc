package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.CalculatorRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rene on 12/12/14.
 */
public class CalculatorRegistryService extends UnicastRemoteObject implements CalculatorRegistry {

    private static final Logger LOG = LogManager.getLogger(CalculatorRegistryService.class);

    private transient List<Calculator> calculatorList;

    public CalculatorRegistryService() throws RemoteException {
        super();
        this.calculatorList = Collections.synchronizedList(new ArrayList<Calculator>());
    }

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
