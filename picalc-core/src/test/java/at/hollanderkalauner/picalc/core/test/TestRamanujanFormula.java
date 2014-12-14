package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.calculationbehaviour.RamanujanFormula;
import at.hollanderkalauner.picalc.core.testhelper.StaticTestValues;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Ramanujan Formula Algorithm to calculate pi
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestRamanujanFormula {

    /**
     * Test Ramanujan Formula against PI
     */
    @Test
    public void testRamanujanFormula() {
        assertEquals(StaticTestValues.PI, new RamanujanFormula().calculatePi(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);
    }

}
