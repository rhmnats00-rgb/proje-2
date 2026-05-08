package gui;
 
import model.AppState;
import model.UserProfile;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
 
/**
 * Step 1: Profile
 * Kullanıcı adı, okul ve oturum adını toplar.
 * Eksik alan varsa kullanıcıya açıklayıcı uyarı gösterir.
 */
public class Step1Profile extends JPanel {
 
    private MainFrame  mainFrame;
    private AppState   appState;
 
    private JTextField tfUsername;
    private JTextField tfSchool;
    private JTextField tfSession;
 
    public Step1Profile(MainFrame mainFrame, AppState appState) {
        this.mainFrame = mainFrame;
        this.appState  = appState;
        initUI();
    }
 
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
 
        // Başlık
        JPanel header = buildHeader("Step 1: Profile", "Enter your session information to begin.");
        add(header, BorderLayout.NORTH);
 
        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(50, 100, 50, 100));
 
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(12, 10, 12, 10);
        g.fill   = GridBagConstraints.HORIZONTAL;
 
        tfUsername = addRow(form, g, 0, "Username:");
        tfSchool   = addRow(form, g, 1, "School:");
        tfSession  = addRow(form, g, 2, "Session Name:");
 
        add(form, BorderLayout.CENTER);
 
        // Butonlar
        JPanel btnRow = buildButtonRow();
        JButton btnNext = primaryBtn("Next \u2192");
        btnNext.addActionListener(e -> onNext());
        btnRow.add(btnNext);
        add(btnRow, BorderLayout.SOUTH);
    }
 
    private void onNext() {
        String user    = tfUsername.getText().trim();
        String school  = tfSchool.getText().trim();
        String session = tfSession.getText().trim();
 
        if (user.isEmpty()) {
            warn("Please enter your username to continue.");
            tfUsername.requestFocus();
            return;
        }
        if (school.isEmpty()) {
            warn("Please enter your school name to continue.");
            tfSchool.requestFocus();
            return;
        }
        if (session.isEmpty()) {
            warn("Please enter a session name to continue.");
            tfSession.requestFocus();
            return;
        }
 
        appState.setProfile(new UserProfile(user, school, session));
        mainFrame.showStep(MainFrame.CARD_STEP2, 1);
    }
 
    // ── Yardımcı metodlar ─────────────────────────────────────────────────────
    private JTextField addRow(JPanel panel, GridBagConstraints g, int row, String lbl) {
        g.gridx = 0; g.gridy = row; g.weightx = 0.25;
        JLabel label = new JLabel(lbl);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(label, g);
 
        g.gridx = 1; g.weightx = 0.75;
        JTextField tf = new JTextField(28);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(tf, g);
        return tf;
    }
 
    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }
 
    static JPanel buildHeader(String title, String subtitle) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0, 120, 215));
        p.setBorder(new EmptyBorder(22, 30, 22, 30));
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 20));
        t.setForeground(Color.WHITE);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 13));
        s.setForeground(new Color(210, 230, 255));
        p.add(t);
        p.add(Box.createVerticalStrut(4));
        p.add(s);
        return p;
    }
 
    static JPanel buildButtonRow() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 14));
        p.setBackground(new Color(245, 247, 250));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(218, 218, 218)));
        return p;
    }
 
    static JButton primaryBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setBackground(new Color(0, 120, 215));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 26, 8, 26));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
 
    static JButton secondaryBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.PLAIN, 13));
        b.setBackground(new Color(225, 225, 225));
        b.setForeground(new Color(55, 55, 55));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 26, 8, 26));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}