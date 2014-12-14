package at.hollanderkalauner.picalc.core.remoteobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;

/**
 * CalculationBehavoiur Interface
 *
 * @author Rene Hollander
 * @version 20141212.1
 */
public interface CalculationBehaviour extends Remote, Serializable {

    /**
     * Calculates Pi with the given decimalPlaces
     *
     * @param decimalPlaces decimalPlaces
     * @return Pi as BigDecimal
     */
    public BigDecimal calculatePi(int decimalPlaces);

}
