package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A panel displayed at the top of the main window.
 * Shows step numbers, names, and completion status.
 */
public class StepIndicatorPanel extends JPanel {

    private static final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private static final Color COLOR_DONE    = new Color(34, 139, 34);   // green
    private static final Color COLOR_ACTIVE  = new Color(30, 100, 200);  // blue
    private static final Color COLOR_PENDING = new Color(160, 160, 160); // grey

    private int currentStep; // 0-based

    public StepIndicatorPanel() {
        this.currentStep = 0;
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 210)));
        setPreferredSize(new Dimension(800, 70));
        setLayout(new GridLayout(1, STEP_NAMES.length));
    }

    /** Update which step is currently active (0-based index). */
    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth  = getWidth();
        int panelHeight = getHeight();
        int cellWidth   = panelWidth / STEP_NAMES.length;

        for (int i = 0; i < STEP_NAMES.length; i++) {

            int cx = i * cellWidth + cellWidth / 2;
            int cy = panelHeight / 2 - 5;

            Color circleColor;
            String label;

            if (i < currentStep) {
                circleColor = COLOR_DONE;
                label = "✓";
            } else if (i == currentStep) {
                circleColor = COLOR_ACTIVE;
                label = String.valueOf(i + 1);
            } else {
                circleColor = COLOR_PENDING;
                label = String.valueOf(i + 1);
            }

            // Draw connector line (except for the first step)
            if (i > 0) {
                int prevCx = (i - 1) * cellWidth + cellWidth / 2;
                g2.setColor(i <= currentStep ? COLOR_DONE : COLOR_PENDING);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(prevCx + 14, cy, cx - 14, cy);
            }

            // Draw circle
            g2.setColor(circleColor);
            g2.fillOval(cx - 13, cy - 13, 26, 26);

            // Draw circle label
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontMetrics fm = g2.getFontMetrics();
            int lx = cx - fm.stringWidth(label) / 2;
            int ly = cy + fm.getAscent() / 2 - 1;
            g2.drawString(label, lx, ly);

            // Draw step name below circle
            g2.setColor(i == currentStep ? COLOR_ACTIVE : (i < currentStep ? COLOR_DONE : COLOR_PENDING));
            g2.setFont(new Font("SansSerif", i == currentStep ? Font.BOLD : Font.PLAIN, 11));
            FontMetrics fm2 = g2.getFontMetrics();
            String name = STEP_NAMES[i];
            int nx = cx - fm2.stringWidth(name) / 2;
            g2.drawString(name, nx, cy + 22);
        }
        g2.dispose();
    }
}
