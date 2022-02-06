package io.thebitspud.vrp.model;

import io.thebitspud.vrp.ui.Main;

import java.awt.Point;

/**
 * Represents a set of locations that are visited by solutions
 */
public class LocationMap implements FitnessFunction {
    private Point[] locations;

    /** Creates a new map containing the specified number of random locations */
    public LocationMap(int count) {
        locations = new Point[count];

        // Generate random locations with x,y coordinates in [0, 500)
        for(int i = 0; i < locations.length; i++) {
            locations[i] = new Point(Main.r.nextInt(500), Main.r.nextInt(500));
        }

        if (Main.CENTER_START) {
            locations[0] = new Point(250, 250);
        }
    }

    /**
     * Returns the sum of squared distances travelled by a candidate solution<br>
     * Lower numbers are considered more fit
     */
    @Override
    public float evaluate(Solution s) {
        Point last = locations[0];
        float dist = 0;
        float max = 0;
        float total = 0;

        // Calculating distance travelled
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.getRoutes(i) >= locations.length) {
                // Next path
                dist += locations[0].distance(last);
                if (dist > max) max = dist;
                total += dist * dist;
                dist = 0;
                last = locations[0];
            } else {
                // Next location
                Point p = locations[s.getRoutes(i)];
                dist += p.distance(last);
                last = p;
            }
        }

        // return max;
        return total;
    }

    public Point getLocation(int index) {
        return locations[index];
    }
}
