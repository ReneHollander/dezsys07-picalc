package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.calculationbehaviour.RamanujanFormula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Ramanujan Formula Algorithm to calculate pi
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestRamanujanFormula {

    private static final double PI = 3.14159265359;
    private static final int PI_DECIMAL_PLACES = 11;

    /**
     * Test Ramanujan Formula against PI
     */
    @Test
    public void testRamanujanFormula() {
        assertEquals(PI, new RamanujanFormula().calculatePi(PI_DECIMAL_PLACES).doubleValue(), 0.000001);
    }

}
