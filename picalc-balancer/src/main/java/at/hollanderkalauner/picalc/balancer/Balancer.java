package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.interfaces.RMICloseable;
import at.hollanderkalauner.picalc.core.interfaces.RMIStartable;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * A Balancer for the Pi Calculator.
 * Adds a Calculator Service and a CaluclatorRegistry Service to register new Calculators.
 * The Client doesn't need to know if the Calculator is a Balancer or not.
 *
 * @author Rene Hollander
 * @version 20141213.1
 */
public class Balancer extends UnicastRemoteObject implements RMIStartable.Service, RMICloseable, Calculator {

    private static final Logger LOG = LogManager.getLogger(Balancer.class);

    private Registry registry;
    private CalculationBehaviour calculationBehaviour;
    private CalculatorRegistryService calculatorRegistryService;
    private int lastCalculator;

    /**
     * Constructs a new Balancer
     *
     * @param initialCalculationBehaviour initial Calculation Behaviour
     *
     * @throws java.rmi.RemoteException if failed to export object
     */
    public Balancer(CalculationBehaviour initialCalculationBehaviour) throws RemoteException {
        super();

        this.calculationBehaviour = initialCalculationBehaviour;
        this.calculatorRegistryService = new CalculatorRegistryService();
        this.lastCalculator = 0;
    }

    @Override
    public void start(int port) throws Exception {
        RMIUtil.setupPolicy();

        LOG.info("Creating Registry on Port " + port);
        this.registry = LocateRegistry.createRegistry(port);
        LOG.info("Successfully created Registry on Port " + port);

        LOG.info("Exporting Objects and Services to Registry");
        this.setCalculationBehaviour(this.calculationBehaviour);
        Naming.bind(Static.BALANCER_CALCULATORREGISTRY_NAME, this.calculatorRegistryService);
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);
        LOG.info("Successfully exported Objects and Services to the Registry");
    }

    @Override
    public void start() throws Exception {
        this.start(1099);
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
            this.calculationBehaviour = calculationBehaviour;
            Naming.rebind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, this.calculationBehaviour);
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

    /**
     * Gets the currently used Registry
     *
     * @return Registry
     */
    public Registry getRegistry() {
        return registry;
    }

    @Override
    public void close() throws Exception {
        LOG.info("Unexporting Objects and closing Registry!");

        this.getRegistry().unbind(Static.CALCULATOR_SERVICE_NAME);
        UnicastRemoteObject.unexportObject(this, true);

        this.getRegistry().unbind(Static.BALANCER_CALCULATORREGISTRY_NAME);
        UnicastRemoteObject.unexportObject(this.calculatorRegistryService, true);

        this.getRegistry().unbind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
        UnicastRemoteObject.unexportObject(this.calculationBehaviour, true);

        // Close registry
        UnicastRemoteObject.unexportObject(this.getRegistry(), true);

        LOG.info("Successfully unexported Objects and closed Registry!");
    }

}
