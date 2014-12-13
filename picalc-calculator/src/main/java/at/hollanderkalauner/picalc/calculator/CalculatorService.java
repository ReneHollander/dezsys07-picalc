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

    public enum Mode {
        STANDALONE, BEHINDBALANCER
    }

    /**
     * Initializes the CalculatorService
     *
     * @throws RemoteException on failure
     */
    public CalculatorService() throws RemoteException {
        super();
        LOG.info("Initializing CalculatorService");
    }

    public void bind(Mode mode, CalculationBehaviour calculationBehaviour) {
        LOG.info("Binding Service " + this.toString() + "!");
        RMIUtil.setupPolicy();
        try {
            if (mode == Mode.BEHINDBALANCER) {
                CalculatorRegistry calculatorRegistry = (CalculatorRegistry) Naming.lookup(Static.BALANCER_CALCULATORREGISTRY_NAME);
                calculatorRegistry.registerCalculator(this);
                LOG.info("Service " + this.toString() + " successfully bound and listening for Requests through the balancer!");
            } else if (mode == Mode.STANDALONE) {
                RMIUtil.setupRegistry();
                if (calculationBehaviour == null) {
                    LOG.error("You need to specify a calculation behaviour to use!");
                } else {
                    Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, calculationBehaviour);
                    Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);

                    LOG.info("Service " + this.toString() + " successfully bound and listening for Requests!");
                }
            }
        } catch (RemoteException e) {
            LOG.error("RemoteException: " + e.getMessage());
        } catch (MalformedURLException e) {
            LOG.error("Check the given URL: " + e.getMessage());
        } catch (AlreadyBoundException e) {
            LOG.error("CalculatorService has already been bound: " + e.getMessage());
        } catch (NotBoundException e) {
            LOG.error("Balancer has not been bound: " + e.getMessage());
        }
    }

    public void bind(Mode mode) throws RemoteException, NotBoundException, MalformedURLException, AlreadyBoundException {
        this.bind(mode, null);
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        try {
            LOG.info("Calculating Pi to " + decimalPlaces + " decimal places");
            CalculationBehaviour calculationBehaviour = (CalculationBehaviour) Naming.lookup(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
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
