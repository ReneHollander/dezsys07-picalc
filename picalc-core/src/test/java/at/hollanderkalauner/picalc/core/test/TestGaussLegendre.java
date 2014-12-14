package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.testhelper.StaticTestValues;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Gauss Legendre Algorithm to calculate pi
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestGaussLegendre {

    /**
     * Test Gauss Legendre against PI
     */
    @Test
    public void testGaussLegendre() {
        assertEquals(StaticTestValues.PI, new GaussLegendre().calculatePi(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);
    }

}
