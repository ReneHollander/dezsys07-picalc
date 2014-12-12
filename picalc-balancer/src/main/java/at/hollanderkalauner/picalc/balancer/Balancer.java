package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by rene on 12/6/14.
 */
public class Balancer extends UnicastRemoteObject implements Calculator {

    private CalculatorRegistryService calculatorRegistryService;
    private int lastCalculator;

    public Balancer() throws RemoteException {
        super();

        this.calculatorRegistryService = new CalculatorRegistryService();
        this.lastCalculator = 0;
    }

    public void bind() throws RemoteException, AlreadyBoundException, MalformedURLException {
        RMIUtil.setupPolicy();
        RMIUtil.setupRegistry();
        Naming.bind(Static.BALANCER_CALCULATORREGISTRY_NAME, this.calculatorRegistryService);
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);

        Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, new GaussLegendre());
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        if (this.calculatorRegistryService.getCalculatorList().size() == 0) {
            throw new RemoteException("No calculators servers available!");
        }
        if (this.calculatorRegistryService.getCalculatorList().size() == this.lastCalculator) {
            this.lastCalculator = 0;
        }
        return this.calculatorRegistryService.getCalculatorList().get(this.lastCalculator++).pi(decimalPlaces);
    }
}
