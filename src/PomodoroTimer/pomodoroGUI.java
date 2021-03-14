package PomodoroTimer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import timer.TimerGUI;								//My class

public class pomodoroGUI extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int productivityMin, smallBreakMin, longBreakMin;
	private static JPanel tomatoImage, tomatoInputs, tomatoButton;
	private static JTextField productiveText, productivityTime, smallBreakText, smallBreakTime, longBreakText, longBreakTime;
	private static JButton startPomodoro;
	private static Border border;
	private static TimerGUI timer;						//Creates the Timer 
	
	//Default Constructor
	public pomodoroGUI() {
		super("Nikko's Tomato Timer");					//Calls on the Parent's Constructor
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(700,700));
		setResizable(false);
		pack();
		
		border = BorderFactory.createLoweredBevelBorder();
		
		//Implementing Features
		implementImage(border);
		implementInputs(border);
		implementButtons();
	}	
	
	private void implementImage(Border one) {					//Includes an Image to the Pomodoro Set-up screen.
		tomatoImage = new JPanel();
		
		ImageIcon icon = new ImageIcon("src/TomatoIcon.png");	//Change the image here
		
		JLabel label = new JLabel(icon);
		label.setBorder(one);
		tomatoImage.add(label);
		add(tomatoImage, BorderLayout.NORTH);
	}
	
	private void implementInputs(Border one) {					//Adds the Inputs to the JPanel tomatoInputs and then is added to the Frame
		tomatoInputs = new JPanel();
		
		//Using BoxLayout to organize the Fields in a vertical manner
		tomatoInputs.setLayout(new BoxLayout(tomatoInputs, BoxLayout.Y_AXIS));
		
		//Bundle the Text with their responding input fields
		JPanel productive, sBreak, lBreak;
		productive = new JPanel();
		sBreak = new JPanel();
		lBreak = new JPanel();
		
		
		//Setting up texts
		productiveText = new JTextField("(Min)Productive Time               : ");
		smallBreakText = new JTextField("(Min)Productive Break(Small): ");
		longBreakText = new JTextField ("(Min)Productive Break(Long) : ");
		
		productiveText.setEditable(false);
		smallBreakText.setEditable(false);
		longBreakText.setEditable(false);
		
		
		//Setting up input fields
		productivityTime = new JTextField(10);					//Length = 10 Characters/Letters
		smallBreakTime = new JTextField(10);
		longBreakTime = new JTextField(10);
		
		//Adding to JPanel
		//Productive Time
		productive.add(productiveText);
		productive.add(productivityTime);
		tomatoInputs.add(productive);
		
		//Small Break
		sBreak.add(smallBreakText);
		sBreak.add(smallBreakTime);
		tomatoInputs.add(sBreak);
		
		//Long Break
		lBreak.add(longBreakText);
		lBreak.add(longBreakTime);
		tomatoInputs.add(lBreak);
		tomatoInputs.setBorder(one);
		add(tomatoInputs, BorderLayout.CENTER);
		
	}
	
	public void setFields(int prod, int sBreak, int lBreak) {	//This method is used to publicly allow other classes to hold onto their previous inputs for the textfields
		this.productivityTime.setText(String.valueOf(prod));
		this.smallBreakTime.setText(String.valueOf(sBreak));
		this.longBreakTime.setText(String.valueOf(lBreak));
	}
	
	private void implementButtons() {							//Adds the button "Start" to execute the Timer with all the conditions
		tomatoButton = new JPanel();
		
		startPomodoro = new JButton("Start Pomodoro"); 
		startPomodoro.setPreferredSize(new Dimension(200,25));
		startPomodoro.addActionListener(this);
		
		tomatoButton.add(startPomodoro);
		add(tomatoButton, BorderLayout.SOUTH);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command) {
			case "Start Pomodoro":
				//If the values are null, set the default to 15, 2, and 25 respectively
				//Store the values into the static int variables
				if(productivityTime.getText().equals("")) {		//Default = 15 min
					productivityMin = 15;
				}else {
					productivityMin = Integer.parseInt(productivityTime.getText());
				}
				
				if(smallBreakTime.getText().equals("")) {		//Default = 2 min
					smallBreakMin = 2;
				}else {
					smallBreakMin = Integer.parseInt(smallBreakTime.getText());
				}
				
				if(longBreakTime.getText().equals("")) {
					longBreakMin = 25;
				}else {
					longBreakMin = Integer.parseInt(longBreakTime.getText());
				}
				
				timer = new TimerGUI(productivityMin, smallBreakMin, longBreakMin);								//Creates a TimerGUI object that will have the min = productivityMin
				this.dispose();
				
				
		} 
		
	}
	
	
	public static void executePomodoroTimer() {			//Creates a pomdoroGUI object to get the program started
	 pomodoroGUI pTimer = new pomodoroGUI();
	 pTimer.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {		//To make Swing Objects More Thread Safe (Helps make our timing more accurate)
			public void run() {
				executePomodoroTimer();
			}
		});
	}


}
