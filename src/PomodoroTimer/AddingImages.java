package PomodoroTimer;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddingImages {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Image of a Tomato");
		frame.setPreferredSize(new Dimension(500,600));
		frame.pack();
		frame.setVisible(true);
		
		JPanel p = new JPanel();
		ImageIcon icon = new ImageIcon("src/Tomato.png");
		JLabel label = new JLabel(icon);
		p.add(label);
		frame.add(p);
		
		
		
		frame.revalidate();
	}
}
