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
 * Step 3: Plan Measurement
 * Seçilen senaryonun boyutlarını ve metriklerini salt-okunur tablo olarak gösterir.
 */
public class Step3Plan extends JPanel {
 
    private MainFrame mainFrame;
    private AppState  appState;
 
    private JLabel lblInfo;
    private JPanel tableContainer;
 
    public Step3Plan(MainFrame mainFrame, AppState appState) {
        this.mainFrame = mainFrame;
        this.appState  = appState;
        initUI();
    }
 
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
 
        // Başlık
        lblInfo = new JLabel(" ");
        lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblInfo.setForeground(new Color(210, 230, 255));
 
        JPanel header = Step1Profile.buildHeader("Step 3: Plan Measurement",
                "Review the metrics for the selected scenario (read-only).");
        add(header, BorderLayout.NORTH);
 
        // Tablo kapsayıcısı
        tableContainer = new JPanel();
        tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(14, 22, 14, 22));
 
        JScrollPane scroll = new JScrollPane(tableContainer);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
 
        // Butonlar
        JPanel btnRow = Step1Profile.buildButtonRow();
        JButton back = Step1Profile.secondaryBtn("\u2190 Back");
        back.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP2, 1));
        JButton next = Step1Profile.primaryBtn("Next \u2192");
        next.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP4, 3));
        btnRow.add(back);
        btnRow.add(next);
        add(btnRow, BorderLayout.SOUTH);
    }
 
    /** MainFrame tarafından bu adım görünür olduğunda çağrılır. */
    public void refresh() {
        Scenario sc = appState.getSelectedScenario();
        if (sc == null) return;
 
        tableContainer.removeAll();
 
        // Senaryo bilgisi
        JLabel scenLbl = new JLabel("Scenario: " + sc.getName()
                + "   |   Mode: " + sc.getMode()
                + "   |   Type: " + sc.getQualityType() + " Quality");
        scenLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        scenLbl.setForeground(new Color(0, 80, 160));
        scenLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.add(scenLbl);
        tableContainer.add(Box.createVerticalStrut(14));
 
        for (Dimension dim : sc.getDimensions()) {
            // Boyut başlığı
            JLabel header = new JLabel(dim.getName()
                    + "   (Coefficient: " + dim.getCoefficient() + ")");
            header.setFont(new Font("SansSerif", Font.BOLD, 14));
            header.setForeground(new Color(0, 90, 170));
            header.setBorder(new EmptyBorder(6, 0, 3, 0));
            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            tableContainer.add(header);
 
            // Tablo
            String[] cols = { "Metric", "Coefficient", "Direction", "Range", "Unit" };
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Metric m : dim.getMetrics()) {
                model.addRow(new Object[] {
                        m.getName(), m.getCoefficient(),
                        m.getDirectionLabel(), m.getRangeLabel(), m.getUnit()
                });
            }
 
            JTable table = buildTable(model);
            JScrollPane sp = tableScroll(table, model.getRowCount());
            sp.setAlignmentX(Component.LEFT_ALIGNMENT);
            tableContainer.add(sp);
            tableContainer.add(Box.createVerticalStrut(16));
        }
 
        tableContainer.revalidate();
        tableContainer.repaint();
    }
 
    // ── Tablo yardımcıları ────────────────────────────────────────────────────
    private JTable buildTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(228, 238, 255));
        t.setGridColor(new Color(218, 222, 228));
        t.setSelectionBackground(new Color(198, 222, 255));
 
        // Ortala: sütun 1-4
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i <= 4; i++) t.getColumnModel().getColumn(i).setCellRenderer(center);
 
        // Yön sütunu renkli
        t.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable tbl, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, v, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                String s = v == null ? "" : v.toString();
                setForeground(s.contains("Higher") ? new Color(0, 128, 0) : new Color(175, 55, 0));
                return this;
            }
        });
        return t;
    }
 
    private JScrollPane tableScroll(JTable t, int rows) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createLineBorder(new Color(208, 213, 220)));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, t.getRowHeight() * (rows + 1) + 4));
        return sp;
    }
}