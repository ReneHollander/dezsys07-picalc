package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.calculationbehaviour.CalculationBehaviourFactory;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.IllegalBehaviourException;
import at.hollanderkalauner.picalc.core.calculationbehaviour.RamanujanFormula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the Calculation Behaviour Factory
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestCalculationBehaviourFactory {

    /**
     * Test creating a GaussLegendre Behaviour
     */
    @Test
    public void testCreateGaussLegendre() {
        assertEquals(GaussLegendre.class, CalculationBehaviourFactory.createBehaviour("gausslegendre").getClass());
    }

    /**
     * Test creating a RamanujanFormula Behaviour
     */
    @Test
    public void testCreateRamanujanFormula() {
        assertEquals(RamanujanFormula.class, CalculationBehaviourFactory.createBehaviour("ramanujanformula").getClass());
    }

    /**
     * Test creating an Invalid Behaviour
     */
    @Test(expected = IllegalBehaviourException.class)
    public void testCreateInvalid() {
        CalculationBehaviourFactory.createBehaviour("invalid");
    }

}
