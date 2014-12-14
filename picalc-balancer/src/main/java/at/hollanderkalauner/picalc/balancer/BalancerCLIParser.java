package at.hollanderkalauner.picalc.balancer;

import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.calculationbehaviour.CalculationBehaviourFactory;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.IllegalBehaviourException;
import org.apache.commons.cli.*;

/**
 * Parses the given arguments
 *
 * @author Paul Kalauner
 * @version 20141213.1
 */
public class BalancerCLIParser {
    private Balancer balancer;
    private CalculationBehaviour calcBehav;

    /**
     * Initializes the parser with the given {@code Balancer} instance
     *
     * @param balancer instance of {@code Balancer}
     */
    public BalancerCLIParser(Balancer balancer) {
        this.balancer = balancer;
    }

    /**
     * Checks arguments and binds the balancer if the arguments are valid
     *
     * @param args arguments that will be validated
     */
    public void start(String[] args) {
        if (checkArgs(args))
            bind();
        else System.exit(0);
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
        options.addOption(OptionBuilder.hasArg().withArgName("behaviour").withDescription("The algorithm used to calculate Pi. Valid options: gausslegendre, ramanujanformula").withLongOpt("behaviour").create('b'));
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
            } else {
                calcBehav = new GaussLegendre();
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
     * Binds the Balancer
     */
    private void bind() {
        this.balancer.bind(calcBehav);
    }

    /**
     * Used for the purpose of testing.
     *
     * @return the used CalculationBehaviour
     */
    public CalculationBehaviour getCalcBehav() {
        return calcBehav;
    }
}
