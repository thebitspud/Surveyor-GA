package io.thebitspud.vrp.ui;

import io.thebitspud.vrp.model.Population;

import java.util.Scanner;
import java.util.SplittableRandom;

/**
 * Runs a command-line simulation of the genetic algorithm to solve the
 * travelling salesman or vehicle routing problem
 */
public class Main {
    /* Scenario parameters */
    public static final int LOCATION_COUNT = 150;
    public static final int FLEET_SIZE = 3; // 1 for travelling salesman
    public static final boolean CENTER_START = true;

    /* Simulation parameters */
    public static final int POPULATION_SIZE = 100;
    public static final int ELITISM = 10; // in (0, POPULATION_SIZE)
    public static final float CROSSOVER_RATE = 0.25f; // in [0, 1]
    public static final boolean EXCLUSIVE_CROSS = true;
    public static final float MAJOR_MUTATION_RATE = 0.5f; // in [0, 1]
    public static final boolean EXCLUSIVE_MAJOR = true;
    public static final float MINOR_MUTATION_RATE = 1f; // in [0, 1]

    // SEED = (new SplittableRandom()).nextLong();
    public static final long SEED = (new SplittableRandom()).nextLong();
    public static final SplittableRandom r = new SplittableRandom(SEED);

    public static void main(String[] args) {
        Population pop = new Population(POPULATION_SIZE);
        printHelp();
        System.out.println(pop);

        Display d = new Display(606, 635);
        d.addCanvasPanel(new Visualizer(pop, d));
        d.setVisible(true);

        Scanner in = new Scanner(System.in);
        boolean running = true;

        // Application loop
        while (running) {
            String[] input = in.nextLine().toLowerCase().split(" ");

            switch (input[0]) {
                case "x":
                    running = false;
                    break;
                case "h":
                    printHelp();
                    break;
                case "d":
                    d.setVisible(!d.isVisible());
                    break;
                case "r":
                    System.out.println("Resetting simulation...");
                    pop.reset();
                    System.out.println(pop);
                    break;
                case "nk":
                    if (input.length == 1) {
                        input = new String[]{input[0], "1"};
                    }

                    input[1] += "000";
                case "n":
                    trySimulate(pop, input);
                    break;
                default:
                    System.out.println("Invalid input '" + input[0] + "'");
                    System.out.println("Type 'h' for help.");
                    break;
            }
        }

        System.exit(0);
    }

    /** Prints out a list of valid console commands */
    public static void printHelp() {
        System.out.println("Commands:");
        System.out.println("'n <c>' to simulate c generations");
        System.out.println("'nk <c>' to simulate 1000*c generations");
        System.out.println("'d' to toggle visualizer");
        System.out.println("'r' to reset simulation");
        System.out.println("'x' to exit");
    }

    /** Attempts to advance the simulation according to provided parameters */
    public static void trySimulate(Population pop, String[] input) {
        if (input.length > 1) {
            try {
                int count = Integer.parseInt(input[1]);
                if (count < 1) {
                    System.out.println("Invalid parameter '" + input[1] + "'");
                    return;
                }

                // Advance only if a valid parameter is provided
                System.out.println("Simulating " + count + " generations...");
                pop.simulate(count);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter '" + input[1] + "'");
                return;
            }
        } else pop.simulate(1);

        System.out.println(pop);
    }
}
