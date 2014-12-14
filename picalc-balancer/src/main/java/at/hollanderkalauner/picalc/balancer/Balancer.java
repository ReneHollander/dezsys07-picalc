package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * A Balancer for the Pi Calculator.
 * Adds a Calculator Service and a CaluclatorRegistry Service to register new Calculators.
 * The Client doesn't need to know if the Calculator is a Balancer or not.
 *
 * @author Rene Hollander
 * @version 20141213.1
 */
public class Balancer extends UnicastRemoteObject implements Calculator {

    private static final Logger LOG = LogManager.getLogger(Balancer.class);

    private CalculatorRegistryService calculatorRegistryService;
    private int lastCalculator;

    /**
     * Constructs a new Balancer
     *
     * @throws java.rmi.RemoteException if failed to export object
     */
    public Balancer() throws RemoteException {
        super();

        this.calculatorRegistryService = new CalculatorRegistryService();
        this.lastCalculator = 0;
    }

    /**
     * Binds CalculatorService and a CalculatorRegistry to the Registry. If a new Calculator
     * connects, it gets added to the List and if a Client Requests a calculation we choose
     * a Calculator based on Round Robin distribution.
     *
     * @param initialCalculationBehaviour Initial Behaviour that the Calculators should use to calculate pi
     */
    public void bind(CalculationBehaviour initialCalculationBehaviour) {
        LOG.info("Binding Balancer");
        RMIUtil.setupPolicy();
        try {
            RMIUtil.setupRegistry();
            this.setCalculationBehaviour(initialCalculationBehaviour);
            Naming.bind(Static.BALANCER_CALCULATORREGISTRY_NAME, this.calculatorRegistryService);
            Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);
            LOG.info("Successfully bound Balancer");
        } catch (MalformedURLException e) {
            LOG.error("MalformedURL: " + e.getMessage());
        } catch (RemoteException e) {
            LOG.error("RemoteException: " + e.getMessage());
        } catch (AlreadyBoundException e) {
            LOG.error("Object has already been bound.");
        }
    }

    /**
     * Sets the Calculation Behaviour used by the Calculators
     *
     * @param calculationBehaviour The new calculation behaviour
     * @throws RemoteException if registry could not be contacted
     */
    public void setCalculationBehaviour(CalculationBehaviour calculationBehaviour) throws RemoteException {
        LOG.info("Setting Calculation Behaviour to " + calculationBehaviour);
        try {
            Naming.rebind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, new GaussLegendre());
        } catch (MalformedURLException e) {
            LOG.error("MalformedURL: " + e.getMessage());
        }
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        BigDecimal result;
        while (true) {

            if (this.calculatorRegistryService.getCalculatorList().size() == 0) {
                RemoteException e = new RemoteException("No calculators servers available!");
                LOG.error("An Error occured balancing a request", e);
                throw e;
            }
            if (this.lastCalculator >= this.calculatorRegistryService.getCalculatorList().size()) {
                this.lastCalculator = 0;
            }

            Calculator calc = this.calculatorRegistryService.getCalculatorList().get(this.lastCalculator);
            this.lastCalculator++;

            try {
                LOG.info("Routing request with " + decimalPlaces + " decimal places to service " + calc);
                result = calc.pi(decimalPlaces);
                break;
            } catch (RemoteException re) {
                if (re.getCause() instanceof ConnectException) {
                    LOG.warn("Removing Calculator " + calc + " from service list due to a lost connection!");
                    this.calculatorRegistryService.unregisterCalculator(calc);
                } else {
                    LOG.error("An Error occured balancing a request", re);
                    throw re;
                }
            }
        }
        return result;
    }
}
