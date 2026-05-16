package gui;

import model.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Step 1 — Profile: collects username, school, and session name.
 */
public class Step1ProfilePanel extends JPanel {

    private final AppState state;
    private final MainFrame mainFrame;

    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionField;

    public Step1ProfilePanel(AppState state, MainFrame mainFrame) {
        this.state     = state;
        this.mainFrame = mainFrame;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Title ---
        JLabel title = new JLabel("Step 1: Profile Information", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 100, 200));
        title.setBorder(new EmptyBorder(30, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // --- Form ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 100, 20, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(10, 10, 10, 10);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        usernameField = new JTextField(25);
        schoolField   = new JTextField(25);
        sessionField  = new JTextField(25);

        styleTextField(usernameField);
        styleTextField(schoolField);
        styleTextField(sessionField);

        addRow(form, gbc, 0, "Username:",     usernameField);
        addRow(form, gbc, 1, "School:",       schoolField);
        addRow(form, gbc, 2, "Session Name:", sessionField);

        add(form, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        btnPanel.setBackground(Color.WHITE);

        JButton nextBtn = new JButton("Next ▶");
        styleButton(nextBtn, new Color(30, 100, 200));
        nextBtn.addActionListener(e -> onNext());
        btnPanel.add(nextBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addRow(JPanel form, GridBagConstraints gbc, int row,
                        String labelText, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        form.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        form.add(field, gbc);
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
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

    private void onNext() {
        String username = usernameField.getText().trim();
        String school   = schoolField.getText().trim();
        String session  = sessionField.getText().trim();

        if (username.isEmpty()) {
            showWarning("Please enter your username to continue.");
            usernameField.requestFocus();
            return;
        }
        if (school.isEmpty()) {
            showWarning("Please enter your school name to continue.");
            schoolField.requestFocus();
            return;
        }
        if (session.isEmpty()) {
            showWarning("Please enter a session name to continue.");
            sessionField.requestFocus();
            return;
        }

        state.setUsername(username);
        state.setSchool(school);
        state.setSessionName(session);

        mainFrame.goToStep(1);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Missing Information",
                JOptionPane.WARNING_MESSAGE);
    }
}
