package gui;

import model.AppState;
import model.Metric;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Step 3 — Plan: read-only view of dimensions and metrics for the selected scenario.
 */
public class Step3PlanPanel extends JPanel {

    private final AppState  state;
    private final MainFrame mainFrame;

    private JPanel contentPanel;

    public Step3PlanPanel(AppState state, MainFrame mainFrame) {
        this.state     = state;
        this.mainFrame = mainFrame;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Title ---
        JLabel title = new JLabel("Step 3: Plan Measurement", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 100, 200));
        title.setBorder(new EmptyBorder(20, 0, 8, 0));
        add(title, BorderLayout.NORTH);

        // --- Scrollable content area ---
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JScrollPane sp = new JScrollPane(contentPanel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        add(sp, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        btnPanel.setBackground(Color.WHITE);

        JButton backBtn = new JButton("◀ Back");
        styleButton(backBtn, new Color(120, 120, 130));
        backBtn.addActionListener(e -> mainFrame.goToStep(1));

        JButton nextBtn = new JButton("Next ▶");
        styleButton(nextBtn, new Color(30, 100, 200));
        nextBtn.addActionListener(e -> mainFrame.goToStep(3));

        btnPanel.add(backBtn);
        btnPanel.add(nextBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    /**
     * Called each time this panel becomes visible so it reflects the latest state.
     */
    public void refresh() {
        contentPanel.removeAll();

        Scenario scenario = state.getScenario();
        if (scenario == null) {
            contentPanel.add(new JLabel("No scenario selected."));
            return;
        }

        // Scenario header
        JLabel scenLbl = new JLabel("Scenario: " + scenario.getName());
        scenLbl.setFont(new Font("SansSerif", Font.ITALIC, 13));
        scenLbl.setForeground(new Color(100, 100, 120));
        scenLbl.setBorder(new EmptyBorder(6, 20, 10, 20));
        contentPanel.add(scenLbl);

        List<model.Dimension> dimensions = scenario.getDimensions();
        for (model.Dimension dim : dimensions) {
            // Dimension header label
            JLabel dimLbl = new JLabel(
                    dim.getName() + "  (Coefficient: " + dim.getCoefficient() + ")");
            dimLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
            dimLbl.setForeground(new Color(30, 100, 200));
            dimLbl.setBorder(new EmptyBorder(12, 20, 4, 20));
            dimLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(dimLbl);

            // Table for metrics
            String[] columns = {"Metric", "Coefficient", "Direction", "Range", "Unit"};
            DefaultTableModel model = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int r, int c) { return false; }
            };

            for (Metric m : dim.getMetrics()) {
                model.addRow(new Object[]{
                        m.getName(),
                        m.getCoefficient(),
                        m.getDirectionArrow(),
                        m.getRangeString(),
                        m.getUnit()
                });
            }

            JTable table = new JTable(model);
            styleTable(table);

            JScrollPane tsp = new JScrollPane(table);
            tsp.setPreferredSize(new java.awt.Dimension(700, table.getRowCount() * 28 + 28));
            tsp.setBorder(BorderFactory.createEmptyBorder(0, 20, 6, 20));
            tsp.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(tsp);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(26);
        table.setGridColor(new Color(210, 210, 220));
        table.setShowGrid(true);
        table.setIntercellSpacing(new java.awt.Dimension(1, 1));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBackground(new Color(230, 235, 250));
        header.setForeground(new Color(40, 40, 80));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(120, 38));
    }
}
