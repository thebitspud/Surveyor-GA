package io.thebitspud.vrp.ui;

import io.thebitspud.vrp.model.LocationMap;
import io.thebitspud.vrp.model.Population;
import io.thebitspud.vrp.model.Solution;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Draws the best solution in a population to the screen
 */
public class Visualizer extends JPanel {
    private Population pop;
    private Display display;

    /** Creates a new visualizer based on the specified population */
    public Visualizer(Population pop, Display display) {
        this.pop = pop;
        this.display = display;

        start();
    }

    /** Starts the application loop */
    private void start() {
        Timer t = new Timer(1000 / 30, ae -> repaint());
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Solution s = pop.getSolutions().get(0);
        LocationMap m = pop.getMap();
        Point base = m.getLocation(0);

        // Drawing paths
        for (int i = -1; i < s.length(); i++) {
            if (i == -1 || s.getRoutes(i) >= Main.LOCATION_COUNT) {
                if (i == s.length() - 1 || s.getRoutes(i + 1) >= Main.LOCATION_COUNT) continue;
                // Drawing lines to start points for paths
                Point start = m.getLocation(s.getRoutes(i + 1));
                g.setColor(Color.BLUE);
                g.drawLine(base.x + 50, base.y + 50, start.x + 50, start.y + 50);
            } else if (i == s.length() - 1 || s.getRoutes(i + 1) >= Main.LOCATION_COUNT) {
                if (s.getRoutes(i) >= Main.LOCATION_COUNT) continue;
                // Drawing lines to end points for paths
                Point last = m.getLocation(s.getRoutes(i));
                g.setColor(Color.RED);
                g.drawLine(base.x + 50, base.y + 50, last.x + 50, last.y + 50);
            } else {
                // Drawing lines between path locations
                Point p1 = m.getLocation(s.getRoutes(i));
                Point p2 = m.getLocation(s.getRoutes(i + 1));
                g.setColor(new Color(90, 110, 130));
                g.drawLine(p1.x + 50, p1.y + 50, p2.x + 50, p2.y + 50);
            }
        }

        // Drawing location nodes
        g.setColor(Color.BLACK);
        for (int i = 0; i < Main.LOCATION_COUNT; i++) {
            Point p1 = m.getLocation(i);
            g.fillRect(p1.x + 48, p1.y + 48, 5, 5);
        }

        // Drawing base node
        g.setColor(Color.RED);
        g.fillOval(base.x + 46, base.y + 46, 8, 8);
        g.drawRect(45, 45, 510, 510);

        display.setTitle(
                "Nodes: " + Main.LOCATION_COUNT
                + " | Agents: " + Main.FLEET_SIZE
                + " | Generation: " + pop.getGeneration());
    }
}
