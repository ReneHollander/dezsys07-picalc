package at.hollanderkalauner.picalc.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;

/**
 * Created by rene on 12/12/14.
 */
public interface CalculationBehaviour extends Remote, Serializable {

    public BigDecimal calculatePi(int decimalPlaces);

}
