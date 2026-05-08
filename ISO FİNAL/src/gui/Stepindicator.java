package gui;
 
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
 
/**
 * Ekranın üstünde gösterilen adım göstergesi.
 * Tamamlanan adımlarda checkmark, aktif adım mavi vurgulanır.
 */
public class StepIndicator extends JPanel {
 
    private static final String[] STEP_NAMES =
            { "Profile", "Define", "Plan", "Collect", "Analyse" };
 
    private static final Color COL_ACTIVE  = new Color(0, 120, 215);
    private static final Color COL_DONE    = new Color(34, 139, 34);
    private static final Color COL_PENDING = new Color(180, 180, 180);
    private static final Color COL_LINE    = new Color(200, 200, 200);
    private static final Color COL_BG      = new Color(245, 247, 250);
 
    private int currentStep; // 0 tabanlı
 
    public StepIndicator() {
        this.currentStep = 0;
        setBackground(COL_BG);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 215, 220)));
        setPreferredSize(new Dimension(0, 66));
    }
 
    /** Aktif adımı günceller (0 tabanlı). */
    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 
        int w  = getWidth();
        int h  = getHeight();
        int n  = STEP_NAMES.length;
        int sw = w / n;
        int r  = 13;
        int cy = h / 2 - 8;
 
        for (int i = 0; i < n; i++) {
            int cx = sw * i + sw / 2;
 
            // Bağlantı çizgisi
            if (i > 0) {
                int prevCx = sw * (i - 1) + sw / 2;
                g2.setColor(i <= currentStep ? COL_DONE : COL_LINE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(prevCx + r, cy, cx - r, cy);
            }
 
            // Daire rengi ve içerik
            Color circleColor;
            String label;
            if (i < currentStep) {
                circleColor = COL_DONE;
                label = "\u2713";          // ✓
            } else if (i == currentStep) {
                circleColor = COL_ACTIVE;
                label = String.valueOf(i + 1);
            } else {
                circleColor = COL_PENDING;
                label = String.valueOf(i + 1);
            }
 
            // Daire
            g2.setColor(circleColor);
            g2.fillOval(cx - r, cy - r, r * 2, r * 2);
 
            // Daire içi yazı
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, cx - fm.stringWidth(label) / 2, cy + fm.getAscent() / 2 - 1);
 
            // Adım adı
            g2.setFont(i == currentStep
                    ? new Font("SansSerif", Font.BOLD,  11)
                    : new Font("SansSerif", Font.PLAIN, 11));
            g2.setColor(i < currentStep ? COL_DONE : (i == currentStep ? COL_ACTIVE : COL_PENDING));
            FontMetrics fm2 = g2.getFontMetrics();
            String name = STEP_NAMES[i];
            g2.drawString(name, cx - fm2.stringWidth(name) / 2, cy + r + 15);
        }
 
        g2.dispose();
    }
}
 