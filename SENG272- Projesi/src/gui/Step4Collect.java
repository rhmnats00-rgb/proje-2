package gui;
 
import model.AppState;
import model.Dimension;
import model.Metric;
import model.Scenario;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
 
/**
 * Step 4: Collect Data
 * Her metrik için ham değeri ve otomatik hesaplanan 1-5 skoru gösterir.
 * Veriler hard-coded olup skor formülü ISO 15939'a göre hesaplanır.
 */
public class Step4Collect extends JPanel {
 
    private MainFrame mainFrame;
    private AppState  appState;
 
    private JPanel collectContainer;
 
    public Step4Collect(MainFrame mainFrame, AppState appState) {
        this.mainFrame = mainFrame;
        this.appState  = appState;
        initUI();
    }
 
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
 
        add(Step1Profile.buildHeader("Step 4: Collect Data",
                "Raw values and calculated scores (1-5) for each metric."),
                BorderLayout.NORTH);
 
        collectContainer = new JPanel();
        collectContainer.setLayout(new BoxLayout(collectContainer, BoxLayout.Y_AXIS));
        collectContainer.setBackground(Color.WHITE);
        collectContainer.setBorder(new EmptyBorder(14, 22, 14, 22));
 
        JScrollPane scroll = new JScrollPane(collectContainer);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
 
        JPanel btnRow = Step1Profile.buildButtonRow();
        JButton back = Step1Profile.secondaryBtn("\u2190 Back");
        back.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP3, 2));
        JButton next = Step1Profile.primaryBtn("Analyse \u2192");
        next.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP5, 4));
        btnRow.add(back);
        btnRow.add(next);
        add(btnRow, BorderLayout.SOUTH);
    }
 
    /** MainFrame tarafından bu adım görünür olduğunda çağrılır. */
    public void refresh() {
        Scenario sc = appState.getSelectedScenario();
        if (sc == null) return;
 
        collectContainer.removeAll();
 
        // Formül açıklaması
        JLabel formula = new JLabel(
            "<html><i>Higher\u2191: score = 1 + (value\u2212min)/(max\u2212min) \u00d7 4"
            + "&nbsp;&nbsp;|&nbsp;&nbsp;"
            + "Lower\u2193: score = 5 \u2212 (value\u2212min)/(max\u2212min) \u00d7 4"
            + "&nbsp;&nbsp;(rounded to nearest 0.5)</i></html>");
        formula.setFont(new Font("SansSerif", Font.ITALIC, 11));
        formula.setForeground(new Color(100, 100, 100));
        formula.setAlignmentX(Component.LEFT_ALIGNMENT);
        collectContainer.add(formula);
        collectContainer.add(Box.createVerticalStrut(12));
 
        for (Dimension dim : sc.getDimensions()) {
            // Boyut başlığı
            JLabel header = new JLabel(dim.getName()
                    + "   (Coefficient: " + dim.getCoefficient() + ")");
            header.setFont(new Font("SansSerif", Font.BOLD, 14));
            header.setForeground(new Color(0, 90, 170));
            header.setBorder(new EmptyBorder(6, 0, 3, 0));
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            collectContainer.add(header);
 
            // Tablo
            String[] cols = { "Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit" };
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Metric m : dim.getMetrics()) {
                model.addRow(new Object[] {
                        m.getName(),
                        m.getDirectionLabel(),
                        m.getRangeLabel(),
                        fmtVal(m.getValue()),
                        m.calculateScore(),
                        m.getCoefficient() + " / " + m.getUnit()
                });
            }
 
            JTable table = buildTable(model);
            JScrollPane sp = new JScrollPane(table);
            sp.setBorder(BorderFactory.createLineBorder(new Color(208, 213, 220)));
            sp.setAlignmentX(Component.LEFT_ALIGNMENT);
            sp.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    table.getRowHeight() * (model.getRowCount() + 1) + 4));
            collectContainer.add(sp);
            collectContainer.add(Box.createVerticalStrut(16));
        }
 
        collectContainer.revalidate();
        collectContainer.repaint();
    }
 
    // ── Tablo ─────────────────────────────────────────────────────────────────
    private JTable buildTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(228, 238, 255));
        t.setGridColor(new Color(218, 222, 228));
        t.setSelectionBackground(new Color(198, 222, 255));
 
        // Ortala: sütun 1-5
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i <= 5; i++) t.getColumnModel().getColumn(i).setCellRenderer(center);
 
        // Yön sütunu
        t.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable tbl, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, v, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                String s = v == null ? "" : v.toString();
                setForeground(s.contains("Higher") ? new Color(0, 128, 0) : new Color(175, 55, 0));
                return this;
            }
        });
 
        // Skor sütunu renkli
        t.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable tbl, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, v, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(new Font("SansSerif", Font.BOLD, 12));
                try {
                    double s = Double.parseDouble(v.toString());
                    if      (s >= 4.5) setForeground(new Color(0, 140, 0));
                    else if (s >= 3.0) setForeground(new Color(175, 110, 0));
                    else               setForeground(new Color(175, 30, 30));
                } catch (Exception ex) { setForeground(Color.BLACK); }
                return this;
            }
        });
 
        return t;
    }
 
    private String fmtVal(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v)) return String.valueOf((long) v);
        return String.format("%.1f", v);
    }
}