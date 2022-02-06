package io.thebitspud.vrp.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A popup window that displays the best solution in a population
 */
public class Display extends JFrame {
    /** Creates a display window and initializes JFrame properties */
    public Display(int width, int height) {
        setTitle("Loading...");
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /** Adds a visualizer panel to the JFrame */
    public void addCanvasPanel(JPanel panel) {
        panel.setSize(getSize());
        panel.setFocusable(false);
        add(panel);
    }
}
