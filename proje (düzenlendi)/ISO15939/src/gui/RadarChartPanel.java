package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

/**
 * Bonus: Draws a radar (spider) chart for dimension scores using Java 2D Graphics.
 */
public class RadarChartPanel extends JPanel {

    private List<model.Dimension> dimensions;

    private static final Color COLOR_WEB    = new Color(200, 200, 220);
    private static final Color COLOR_FILL   = new Color(30, 100, 200, 70);
    private static final Color COLOR_STROKE = new Color(30, 100, 200);
    private static final Color COLOR_LABEL  = new Color(50, 50, 80);

    public RadarChartPanel(List<model.Dimension> dimensions) {
        this.dimensions = dimensions;
        setBackground(Color.WHITE);
        setPreferredSize(new java.awt.Dimension(380, 320));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dimensions == null || dimensions.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w  = getWidth();
        int h  = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        int radius = Math.min(w, h) / 2 - 60;

        int n = dimensions.size();
        if (n < 3) {
            g2.drawString("Need at least 3 dimensions for radar chart.", 20, cy);
            g2.dispose();
            return;
        }

        // --- Draw background web (5 levels: 1..5) ---
        g2.setStroke(new BasicStroke(1f));
        for (int level = 1; level <= 5; level++) {
            double r = radius * level / 5.0;
            Path2D web = new Path2D.Double();
            for (int i = 0; i < n; i++) {
                double angle = Math.PI / 2 + 2 * Math.PI * i / n;
                double x = cx + r * Math.cos(angle);
                double y = cy - r * Math.sin(angle);
                if (i == 0) web.moveTo(x, y);
                else        web.lineTo(x, y);
            }
            web.closePath();
            g2.setColor(COLOR_WEB);
            g2.draw(web);
        }

        // --- Draw axis lines ---
        g2.setColor(new Color(180, 180, 200));
        for (int i = 0; i < n; i++) {
            double angle = Math.PI / 2 + 2 * Math.PI * i / n;
            int x2 = (int)(cx + radius * Math.cos(angle));
            int y2 = (int)(cy - radius * Math.sin(angle));
            g2.drawLine(cx, cy, x2, y2);
        }

        // --- Draw data polygon ---
        Path2D data = new Path2D.Double();
        for (int i = 0; i < n; i++) {
            double score = dimensions.get(i).calculateScore();
            double r = radius * score / 5.0;
            double angle = Math.PI / 2 + 2 * Math.PI * i / n;
            double x = cx + r * Math.cos(angle);
            double y = cy - r * Math.sin(angle);
            if (i == 0) data.moveTo(x, y);
            else        data.lineTo(x, y);
        }
        data.closePath();

        g2.setColor(COLOR_FILL);
        g2.fill(data);
        g2.setColor(COLOR_STROKE);
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(data);

        // --- Draw data points ---
        for (int i = 0; i < n; i++) {
            double score = dimensions.get(i).calculateScore();
            double r = radius * score / 5.0;
            double angle = Math.PI / 2 + 2 * Math.PI * i / n;
            int px = (int)(cx + r * Math.cos(angle));
            int py = (int)(cy - r * Math.sin(angle));
            g2.setColor(COLOR_STROKE);
            g2.fillOval(px - 4, py - 4, 8, 8);
        }

        // --- Draw labels ---
        g2.setColor(COLOR_LABEL);
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i < n; i++) {
            double angle = Math.PI / 2 + 2 * Math.PI * i / n;
            int lx = (int)(cx + (radius + 28) * Math.cos(angle));
            int ly = (int)(cy - (radius + 28) * Math.sin(angle));

            String name  = dimensions.get(i).getName();
            String score = String.format("%.2f", dimensions.get(i).calculateScore());
            int sw = fm.stringWidth(name);

            g2.drawString(name,  lx - sw / 2, ly);
            g2.drawString(score, lx - fm.stringWidth(score) / 2, ly + 13);
        }

        // --- Title ---
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(new Color(30, 100, 200));
        String chartTitle = "Radar Chart — Dimension Scores";
        g2.drawString(chartTitle, cx - fm.stringWidth(chartTitle) / 2, 18);

        g2.dispose();
    }
}
