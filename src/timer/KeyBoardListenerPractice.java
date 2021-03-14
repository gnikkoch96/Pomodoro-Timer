package timer;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class KeyBoardListenerPractice extends JFrame implements KeyListener {
	private static boolean isKey = false;
	
	public KeyBoardListenerPractice() {
		super("Title");
		setPreferredSize(new Dimension(300, 500));
		pack();
		
		this.addKeyListener(this);
		
		JTextArea ta = new JTextArea();
		//ta.addKeyListener(this);
		add(ta);
		ta.addKeyListener(this);
    
		setVisible(true);

		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		isKey = true;
		System.out.println(isKey);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("released");
		System.out.println(e.isConsumed());
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("typed");
	}

	public static void executeFrame() {
		KeyBoardListenerPractice obj = new KeyBoardListenerPractice();
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {		//To make Swing Objects More Thread Safe (Helps make our timing more accurate)
			public void run() {
				executeFrame();
			}
		});
		
	}

}
