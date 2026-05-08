package main;
 
import gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
 
/**
 * ISO 15939 Measurement Process Simulator
 * Uygulama giriş noktası.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Varsayılan look and feel kullanılır
            }
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
