package gui;
 
import model.AppState;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
 
/**
 * Ana uygulama penceresi.
 * CardLayout ile 5 wizard adımı arasında geçiş yapar.
 */
public class MainFrame extends JFrame {
 
    public static final String CARD_STEP1 = "Step1";
    public static final String CARD_STEP2 = "Step2";
    public static final String CARD_STEP3 = "Step3";
    public static final String CARD_STEP4 = "Step4";
    public static final String CARD_STEP5 = "Step5";
 
    private CardLayout    cardLayout;
    private JPanel        cardPanel;
    private StepIndicator stepIndicator;
    private AppState      appState;
 
    private Step1Profile step1;
    private Step2Define  step2;
    private Step3Plan    step3;
    private Step4Collect step4;
    private Step5Analyse step5;
 
    public MainFrame() {
        appState = new AppState();
        initUI();
    }
 
    private void initUI() {
        setTitle("ISO 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 660);
        setMinimumSize(new Dimension(760, 560));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
 
        // Üst: adım göstergesi
        stepIndicator = new StepIndicator();
        add(stepIndicator, BorderLayout.NORTH);
 
        // Orta: kart paneli
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
 
        step1 = new Step1Profile(this, appState);
        step2 = new Step2Define(this, appState);
        step3 = new Step3Plan(this, appState);
        step4 = new Step4Collect(this, appState);
        step5 = new Step5Analyse(this, appState);
 
        cardPanel.add(step1, CARD_STEP1);
        cardPanel.add(step2, CARD_STEP2);
        cardPanel.add(step3, CARD_STEP3);
        cardPanel.add(step4, CARD_STEP4);
        cardPanel.add(step5, CARD_STEP5);
 
        add(cardPanel, BorderLayout.CENTER);
 
        showStep(CARD_STEP1, 0);
    }
 
    /**
     * Belirtilen adıma geçer ve step indicator'ı günceller.
     *
     * @param cardName  Gösterilecek kart adı
     * @param stepIndex 0 tabanlı adım numarası
     */
    public void showStep(String cardName, int stepIndex) {
        if (CARD_STEP3.equals(cardName)) step3.refresh();
        if (CARD_STEP4.equals(cardName)) step4.refresh();
        if (CARD_STEP5.equals(cardName)) step5.refresh();
        stepIndicator.setCurrentStep(stepIndex);
        cardLayout.show(cardPanel, cardName);
    }
}