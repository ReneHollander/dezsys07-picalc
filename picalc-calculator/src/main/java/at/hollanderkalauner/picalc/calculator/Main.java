package at.hollanderkalauner.picalc.calculator;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        CalculatorService calculatorService = new CalculatorService();
        calculatorService.bind(CalculatorService.Mode.BEHINDBALANCER);
    }


}
