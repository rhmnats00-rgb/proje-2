import javax.swing.JFrame;

public class JB_javaframeözellikleri {

	public static void main(String[] args) {
		JFrame
		frame = new JFrame("JFrame Ozellikleri");
		
		
		
		frame.setSize(1024,768);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame);.args 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
