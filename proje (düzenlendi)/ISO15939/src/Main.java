import gui.MainFrame;

import javax.swing.*;

/**
 * Application entry point.
 * Launches the ISO 15939 Measurement Process Simulator on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        // Apply system look-and-feel for native widgets
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default if system L&F is unavailable
        }

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
