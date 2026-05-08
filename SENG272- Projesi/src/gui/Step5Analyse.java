package gui;
 
import model.AppState;
import model.Dimension;
import model.Scenario;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.List;
 
/**
 * Step 5: Analyse
 * (5a) Boyut ağırlıklı ortalamaları — JProgressBar
 * (5b) Radar chart — Graphics2D ile çizilir (Bonus)
 * (5c) Gap analizi — en düşük skor, gap değeri, kalite etiketi
 */
public class Step5Analyse extends JPanel {
 
    private MainFrame mainFrame;
    private AppState  appState;
    private JPanel    contentPanel;
 
    public Step5Analyse(MainFrame mainFrame, AppState appState) {
        this.mainFrame = mainFrame;
        this.appState  = appState;
        initUI();
    }
 
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
 
        add(Step1Profile.buildHeader("Step 5: Analyse",
                "Weighted dimension scores, radar chart and gap analysis."),
                BorderLayout.NORTH);
 
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(14, 24, 24, 24));
 
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
 
        JPanel btnRow = Step1Profile.buildButtonRow();
        JButton back = Step1Profile.secondaryBtn("\u2190 Back");
        back.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP4, 3));
        JButton restart = Step1Profile.primaryBtn("\u21BA Restart");
        restart.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP1, 0));
        btnRow.add(back);
        btnRow.add(restart);
        add(btnRow, BorderLayout.SOUTH);
    }
 
    /** MainFrame tarafından bu adım görünür olduğunda çağrılır. */
    public void refresh() {
        Scenario sc = appState.getSelectedScenario();
        if (sc == null) return;
 
        contentPanel.removeAll();
 
        List<Dimension> dims = sc.getDimensions();
        double[] scores = new double[dims.size()];
        for (int i = 0; i < dims.size(); i++) {
            scores[i] = dims.get(i).calculateDimensionScore();
        }
 
        // ── 5a. Ağırlıklı Ortalamalar ─────────────────────────────────────────
        contentPanel.add(sectionTitle("5a. Dimension-Based Weighted Averages"));
        contentPanel.add(Box.createVerticalStrut(8));
 
        for (int i = 0; i < dims.size(); i++) {
            Dimension dim = dims.get(i);
            double    sc2  = scores[i];
 
            JPanel row = new JPanel(new BorderLayout(12, 0));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
 
            JLabel lbl = new JLabel(dim.getName());
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setPreferredSize(new Dimension(210, 28));
            row.add(lbl, BorderLayout.WEST);
 
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue((int) Math.round(sc2 / 5.0 * 100));
            bar.setStringPainted(true);
            bar.setString(String.format("%.2f / 5.00", sc2));
            bar.setFont(new Font("SansSerif", Font.BOLD, 11));
            bar.setForeground(scoreColor(sc2));
            bar.setBackground(new Color(232, 238, 244));
            row.add(bar, BorderLayout.CENTER);
 
            contentPanel.add(row);
            contentPanel.add(Box.createVerticalStrut(6));
        }
        contentPanel.add(Box.createVerticalStrut(22));
 
        // ── 5b. Radar Chart (Bonus) ───────────────────────────────────────────
        contentPanel.add(sectionTitle("5b. Radar Chart (Bonus)"));
        contentPanel.add(Box.createVerticalStrut(8));
 
        RadarPanel radar = new RadarPanel(dims, scores);
        radar.setAlignmentX(Component.LEFT_ALIGNMENT);
        radar.setPreferredSize(new Dimension(560, 320));
        radar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        contentPanel.add(radar);
        contentPanel.add(Box.createVerticalStrut(22));
 
        // ── 5c. Gap Analizi ───────────────────────────────────────────────────
        contentPanel.add(sectionTitle("5c. Gap Analysis"));
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(buildGapPanel(dims, scores));
 
        contentPanel.revalidate();
        contentPanel.repaint();
    }
 
    // ── Gap paneli ────────────────────────────────────────────────────────────
    private JPanel buildGapPanel(List<Dimension> dims, double[] scores) {
        int worst = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] < scores[worst]) worst = i;
        }
        Dimension dim   = dims.get(worst);
        double    score = scores[worst];
        double    gap   = 5.0 - score;
        String    level = qualityLevel(score);
 
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(255, 248, 228));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 155, 55), 1),
                new EmptyBorder(14, 18, 14, 18)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
 
        addGapRow(p, "Dimension:",     dim.getName(),                           new Color(170, 55, 0));
        addGapRow(p, "Score:",         String.format("%.2f / 5.00", score),     Color.BLACK);
        addGapRow(p, "Gap:",           String.format("%.2f  (5.00 - %.2f)", gap, score), new Color(170, 55, 0));
        addGapRow(p, "Quality Level:", level,                                    levelColor(level));
 
        JLabel msg = new JLabel("\u26A0  This dimension has the lowest score and requires the most improvement.");
        msg.setFont(new Font("SansSerif", Font.BOLD, 12));
        msg.setForeground(new Color(140, 45, 0));
        msg.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.add(msg);
        return p;
    }
 
    private void addGapRow(JPanel p, String label, String value, Color valueColor) {
        JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 2));
        row.setBackground(new Color(255, 248, 228));
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.PLAIN, 13));
        v.setForeground(valueColor);
        row.add(l); row.add(v);
        p.add(row);
    }
 
    // ── Yardımcılar ───────────────────────────────────────────────────────────
    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(new Color(0, 88, 162));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
 
    private Color scoreColor(double s) {
        if (s >= 4.0) return new Color(0, 155, 0);
        if (s >= 3.0) return new Color(195, 125, 0);
        return new Color(195, 35, 35);
    }
 
    private String qualityLevel(double s) {
        if (s >= 4.5) return "Excellent";
        if (s >= 3.5) return "Good";
        if (s >= 2.5) return "Needs Improvement";
        return "Poor";
    }
 
    private Color levelColor(String l) {
        switch (l) {
            case "Excellent":         return new Color(0, 128, 0);
            case "Good":              return new Color(75, 128, 0);
            case "Needs Improvement": return new Color(175, 95, 0);
            default:                  return new Color(175, 30, 30);
        }
    }
 
    // =========================================================================
    // İç sınıf: Radar grafiği (Graphics2D ile çizilir — Bonus)
    // =========================================================================
    static class RadarPanel extends JPanel {
 
        private final List<Dimension> dims;
        private final double[]        scores;
 
        RadarPanel(List<Dimension> dims, double[] scores) {
            this.dims   = dims;
            this.scores = scores;
            setBackground(Color.WHITE);
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dims == null || dims.isEmpty()) return;
 
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 
            int w  = getWidth();
            int h  = getHeight();
            int cx = w / 2;
            int cy = h / 2;
            int r  = Math.min(w, h) / 2 - 58;
            int n  = dims.size();
 
            // Konsantrik halkalar (1-5 ölçeği)
            for (int ring = 1; ring <= 5; ring++) {
                int rr = (int) (r * ring / 5.0);
                g2.setColor(new Color(218, 228, 242));
                g2.setStroke(new BasicStroke(ring == 5 ? 1.4f : 0.7f));
                g2.drawOval(cx - rr, cy - rr, rr * 2, rr * 2);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                g2.setColor(new Color(155, 155, 155));
                g2.drawString(String.valueOf(ring), cx + 3, cy - rr + 4);
            }
 
            // Eksen çizgileri ve etiketler
            double[] axX = new double[n];
            double[] axY = new double[n];
            for (int i = 0; i < n; i++) {
                double angle = Math.toRadians(-90.0 + 360.0 / n * i);
                axX[i] = cx + r * Math.cos(angle);
                axY[i] = cy + r * Math.sin(angle);
                g2.setColor(new Color(185, 198, 215));
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawLine(cx, cy, (int) axX[i], (int) axY[i]);
 
                // Etiket
                String name = dims.get(i).getName();
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                g2.setColor(new Color(35, 75, 140));
                FontMetrics fm = g2.getFontMetrics();
                double ox = axX[i] - cx;
                double oy = axY[i] - cy;
                double len = Math.sqrt(ox * ox + oy * oy);
                int lx = (int) axX[i] + (int) (ox / len * 12);
                int ly = (int) axY[i] + (int) (oy / len * 12);
                if (lx < cx) lx -= fm.stringWidth(name);
                if (ly < cy) ly -= 3; else ly += fm.getAscent();
                g2.drawString(name, lx, ly);
            }
 
            // Veri poligonu
            Path2D.Double poly = new Path2D.Double();
            for (int i = 0; i < n; i++) {
                double angle = Math.toRadians(-90.0 + 360.0 / n * i);
                double frac  = scores[i] / 5.0;
                double px    = cx + r * frac * Math.cos(angle);
                double py    = cy + r * frac * Math.sin(angle);
                if (i == 0) poly.moveTo(px, py); else poly.lineTo(px, py);
            }
            poly.closePath();
 
            g2.setColor(new Color(0, 120, 215, 52));
            g2.fill(poly);
            g2.setColor(new Color(0, 95, 200));
            g2.setStroke(new BasicStroke(2f));
            g2.draw(poly);
 
            // Nokta ve skor etiketleri
            for (int i = 0; i < n; i++) {
                double angle = Math.toRadians(-90.0 + 360.0 / n * i);
                double frac  = scores[i] / 5.0;
                int px = (int) (cx + r * frac * Math.cos(angle));
                int py = (int) (cy + r * frac * Math.sin(angle));
                g2.setColor(new Color(0, 75, 180));
                g2.fillOval(px - 4, py - 4, 9, 9);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(px - 4, py - 4, 9, 9);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.setColor(new Color(15, 55, 135));
                g2.drawString(String.format("%.1f", scores[i]), px + 6, py - 3);
            }
 
            g2.dispose();
        }
    }
}