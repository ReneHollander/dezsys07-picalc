package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.calculationbehaviour.CalculationBehaviourFactory;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.IllegalBehaviourException;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import org.apache.commons.cli.*;

/**
 * Parses the given arguments
 *
 * @author Paul Kalauner
 * @version 20141213.1
 */
public class CalculatorCLIParser {


    /**
     * Represents the start mode of the CalculatorService
     */
    public static enum Mode {

        /**
         * CalculatorService will be started without Balancer
         */
        STANDALONE,

        /**
         * CalculatorService will be started with a Balancer
         */
        BEHINDBALANCER
    }

    private CalculationBehaviour calcBehav;

    private Mode mode;
    private String host;
    private int port;

    /**
     * Initializes the parser
     */
    public CalculatorCLIParser() {
        this.calcBehav = new GaussLegendre();
        this.host = "localhost";
        this.port = 1099;
    }

    /**
     * Validates the given Arguments<br>
     * This method is declared as public for the purpose of testing.
     *
     * @param args arguments to validate
     * @return true if args are valid
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public boolean checkArgs(String[] args) {
        Options options = new Options();
        options.addOption(OptionBuilder.withDescription("Shows a help dialog").create("help"));
        options.addOption(OptionBuilder.hasArg().withArgName("hostname").withLongOpt("host").withDescription("Hostname of the balancer (Default: localhost)").create('h'));
        options.addOption(OptionBuilder.hasArg().withArgName("portnumber").withLongOpt("port").withType(Number.class).withDescription("Port of the balancer (Default: 1099)").create('p'));
        options.addOption(OptionBuilder.withDescription("Starts the CalculatorService without Balancer").create("standalone"));
        options.addOption(OptionBuilder.hasArg().withArgName("behaviour").withDescription("The algorithm used to calculate Pi. Valid options: gausslegendre, ramanujanformula " +
                "(This argument is only used when using CalculatorService without Balancer) ").withLongOpt("behaviour").create('b'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                hf.printHelp("java -jar Balancer.jar", options, true);
                return false;
            }

            if (cmd.hasOption("standalone")) {
                this.mode = Mode.STANDALONE;
                if (cmd.hasOption('b')) {
                    calcBehav = CalculationBehaviourFactory.createBehaviour(cmd.getOptionValue('b'));
                }
            } else {
                this.mode = Mode.BEHINDBALANCER;
                if (cmd.hasOption('h')) {
                    this.host = cmd.getOptionValue('h');
                }

                if (cmd.hasOption('p')) {
                    this.port = ((Number) cmd.getParsedOptionValue("p")).intValue();
                }
            }
        } catch (IllegalBehaviourException ibe) {
            System.out.println("Invalid Behaviour " + (cmd != null ? cmd.getOptionValue('b') : null));
            return false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar Balancer.jar", options, true);
            return false;
        }
        return true;
    }


    /**
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return port number
     */
    public int getPort() {
        return port;
    }

    /**
     * @return used CalculationBehaviour
     */
    public CalculationBehaviour getCalcBehav() {
        return calcBehav;
    }

    /**
     * @return mode (either Standalone or BehindBalancer)
     */
    public Mode getMode() {
        return this.mode;
    }
}
