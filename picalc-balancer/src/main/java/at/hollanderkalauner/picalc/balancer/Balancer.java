package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.Calculator;
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
 * Created by rene on 12/6/14.
 */
public class Balancer extends UnicastRemoteObject implements Calculator {

    private static final Logger LOG = LogManager.getLogger(Balancer.class);

    private CalculatorRegistryService calculatorRegistryService;
    private int lastCalculator;

    public Balancer() throws RemoteException {
        super();

        this.calculatorRegistryService = new CalculatorRegistryService();
        this.lastCalculator = 0;
    }

    public void bind(CalculationBehaviour initialCalculationBehaviour) throws RemoteException, AlreadyBoundException, MalformedURLException {
        LOG.info("Binding Balancer");
        RMIUtil.setupPolicy();
        RMIUtil.setupRegistry();

        this.setCalculationBehaviour(initialCalculationBehaviour);
        Naming.bind(Static.BALANCER_CALCULATORREGISTRY_NAME, this.calculatorRegistryService);
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, this);
        LOG.info("Successfully bound Balancer");
    }

    public void setCalculationBehaviour(CalculationBehaviour calculationBehaviour) throws MalformedURLException, RemoteException {
        LOG.info("Setting Calculation Behaviour to " + calculationBehaviour);
        Naming.rebind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, new GaussLegendre());
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
