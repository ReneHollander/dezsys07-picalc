package at.hollanderkalauner.picalc.balancer;

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
public class BalancerCLIParser {
    private CalculationBehaviour calcBehav;
    private int port;

    /**
     * Initializes the parser
     */
    public BalancerCLIParser() {
        this.calcBehav = new GaussLegendre();
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
        options.addOption(OptionBuilder.hasArg().withArgName("portnumber").withLongOpt("port").withType(Number.class).withDescription("Port to bind on (Default: 1099)").create('p'));
        options.addOption(OptionBuilder.hasArg().withArgName("behaviour").withDescription("The algorithm used to calculate Pi. Valid options: gausslegendre, ramanujanformula (Default: GaussLegendre)").withLongOpt("behaviour").create('b'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                hf.printHelp("java -jar Balancer.jar", options, true);
                return false;
            }

            // If a behaviour has been given use it. Otherwise use GaussLegendre.
            if (cmd.hasOption('b')) {
                calcBehav = CalculationBehaviourFactory.createBehaviour(cmd.getOptionValue('b'));
            }

            if (cmd.hasOption('p')) {
                this.port = ((Number) cmd.getParsedOptionValue("p")).intValue();
            }
        } catch (IllegalBehaviourException ibe) {
            System.out.println("Invalid Behaviour");
            return false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar Balancer.jar", options, true);
            return false;
        }
        return true;
    }


    /**
     * @return the used CalculationBehaviour
     */
    public CalculationBehaviour getCalcBehav() {
        return calcBehav;
    }

    /**
     * @return port number
     */
    public int getPort() {
        return port;
    }
}
