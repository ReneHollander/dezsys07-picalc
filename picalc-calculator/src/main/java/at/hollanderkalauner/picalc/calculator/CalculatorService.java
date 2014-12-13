package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Calculator Service
 *
 * @author Rene Hollander
 * @version 20141212.1
 */
public class CalculatorService extends UnicastRemoteObject implements Calculator {
    private static final Logger LOG = LogManager.getLogger(CalculatorService.class);

    private String balancerurl;

    /**
     * Initializes the CalculatorService
     *
     * @throws RemoteException on failure
     */
    public CalculatorService() throws RemoteException {
        super();
        LOG.info("Initializing CalculatorService");
    }

    /**
     * Binds a new calculator and informs the balancer
     *
     * @param host Hostname of the Balancer
     * @param port Port of the Balancer
     */
    public void bindToBalancer(String host, int port) {
        LOG.info("Binding Service " + this.toString() + " in balancer mode!");
        RMIUtil.setupPolicy();
        try {

            this.balancerurl = RMIUtil.createRMIUrl(host, port);

            CalculatorRegistry calculatorRegistry = (CalculatorRegistry) Naming.lookup(this.getBalancerurl() + Static.BALANCER_CALCULATORREGISTRY_NAME);
            calculatorRegistry.registerCalculator(this);
            LOG.info("Service " + this.toString() + " successfully bound and listening for Requests through the balancer!");
        } catch (RemoteException e) {
            LOG.error("RemoteException: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            LOG.error("Check the given URL: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            LOG.error("Balancer has not been bound: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Binds the Calculator in Standalone Mode
     *
     * @param calculationBehaviour Calculation Behaviour that is  being used
     */
    public void bindStandalone(CalculationBehaviour calculationBehaviour) {
        LOG.info("Binding Service " + this.toString() + " in standalone mode!");
        RMIUtil.setupPolicy();
        try {
            RMIUtil.setupRegistry();
            if (calculationBehaviour == null) {
                LOG.error("You need to specify a calculation behaviour to use!");
            } else {
                Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, calculationBehaviour);
                Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);

                LOG.info("Service " + this.toString() + " successfully bound and listening for Requests!");
            }
        } catch (RemoteException e) {
            LOG.error("RemoteException: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            LOG.error("Check the given URL: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            LOG.error("CalculatorService has already been bound: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Gets the URL of the Balancer
     *
     * @return URL of the Balancer
     */
    public String getBalancerurl() {
        return balancerurl;
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        try {
            LOG.info("Calculating Pi to " + decimalPlaces + " decimal places");
            CalculationBehaviour calculationBehaviour = (CalculationBehaviour) Naming.lookup(this.getBalancerurl() + Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
            return calculationBehaviour.calculatePi(decimalPlaces);
        } catch (NotBoundException e) {
            LOG.error(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME + " is not bound!");
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "CalculatorService{}";
    }
}
