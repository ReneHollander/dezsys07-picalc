package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.CalculatorRegistry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 12/12/14.
 */
public class CalculatorRegistryService extends UnicastRemoteObject implements CalculatorRegistry {

    private transient List<Calculator> calculatorList;

    public CalculatorRegistryService() throws RemoteException {
        super();
        this.calculatorList = new ArrayList<Calculator>();
    }

    public List<Calculator> getCalculatorList() {
        return calculatorList;
    }

    @Override
    public void registerCalculator(Calculator calculator) throws RemoteException {
        this.calculatorList.add(calculator);
    }
}
