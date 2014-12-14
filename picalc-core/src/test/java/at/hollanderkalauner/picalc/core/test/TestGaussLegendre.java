package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Gauss Legendre Algorithm to calculate pi
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestGaussLegendre {

    private static final double PI = 3.14159265359;
    private static final int PI_DECIMAL_PLACES = 11;

    /**
     * Test Gauss Legendre against PI
     */
    @Test
    public void testGaussLegendre() {
        assertEquals(PI, new GaussLegendre().calculatePi(PI_DECIMAL_PLACES).doubleValue(), 0.000001);
    }

}
