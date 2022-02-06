package io.thebitspud.vrp.model;

import io.thebitspud.vrp.ui.Main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Represents the algorithm's current batch of candidate solutions
 */
public class Population {
    private ArrayList<Solution> solutions;
    private LocationMap map;

    private float mean, min;
    private int size, generation;

    /** Creates a new population of the specified size */
    public Population(int size) {
        this.size = size;
        solutions = new ArrayList<>(size);
        reset();
    }

    /** Resets the simulation back to generation 0 */
    public void reset() {
        map = new LocationMap(Main.LOCATION_COUNT);

        generation = 0;
        solutions.clear();
        for (int i = 0; i < size; i++) {
            solutions.add(new Solution());
        }

        sortSolutions();
        computeStats();
    }

    /** Advances the simulation by the specified number of generations */
    public void simulate(int count) {
        for (int i = 0; i < count; i++) nextGeneration();
        computeStats();
    }

    /** Generates a new generation of solutions via genetic operations */
    public void nextGeneration() {
        generation++;

        // Performing a simple truncation selection operation by removing
        // the bottom (least fit) portion of solutions from the population
        solutions.subList(Main.ELITISM, size).clear();

        // Propagating and mutating solutions from remaining pool
        int validParents = solutions.size();
        while (solutions.size() < size) {
            Solution p1 = solutions.get(Main.r.nextInt(validParents));
            if (Main.CROSSOVER_RATE > Main.r.nextDouble()) {
                Solution p2 = solutions.get(Main.r.nextInt(validParents));
                solutions.add(new Solution(p1, p2));
            } else {
                solutions.add(new Solution(p1));
            }
        }

        sortSolutions();
    }

    /** Sorts solutions by fitness in ascending order (lower is better) */
    public void sortSolutions() {
        // Faster than plugging the eval function into the comparator
        HashMap<Solution, Float> evals = new HashMap<>();
        for (Solution s: solutions) evals.put(s, map.evaluate(s));
        solutions.sort(Comparator.comparingDouble(evals::get));
    }

    /** Computes common population parameters */
    public void computeStats() {
        float total = 0;
        min = Float.MAX_VALUE;

        for (int i = 0; i < size; i++) {
            float eval = map.evaluate(solutions.get(i));
            min = Math.min(min, eval);
            total += eval;
        }

        mean = total / size;
    }

    /** Returns a string representation of population parameters */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.000");

        return "Generation: " + generation
                + "  Best: " + df.format(Math.sqrt(min / Main.FLEET_SIZE))
                + "  Mean: " + df.format(Math.sqrt(mean / Main.FLEET_SIZE));
    }

    public int getGeneration() {
        return generation;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public LocationMap getMap() {
        return map;
    }
}
