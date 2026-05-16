package gui;

import data.ScenarioRepository;
import model.AppState;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Step 2 — Define: Quality Type, Mode, and Scenario selection.
 */
public class Step2DefinePanel extends JPanel {

    private final AppState    state;
    private final MainFrame   mainFrame;

    // Quality Type (mutually exclusive → ButtonGroup)
    private JRadioButton rbProduct;
    private JRadioButton rbProcess;
    private ButtonGroup  typeGroup;

    // Mode (mutually exclusive → ButtonGroup)
    private JRadioButton rbHealth;
    private JRadioButton rbEducation;
    private ButtonGroup  modeGroup;

    // Scenario (single-selection list)
    private JList<String>       scenarioList;
    private DefaultListModel<String> scenarioModel;
    private List<Scenario>      currentScenarios;

    public Step2DefinePanel(AppState state, MainFrame mainFrame) {
        this.state     = state;
        this.mainFrame = mainFrame;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Title ---
        JLabel title = new JLabel("Step 2: Define Quality Dimensions", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 100, 200));
        title.setBorder(new EmptyBorder(25, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // --- Centre: three sections side-by-side ---
        JPanel centre = new JPanel(new GridLayout(1, 3, 20, 0));
        centre.setBackground(Color.WHITE);
        centre.setBorder(new EmptyBorder(10, 40, 10, 40));

        centre.add(buildTypePanel());
        centre.add(buildModePanel());
        centre.add(buildScenarioPanel());

        add(centre, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        btnPanel.setBackground(Color.WHITE);

        JButton backBtn = new JButton("◀ Back");
        styleButton(backBtn, new Color(120, 120, 130));
        backBtn.addActionListener(e -> mainFrame.goToStep(0));

        JButton nextBtn = new JButton("Next ▶");
        styleButton(nextBtn, new Color(30, 100, 200));
        nextBtn.addActionListener(e -> onNext());

        btnPanel.add(backBtn);
        btnPanel.add(nextBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // ---------------------------------------------------------------
    // Quality Type sub-panel
    // ---------------------------------------------------------------
    private JPanel buildTypePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(titledBorder("2a. Quality Type"));

        typeGroup  = new ButtonGroup();
        rbProduct  = new JRadioButton("Product Quality");
        rbProcess  = new JRadioButton("Process Quality");

        styleRadio(rbProduct);
        styleRadio(rbProcess);

        typeGroup.add(rbProduct);
        typeGroup.add(rbProcess);
        rbProduct.setSelected(true);

        p.add(Box.createVerticalStrut(10));
        p.add(rbProduct);
        p.add(Box.createVerticalStrut(5));

        JLabel prodDesc = hint("Software product characteristics:\nperformance, security, usability, reliability");
        p.add(prodDesc);

        p.add(Box.createVerticalStrut(15));
        p.add(rbProcess);
        p.add(Box.createVerticalStrut(5));

        JLabel procDesc = hint("Development process characteristics:\nsprint efficiency, code quality, team collaboration");
        p.add(procDesc);

        return p;
    }

    // ---------------------------------------------------------------
    // Mode sub-panel
    // ---------------------------------------------------------------
    private JPanel buildModePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(titledBorder("2b. Mode"));

        modeGroup   = new ButtonGroup();
        rbHealth    = new JRadioButton("Health");
        rbEducation = new JRadioButton("Education");

        styleRadio(rbHealth);
        styleRadio(rbEducation);

        modeGroup.add(rbHealth);
        modeGroup.add(rbEducation);
        rbHealth.setSelected(true);

        // When mode changes → reload scenario list
        rbHealth.addActionListener(e    -> refreshScenarios("Health"));
        rbEducation.addActionListener(e -> refreshScenarios("Education"));

        p.add(Box.createVerticalStrut(10));
        p.add(rbHealth);
        p.add(hint("Health management system scenarios"));

        p.add(Box.createVerticalStrut(15));
        p.add(rbEducation);
        p.add(hint("Education LMS system scenarios"));

        return p;
    }

    // ---------------------------------------------------------------
    // Scenario sub-panel
    // ---------------------------------------------------------------
    private JPanel buildScenarioPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(titledBorder("2c. Scenario"));

        scenarioModel = new DefaultListModel<>();
        scenarioList  = new JList<>(scenarioModel);
        scenarioList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scenarioList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        scenarioList.setBackground(new Color(248, 248, 252));
        scenarioList.setFixedCellHeight(36);

        // Load initial scenarios for Health mode
        refreshScenarios("Health");

        JScrollPane sp = new JScrollPane(scenarioList);
        sp.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200)));
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------
    private void refreshScenarios(String mode) {
        currentScenarios = ScenarioRepository.getScenarios(mode);
        scenarioModel.clear();
        for (Scenario s : currentScenarios) {
            scenarioModel.addElement(s.getName());
        }
        if (!currentScenarios.isEmpty()) {
            scenarioList.setSelectedIndex(0);
        }
    }

    private void onNext() {
        String qualityType = rbProduct.isSelected() ? "Product" : "Process";
        String mode        = rbHealth.isSelected()  ? "Health"  : "Education";

        int selectedIdx = scenarioList.getSelectedIndex();
        if (selectedIdx < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a scenario to continue.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        state.setQualityType(qualityType);
        state.setMode(mode);
        state.setScenario(currentScenarios.get(selectedIdx));

        mainFrame.goToStep(2);
    }

    private TitledBorder titledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)), title);
        b.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        b.setTitleColor(new Color(30, 100, 200));
        return b;
    }

    private void styleRadio(JRadioButton rb) {
        rb.setFont(new Font("SansSerif", Font.BOLD, 13));
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
    }

    private JLabel hint(String text) {
        JLabel lbl = new JLabel("<html><font color='#888888' size='3'>"
                + text.replace("\n", "<br>") + "</font></html>");
        lbl.setBorder(new EmptyBorder(0, 22, 0, 0));
        return lbl;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 38));
    }
}
