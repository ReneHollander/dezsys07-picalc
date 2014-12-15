package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import at.hollanderkalauner.picalc.core.calculationbehaviour.IllegalBehaviourException;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Creates a new Calculator Service in standalone mode
 *
 * @author Rene Hollander
 * @version 20141215.1
 */
public class StandaloneCalculatorService extends CalculatorService {

    private static final Logger LOG = LogManager.getLogger(StandaloneCalculatorService.class);

    private Registry registry;
    private CalculationBehaviour calculationBehaviour;

    /**
     * Initializes the CalculatorService
     *
     * @param calculationBehaviour the calculation behaviour that should be used
     * @throws java.rmi.RemoteException on failure
     */
    public StandaloneCalculatorService(CalculationBehaviour calculationBehaviour) throws RemoteException {
        if (calculationBehaviour == null)
            throw new IllegalBehaviourException("You need to specify a calculation behaviour to use!");
        this.calculationBehaviour = calculationBehaviour;
    }

    @Override
    public CalculationBehaviour getCalculationBehaviour() {
        return this.calculationBehaviour;
    }

    /**
     * Get the currently used Registry
     *
     * @return Registry
     */
    public Registry getRegistry() {
        return this.registry;
    }


    @Override
    public void start(int port) throws Exception {
        LOG.info("Starting service " + this.toString() + " in standalone mode!");
        RMIUtil.setupPolicy();
        this.registry = LocateRegistry.createRegistry(port);

        Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, calculationBehaviour);
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);
        LOG.info("Successfully started service " + this.toString() + " in standalone mode!");
    }

    @Override
    public void start() throws Exception {
        this.start(1099);
    }

    @Override
    public void close() throws Exception {
        LOG.info("Unexporting Objects and closing Registry!");

        this.getRegistry().unbind(Static.CALCULATOR_SERVICE_NAME);
        UnicastRemoteObject.unexportObject(this, true);

        // Close registry
        UnicastRemoteObject.unexportObject(this.getRegistry(), true);

        LOG.info("Successfully unexported Objects and closed Registry!");
    }


}
