package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFrame.*;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JB_frame_özellikler {
	
	

	public static void main(String[] args) {
		JFrame frame = new JFrame("JFrame Ozellikleri");
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(230,200, 140 ));
		
		JLabel label = new JLabel(".setLayout(null); örnek!");
		label.setFont(new Font("Helvetica",Font.BOLD,18));
		label.setForeground(new Color(50, 230, 80));
		label.setOpaque(true);
		label.setBackground(new Color(45,240,240));
		label.setBounds(50,50,400,30);
		
		JTextField tf = new JTextField("textField alani... Yazi yaz...");
		tf.setFont(new Font("Helvetica", Font.BOLD,18));
		tf.setEditable(false);
		tf.setBounds(50, 130, 400, 30);
		
		JButton buton = new JButton("Exit");
		buton.setBounds(50,210,70,30);
		
		buton.addActionListener(new ActionListener( ) {
			puplic void actionPerformed(ActionEvent event  )
			{ System.exit(0);}
		});
		
		frame.add(label);
		frame.add(tf);
		frame.add(buton);
		
		frame.setSize(1024,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
