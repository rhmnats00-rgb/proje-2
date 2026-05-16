package gui;

import model.AppState;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window.
 * Uses CardLayout to implement the 5-step wizard.
 */
public class MainFrame extends JFrame {

    private final AppState state;

    // Step indicator at the top
    private final StepIndicatorPanel stepIndicator;

    // Wizard cards
    private final CardLayout     cardLayout;
    private final JPanel         cardPanel;

    private final Step1ProfilePanel profile;
    private final Step2DefinePanel  define;
    private final Step3PlanPanel    plan;
    private final Step4CollectPanel collect;
    private final Step5AnalysePanel analyse;

    private static final String[] CARD_NAMES = {
            "PROFILE", "DEFINE", "PLAN", "COLLECT", "ANALYSE"
    };

    public MainFrame() {
        super("ISO 15939 Measurement Process Simulator");

        state = new AppState();

        // Build panels
        profile = new Step1ProfilePanel(state, this);
        define  = new Step2DefinePanel(state, this);
        plan    = new Step3PlanPanel(state, this);
        collect = new Step4CollectPanel(state, this);
        analyse = new Step5AnalysePanel(state, this);

        // Card panel
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
        cardPanel.add(profile, CARD_NAMES[0]);
        cardPanel.add(define,  CARD_NAMES[1]);
        cardPanel.add(plan,    CARD_NAMES[2]);
        cardPanel.add(collect, CARD_NAMES[3]);
        cardPanel.add(analyse, CARD_NAMES[4]);

        // Step indicator
        stepIndicator = new StepIndicatorPanel();

        // Root layout
        setLayout(new BorderLayout());
        add(stepIndicator, BorderLayout.NORTH);
        add(cardPanel,     BorderLayout.CENTER);

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 620);
        setMinimumSize(new Dimension(780, 540));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Navigate to a specific step (0-based).
     * Refreshes data-dependent panels before showing them.
     */
    public void goToStep(int step) {
        if (step == 2) plan.refresh();
        if (step == 3) collect.refresh();
        if (step == 4) analyse.refresh();

        cardLayout.show(cardPanel, CARD_NAMES[step]);
        stepIndicator.setCurrentStep(step);
    }

    /**
     * Reset to step 0 (Profile) — creates a fresh AppState equivalent
     * by re-starting the application flow without recreating the frame.
     */
    public void restart() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to restart and clear all data?",
                "Restart", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            state.setUsername(null);
            state.setSchool(null);
            state.setSessionName(null);
            state.setQualityType(null);
            state.setMode(null);
            state.setScenario(null);
            goToStep(0);
        }
    }
}
