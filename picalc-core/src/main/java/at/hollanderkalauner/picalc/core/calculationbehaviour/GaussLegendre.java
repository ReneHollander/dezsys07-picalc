package at.hollanderkalauner.picalc.core.calculationbehaviour;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;

import java.math.BigDecimal;

/**
 * Gauss Legendre Algorithm to calculate Pi <br>
 * Source: http://java.lykkenborg.no/2005/03/computing-pi-using-bigdecimal.html
 * Last Viewed: 12.12.2014
 *
 * @author Gaute Lykkenborg
 * @version 20050312.1
 */
public class GaussLegendre implements CalculationBehaviour {
    private static final BigDecimal TWO = new BigDecimal(2);
    private static final BigDecimal FOUR = new BigDecimal(4);

    @Override
    public BigDecimal calculatePi(int decimalPlaces) {
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.ONE.divide(sqrt(TWO, decimalPlaces), decimalPlaces, BigDecimal.ROUND_HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = BigDecimal.ONE;
        BigDecimal y;

        while (!a.equals(b)) {
            y = a;
            a = a.add(b).divide(TWO, decimalPlaces, BigDecimal.ROUND_HALF_UP);
            b = sqrt(b.multiply(y), decimalPlaces);
            t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
            x = x.multiply(TWO);
        }

        return a.add(b).multiply(a.add(b)).divide(t.multiply(FOUR), decimalPlaces, BigDecimal.ROUND_HALF_UP);
    }

    // the Babylonian square root method (Newton's method)
    private static BigDecimal sqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));

        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, SCALE, BigDecimal.ROUND_HALF_UP);
        }

        return x1;
    }
}