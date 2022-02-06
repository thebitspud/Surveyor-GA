package io.thebitspud.vrp.model;

import io.thebitspud.vrp.ui.Main;

import java.util.Arrays;

/**
 * Represents a candidate solution for the genetic algorithm
 */
public class Solution {
    /** Order in which all locations are visited */
    private int[] routes;

    /** Generates a new solution with random parameters */
    public Solution() {
        // location 0 is at the start and end of every path, no need to include
        routes = new int[Main.LOCATION_COUNT + Main.FLEET_SIZE - 2];
        Arrays.setAll(routes, i -> i + 1);

        // Randomly shuffling assignments
        for (int i = routes.length - 1; i > 0; i--) {
            int j = Main.r.nextInt(i);
            int temp = routes[j];
            routes[j] = routes[i];
            routes[i] = temp;
        }
    }

    /** Creates a new solution by cloning a parent, then mutating */
    public Solution(Solution parent) {
        this.routes = parent.routes.clone();

        mutate();
    }

    /** Creates a new solution by crossing two parents */
    public Solution(Solution p1, Solution p2) {
        this.routes = new int[p1.routes.length];
        Arrays.fill(routes, -1);

        // Copying random range of routes from first parent
        int cStart = Main.r.nextInt(p1.routes.length - 1);
        int cEnd = Main.r.nextInt(p1.routes.length - cStart - 1) + (cStart + 1);
        System.arraycopy(p1.routes, cStart, routes, cStart, cEnd - cStart);

        // Using a lookup array is much faster than alternatives
        boolean[] p2Contains = new boolean[p1.routes.length];
        for (int j : routes) {
            if (j >= 0) p2Contains[j - 1] = true;
        }

        // Order crossover (OX)
        int p2Index = cEnd;
        for (int i = 0; i < routes.length; i++) {
            if (i >= cStart && i < cEnd) continue;
            while (routes[i] == -1) {
                final int location = p2.routes[p2Index];
                if (!p2Contains[location - 1]) {
                    routes[i] = p2.routes[p2Index];
                }

                if (p2Index >= routes.length - 1) p2Index = 0;
                else p2Index++;
            }
        }

        if (!Main.EXCLUSIVE_CROSS) mutate();
    }

    /** Randomly performs mutations on parameters */
    public void mutate() {
        if (Main.MAJOR_MUTATION_RATE > Main.r.nextDouble()) {
            inversionMutation();
            if (Main.EXCLUSIVE_MAJOR) return;
        }

        if (Main.MINOR_MUTATION_RATE > Main.r.nextDouble()) {
            if (Main.r.nextBoolean()) shiftMutation();
            else swapMutation();
        }
    }

    /** Performs a random inversion mutation on the solution */
    private void inversionMutation() {
        int min = 2;
        int a = Main.r.nextInt(routes.length - min);
        int b = Main.r.nextInt(routes.length - min);
        int span = Math.abs(a - b) + min;
        int start = Main.r.nextInt(routes.length - span);

        int[] temp = new int[span];
        System.arraycopy(routes, start, temp, 0, span);

        // Reversing order of subarray
        for (int i = 0; i < span; i++) {
            routes[start + span - i - 1] = temp[i];
        }
    }

    /** Performs a random shift mutation on the solution */
    private void shiftMutation() {
        int from = Main.r.nextInt(routes.length);
        int to = Main.r.nextInt(routes.length);
        int value = routes[from];

        // Creating new array with element removed
        int[] temp = new int[routes.length - 1];
        for (int i = 0; i < temp.length; i++) {
            if (i < from) temp[i] = routes[i];
            else temp[i] = routes[i + 1];
        }

        // Re-adding element at new position
        for (int i = 0; i < routes.length; i++) {
            if (i < to) routes[i] = temp[i];
            else if (i > to) routes[i] = temp[i - 1];
            else routes[i] = value;
        }
    }

    /** Performs a random swap mutation on the solution */
    private void swapMutation() {
        int a = Main.r.nextInt(routes.length);
        int b = Main.r.nextInt(routes.length);

        int temp = routes[a];
        routes[a] = routes[b];
        routes[b] = temp;
    }

    public int getRoutes(int i) {
        return routes[i];
    }

    public int length() {
        return routes.length;
    }

    /** Returns a string representation of the solution's route order */
    @Override
    public String toString() {
        return Arrays.toString(routes);
    }
}
