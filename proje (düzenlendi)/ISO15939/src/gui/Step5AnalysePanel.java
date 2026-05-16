package gui;

import model.AppState;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Step 5 — Analyse: weighted averages, radar chart, and gap analysis.
 */
public class Step5AnalysePanel extends JPanel {

    private final AppState  state;
    private final MainFrame mainFrame;

    // Dynamic content areas
    private JPanel  scoresPanel;
    private JPanel  radarHolder;
    private JPanel  gapPanel;

    public Step5AnalysePanel(AppState state, MainFrame mainFrame) {
        this.state     = state;
        this.mainFrame = mainFrame;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Title ---
        JLabel title = new JLabel("Step 5: Analyse Results", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 100, 200));
        title.setBorder(new EmptyBorder(20, 0, 8, 0));
        add(title, BorderLayout.NORTH);

        // --- Main scrollable area ---
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.WHITE);
        main.setBorder(new EmptyBorder(0, 30, 0, 30));

        // 5a — Dimension scores
        scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setBackground(Color.WHITE);
        scoresPanel.setBorder(titledBorder("5a. Dimension-Based Weighted Averages"));
        scoresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(scoresPanel);
        main.add(Box.createVerticalStrut(16));

        // 5b — Radar chart
        radarHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        radarHolder.setBackground(Color.WHITE);
        radarHolder.setBorder(titledBorder("5b. Radar Chart (Bonus)"));
        radarHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(radarHolder);
        main.add(Box.createVerticalStrut(16));

        // 5c — Gap analysis
        gapPanel = new JPanel();
        gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.Y_AXIS));
        gapPanel.setBackground(Color.WHITE);
        gapPanel.setBorder(titledBorder("5c. Gap Analysis"));
        gapPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(gapPanel);
        main.add(Box.createVerticalStrut(20));

        JScrollPane sp = new JScrollPane(main);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        add(sp, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        btnPanel.setBackground(Color.WHITE);

        JButton backBtn = new JButton("◀ Back");
        styleButton(backBtn, new Color(120, 120, 130));
        backBtn.addActionListener(e -> mainFrame.goToStep(3));

        JButton restartBtn = new JButton("🔄 Restart");
        styleButton(restartBtn, new Color(40, 160, 80));
        restartBtn.addActionListener(e -> mainFrame.restart());

        btnPanel.add(backBtn);
        btnPanel.add(restartBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    /** Called each time this panel is shown. */
    public void refresh() {
        Scenario scenario = state.getScenario();
        if (scenario == null) return;

        List<model.Dimension> dimensions = scenario.getDimensions();

        // ---- 5a: Progress bars ----
        scoresPanel.removeAll();
        scoresPanel.add(Box.createVerticalStrut(8));

        for (model.Dimension dim : dimensions) {
            double score = dim.calculateScore();
            int pct      = (int) Math.round(score / 5.0 * 100);

            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBackground(Color.WHITE);
            row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));

            JLabel nameLbl = new JLabel(
                    String.format("%-30s %.2f / 5.0", dim.getName(), score));
            nameLbl.setFont(new Font("Monospaced", Font.BOLD, 13));
            nameLbl.setPreferredSize(new java.awt.Dimension(260, 28));

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(pct);
            bar.setStringPainted(false);
            bar.setForeground(scoreColor(score));
            bar.setBackground(new Color(230, 232, 240));
            bar.setPreferredSize(new java.awt.Dimension(300, 22));
            bar.setBorderPainted(false);

            row.add(nameLbl, BorderLayout.WEST);
            row.add(bar,     BorderLayout.CENTER);
            row.setBorder(new EmptyBorder(3, 10, 3, 10));
            scoresPanel.add(row);
        }
        scoresPanel.revalidate();
        scoresPanel.repaint();

        // ---- 5b: Radar chart ----
        radarHolder.removeAll();
        radarHolder.add(new RadarChartPanel(dimensions));
        radarHolder.revalidate();
        radarHolder.repaint();

        // ---- 5c: Gap analysis ----
        gapPanel.removeAll();
        gapPanel.add(Box.createVerticalStrut(8));

        model.Dimension worst = dimensions.get(0);
        for (model.Dimension d : dimensions) {
            if (d.calculateScore() < worst.calculateScore()) {
                worst = d;
            }
        }

        double worstScore = worst.calculateScore();
        double gap        = 5.0 - worstScore;
        String level      = qualityLevel(worstScore);

        addGapRow(gapPanel, "Lowest Dimension:", worst.getName());
        addGapRow(gapPanel, "Score:",            String.format("%.2f / 5.0", worstScore));
        addGapRow(gapPanel, "Gap (5.0 − score):",String.format("%.2f", gap));
        addGapRow(gapPanel, "Quality Level:",    level);

        JLabel advice = new JLabel(
                "<html><i>This dimension has the lowest score and requires the most improvement.</i></html>");
        advice.setFont(new Font("SansSerif", Font.ITALIC, 13));
        advice.setForeground(new Color(160, 60, 40));
        advice.setBorder(new EmptyBorder(8, 14, 6, 14));
        gapPanel.add(advice);

        gapPanel.revalidate();
        gapPanel.repaint();
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private void addGapRow(JPanel parent, String key, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 28));

        JLabel k = new JLabel(key);
        k.setFont(new Font("SansSerif", Font.BOLD, 13));
        k.setPreferredSize(new java.awt.Dimension(200, 24));

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.PLAIN, 13));

        row.add(k);
        row.add(v);
        parent.add(row);
    }

    private Color scoreColor(double score) {
        if (score >= 4.5) return new Color(34, 139, 34);   // green
        if (score >= 3.5) return new Color(70, 130, 180);  // blue
        if (score >= 2.5) return new Color(210, 160, 30);  // amber
        return new Color(180, 50, 40);                     // red
    }

    private String qualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor";
    }

    private TitledBorder titledBorder(String text) {
        TitledBorder b = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 210)), text);
        b.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        b.setTitleColor(new Color(30, 100, 200));
        return b;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(130, 38));
    }
}
