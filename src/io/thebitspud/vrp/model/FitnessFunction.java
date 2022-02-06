package io.thebitspud.vrp.model;

/**
 * Represents a fitness function for the genetic algorithm
 */
public interface FitnessFunction {
    /** Computes and returns a fitness score for the specified solution */
    float evaluate(Solution s);
}