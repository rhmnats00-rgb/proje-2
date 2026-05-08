package gui;
 
import data.ScenarioData;
import model.AppState;
import model.Scenario;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
 
/**
 * Step 2: Define Quality Dimensions
 * Üç aşamalı seçim: Kalite Türü → Mod → Senaryo
 * Her seçim grubu ButtonGroup ile karşılıklı dışlamalı yapıdadır.
 */
public class Step2Define extends JPanel {
 
    private MainFrame mainFrame;
    private AppState  appState;
 
    private JRadioButton rbProduct;
    private JRadioButton rbProcess;
    private JRadioButton rbHealth;
    private JRadioButton rbEducation;
 
    private JPanel      scenarioRadioPanel;
    private ButtonGroup scenarioGroup;
 
    public Step2Define(MainFrame mainFrame, AppState appState) {
        this.mainFrame = mainFrame;
        this.appState  = appState;
        initUI();
    }
 
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
 
        add(Step1Profile.buildHeader("Step 2: Define Quality Dimensions",
                "Select quality type, mode and scenario."), BorderLayout.NORTH);
 
        // İçerik
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        center.setBorder(new EmptyBorder(18, 36, 18, 36));
 
        // 2a. Kalite türü
        JPanel typeSection = section("2a. Quality Type");
        ButtonGroup typeGroup = new ButtonGroup();
        rbProduct = radio("Product Quality  \u2014  Software product characteristics (performance, security, usability, reliability)");
        rbProcess = radio("Process Quality  \u2014  Development process characteristics (sprint efficiency, code quality, team collaboration)");
        rbProduct.setSelected(true);
        typeGroup.add(rbProduct);
        typeGroup.add(rbProcess);
        rbProduct.addActionListener(e -> refreshScenarios());
        rbProcess.addActionListener(e -> refreshScenarios());
        typeSection.add(rbProduct);
        typeSection.add(Box.createVerticalStrut(6));
        typeSection.add(rbProcess);
        center.add(typeSection);
        center.add(Box.createVerticalStrut(12));
 
        // 2b. Mod
        JPanel modeSection = section("2b. Mode");
        ButtonGroup modeGroup = new ButtonGroup();
        rbHealth    = radio("Health \u2014 Health management system scenarios (ready-made dataset)");
        rbEducation = radio("Education \u2014 Education LMS system scenarios (ready-made dataset)");
        rbHealth.setSelected(true);
        modeGroup.add(rbHealth);
        modeGroup.add(rbEducation);
        rbHealth.addActionListener(e -> refreshScenarios());
        rbEducation.addActionListener(e -> refreshScenarios());
        modeSection.add(rbHealth);
        modeSection.add(Box.createVerticalStrut(6));
        modeSection.add(rbEducation);
        center.add(modeSection);
        center.add(Box.createVerticalStrut(12));
 
        // 2c. Senaryo (dinamik)
        JPanel scenSection = section("2c. Scenario");
        scenarioRadioPanel = new JPanel();
        scenarioRadioPanel.setLayout(new BoxLayout(scenarioRadioPanel, BoxLayout.Y_AXIS));
        scenarioRadioPanel.setBackground(Color.WHITE);
        scenSection.add(scenarioRadioPanel);
        center.add(scenSection);
 
        JScrollPane scroll = new JScrollPane(center);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
 
        // Butonlar
        JPanel btnRow = Step1Profile.buildButtonRow();
        JButton btnBack = Step1Profile.secondaryBtn("\u2190 Back");
        btnBack.addActionListener(e -> mainFrame.showStep(MainFrame.CARD_STEP1, 0));
        JButton btnNext = Step1Profile.primaryBtn("Next \u2192");
        btnNext.addActionListener(e -> onNext());
        btnRow.add(btnBack);
        btnRow.add(btnNext);
        add(btnRow, BorderLayout.SOUTH);
 
        refreshScenarios();
    }
 
    /** Seçilen moda göre senaryo listesini yeniden oluşturur. */
    private void refreshScenarios() {
        scenarioRadioPanel.removeAll();
        scenarioGroup = new ButtonGroup();
 
        String mode = rbHealth.isSelected() ? "Health" : "Education";
        List<Scenario> list = ScenarioData.getByMode(mode);
 
        if (list.isEmpty()) {
            JLabel none = new JLabel("No scenarios found for this selection.");
            none.setFont(new Font("SansSerif", Font.ITALIC, 12));
            none.setForeground(Color.GRAY);
            scenarioRadioPanel.add(none);
        } else {
            boolean first = true;
            for (Scenario s : list) {
                JRadioButton rb = radio(s.getName() + "  [" + s.getQualityType() + " Quality]");
                rb.setActionCommand(s.getName());
                if (first) { rb.setSelected(true); first = false; }
                scenarioGroup.add(rb);
                scenarioRadioPanel.add(rb);
                scenarioRadioPanel.add(Box.createVerticalStrut(6));
            }
        }
        scenarioRadioPanel.revalidate();
        scenarioRadioPanel.repaint();
    }
 
    private void onNext() {
        appState.setQualityType(rbProduct.isSelected() ? "Product" : "Process");
        String mode = rbHealth.isSelected() ? "Health" : "Education";
        appState.setMode(mode);
 
        if (scenarioGroup == null || scenarioGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a scenario to continue.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        String name = scenarioGroup.getSelection().getActionCommand();
        Scenario found = null;
        for (Scenario s : ScenarioData.getByMode(mode)) {
            if (s.getName().equals(name)) { found = s; break; }
        }
        if (found == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a scenario to continue.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        appState.setSelectedScenario(found);
        mainFrame.showStep(MainFrame.CARD_STEP3, 2);
    }
 
    // ── Yardımcılar ───────────────────────────────────────────────────────────
    private JPanel section(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(195, 210, 225), 1),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12), new Color(0, 85, 165));
        p.setBorder(BorderFactory.createCompoundBorder(tb, new EmptyBorder(8, 10, 8, 10)));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return p;
    }
 
    private JRadioButton radio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        return rb;
    }
}