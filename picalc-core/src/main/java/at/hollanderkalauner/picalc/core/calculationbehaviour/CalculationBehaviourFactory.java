package at.hollanderkalauner.picalc.core.calculationbehaviour;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory to create CalculationBehaviours
 *
 * @author Paul Kalauner
 * @version 20141212.1
 */
public class CalculationBehaviourFactory {
    private static final Logger LOG = LogManager.getLogger(CalculationBehaviourFactory.class);

    /**
     * Create a new Pi Calculation Behaviour based on the name
     *
     * @param behaviour Behaviour to create
     * @return Created Behaviour
     */
    public static CalculationBehaviour createBehaviour(String behaviour) {
        LOG.debug("Creating Behaviour " + behaviour);
        switch (behaviour) {
            case "gausslegendre":
                return new GaussLegendre();
            case "ramanujanformula":
                return new RamanujanFormula();
            default:
                throw new IllegalBehaviourException("The behaviour " + behaviour + " is not a valid CalculationBehaviour!");
        }
    }
}