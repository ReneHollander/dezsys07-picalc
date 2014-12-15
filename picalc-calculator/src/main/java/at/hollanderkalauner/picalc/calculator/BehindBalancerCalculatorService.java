package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculatorRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Creates a Calculator Service running behind a balancer
 *
 * @author Rene Hollander
 * @version 20141215.1
 */
public class BehindBalancerCalculatorService extends CalculatorService {

    private static final Logger LOG = LogManager.getLogger(BehindBalancerCalculatorService.class);

    private Registry registry;

    private String balancerHostname;
    private int balancerPort;

    /**
     * Initializes and new Calculator Service running behind a balancer
     *
     * @param balancerHostname Hostname of the Balancer
     * @param balancerPort     Port of the Balancer
     * @throws java.rmi.RemoteException on failure
     */
    public BehindBalancerCalculatorService(String balancerHostname, int balancerPort) throws RemoteException {
        this.balancerHostname = balancerHostname;
        this.balancerPort = balancerPort;
    }

    @Override
    public void start(int port) throws Exception {
        LOG.info("Starting service " + this.toString() + " in behind balancer mode!");
        RMIUtil.setupPolicy();
        this.registry = LocateRegistry.getRegistry(this.balancerHostname, this.balancerPort);

        this.getCalculatorRegistry().registerCalculator(this);
        LOG.info("Successfully started service " + this.toString() + " in behind balance mode!");
    }

    @Override
    public void start() throws Exception {
        this.start(-1);
    }

    @Override
    public CalculationBehaviour getCalculationBehaviour() {
        try {
            return (CalculationBehaviour) this.getRegistry().lookup(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
        } catch (Exception e) {
            LOG.error("Error getting CalculationBehaviour from Registry", e);
        }
        return null;
    }

    /**
     * Get the currently used Registry
     *
     * @return Registry
     */
    public Registry getRegistry() {
        return this.registry;
    }

    private CalculatorRegistry getCalculatorRegistry() throws RemoteException, NotBoundException {
        return (CalculatorRegistry) this.getRegistry().lookup(Static.BALANCER_CALCULATORREGISTRY_NAME);
    }

    @Override
    public void close() throws Exception {
        LOG.info("Unexporting Objects and closing Registry!");

        this.getCalculatorRegistry().unregisterCalculator(this);
        UnicastRemoteObject.unexportObject(this, true);

        LOG.info("Successfully unexported Objects and closed Registry!");
    }


}
