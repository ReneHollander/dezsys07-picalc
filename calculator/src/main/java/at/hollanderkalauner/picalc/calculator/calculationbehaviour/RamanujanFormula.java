package at.hollanderkalauner.picalc.calculator.calculationbehaviour;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by rene on 12/12/14.
 */
public class RamanujanFormula implements CalculationBehaviour {

    private static final int TERMS = 250;

    @Override
    public BigDecimal calculatePi(int decimalPlaces) {
        return calculatePiImpl(decimalPlaces, TERMS);
    }

    private static BigDecimal calculatePiImpl(int decimalplaces, int terms) {
        BigDecimal inverse_pi, Pi, numerator, divisor;
        BigDecimal[] sigma = new BigDecimal[terms];
        BigDecimal[] term = new BigDecimal[terms];

        // This coeeficient multiplies the final sum of terms
        BigDecimal root_2 = Sqrt_2(200);
        BigDecimal coefficient = root_2.multiply(BigDecimal.valueOf(2)).divide(BigDecimal.valueOf(9801), 1000, BigDecimal.ROUND_HALF_UP);

        // Calculate sum of terms
        for (int i = 0; i < terms; i++) {
            // Calculate next term
            try {
                numerator = new BigDecimal(LargeFactorial(4 * i).multiply(BigInteger.valueOf(1103 + 26390 * i)));
                divisor = new BigDecimal(LargeFactorial(i).pow(4).multiply(BigInteger.valueOf(396).pow(4 * i)));
                term[i] = (numerator.divide(divisor, decimalplaces, BigDecimal.ROUND_HALF_UP));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            // Summate terms and store cumulative sum in the array sigma
            if (i == 0) {
                sigma[i] = term[i];
            }
            if (i > 0) {
                sigma[i] = term[i].add(sigma[i - 1]);
            }
        }
        // Final algebra multiply sum by coefficient and invert
        inverse_pi = coefficient.multiply(sigma[terms - 1]);
        BigDecimal unity = new BigDecimal("1");
        Pi = unity.divide(inverse_pi, decimalplaces, BigDecimal.ROUND_HALF_UP);

        return Pi;
    }

    // Calculates factorials of large values using BigInteger
    private static BigInteger LargeFactorial(int n) throws IllegalArgumentException {
        if (n == -1) {
            throw new IllegalArgumentException("Negative factorial not defined");
        }
        BigInteger factorial = new BigInteger("1");
        if (n == 0 || n == 1) {
            return factorial;
        }
        while (n > 1) {
            BigInteger N = BigInteger.valueOf(n);
            factorial = factorial.multiply(N);
            // System.out.println(factorial);
            n--;
        }
        return factorial;
    }

    private static BigDecimal Sqrt_2(int terms) {
        BigDecimal root_2;
        BigDecimal[] buffer = new BigDecimal[terms + 1];
        BigDecimal initial = new BigDecimal("1.4");
        buffer[0] = initial;
        for (int i = 1; i <= terms; i++) {
            buffer[i] = buffer[i - 1].divide(BigDecimal.valueOf(2), terms * 5, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(1).divide(buffer[i - 1], terms * 5, BigDecimal.ROUND_HALF_UP));
        }
        root_2 = buffer[terms];
        return root_2;
    }
}
