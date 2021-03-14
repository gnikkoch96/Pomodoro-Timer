package timer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import PomodoroTimer.pomodoroGUI;

@SuppressWarnings("serial")
//Serial allows this class to be transported properly through the Internet
public class TimerGUI extends JFrame implements ActionListener, KeyListener{		

	//Variables for holding Hour, Min, and Sec
	private static int hour, min, sec, initialHour, initialMin, initialSec, sumProductivityMin, smallBreakMin, longBreakMin, productivityMin, counter;	
	private static boolean executeOnType, switchTime;										//Used to write to initial variables once
																							//switchTime = 0 (Productivity Timer) and 1 = Break Timer (Small and Long)
	private static JPanel timePanel, buttonPanel, counterPanel;					
	private static JFrame finishFrame;													//Frame that displays reset button and field explaining that the timer has been completed
	private static JTextField timeFieldHour, timeFieldMin, timeFieldSec, counterField;	
	private static JButton start, stop, pause, reset, pomodoroSettings, smallBreakButton, longBreakButton, productivityButton;
	private static Timer timer;															//One Timer is good enough
	private static PlaySound soundPlay;													//User Defined Class
	
	public TimerGUI(){																	//Default Constructor
		super("Nikko's Timer");															//Calls on JFrame(String) constructor
		setPreferredSize(new Dimension(1000,400));										//Width, Length
		setResizable(false);															//Can't resize the program
		setLayout(new BorderLayout());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);									//Stops timer from continuing even after closing
		setLocationRelativeTo(null);
		
		//Adding KeyListener to Frame
		this.addKeyListener(this);
		
		implementTimer();
		
		//Adding KeyListener to TextFields (Not sure if this the right way to do it)
		timeFieldHour.addKeyListener(this);
		timeFieldMin.addKeyListener(this);
		timeFieldSec.addKeyListener(this);
		
		
	}
	
	public TimerGUI(int productiveMin, int smallBreak, int longBreak) {					//Overloaded constructor for classes that are using this class (i.e. the PomodoroGUI)
		super("Nikko's Timer");															//Calls on JFrame(String) constructor
		setPreferredSize(new Dimension(1000,400));										//Width, Length
		setResizable(false);															//Can't resize the program
		setLayout(new BorderLayout());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);									//Stops timer from continuing even after closing
		setLocationRelativeTo(null);
		
		//Adding KeyListener to Frame
		this.addKeyListener(this);
		
		implementTimer();
		
		//Adding KeyListener to TextFields (Not sure if this the right way to do it)
		timeFieldHour.addKeyListener(this);
		timeFieldMin.addKeyListener(this);
		timeFieldSec.addKeyListener(this);
		
		//Logically, no one is going to have 1 hour productivity hours as that is not what the pomodoro timer does 
		min = productiveMin;
		productivityMin = productiveMin;
		smallBreakMin = smallBreak;
		longBreakMin = longBreak;
		
		hour = 0;
		sec = 0;
		
		activatePomodoro(min);
		setVisible(true); 
		
	}
	
	public void activatePomodoro(int min) {											//Starts the timer automatically when executed through the overloaded constructor
		//1. Set TextFieldMIn to min
		timeFieldMin.setText(String.valueOf(min)); 									//Stores the integer in string format and stores it in the field
		//2. Start Timer
		startTimer();
		
		start.setVisible(false);
		pause.setVisible(true);
		stop.setEnabled(true);
	
		
	}
	
	
	public void implementButtons() {													//Places Start, Stop, and Reset on the JPanel
		
		buttonPanel = new JPanel();

		start = new JButton("Start Timer");
		pause = new JButton("Pause Timer");
		stop = new JButton("Stop Timer");
		reset = new JButton("Reset Timer");												//Will be displayed in another window showing that the timer has been completed
		pomodoroSettings = new JButton("Settings");										//Show the Pomodoro Screen again if you want to edit the time for productivity min, small break,and long break
		smallBreakButton = new JButton("Start Small Break");							//Starst the small break
		longBreakButton = new JButton("Start Long Break");								//Starts long break
		productivityButton = new JButton("Start Productivity");							//Start Productivity Again
		
		//Setting Sizes for buttons
		start.setPreferredSize(new Dimension(100, 30));	
		pause.setPreferredSize(new Dimension(100, 30));
		stop.setPreferredSize(new Dimension(100, 30));
		reset.setPreferredSize(new Dimension(100,30));
		pomodoroSettings.setPreferredSize(new Dimension(100,30));
		smallBreakButton.setPreferredSize(new Dimension(150,30));
		longBreakButton.setPreferredSize(new Dimension(150,30));
		productivityButton.setPreferredSize(new Dimension(150,30));
		
		start.addActionListener(this);
		pause.addActionListener(this);
		stop.addActionListener(this);
		reset.addActionListener(this);
		pomodoroSettings.addActionListener(this);
		smallBreakButton.addActionListener(this);
		longBreakButton.addActionListener(this);
		productivityButton.addActionListener(this);
		
		buttonPanel.add(start);
		buttonPanel.add(pause);
		buttonPanel.add(stop);
		buttonPanel.add(pomodoroSettings);
		
		pause.setVisible(false);														//So it takes start's position once it becomes visible again
		stop.setEnabled(false);															//I don't want the user pressing stop when they haven't even started the timer yet
		
		
	}
	public void implementTimer() {														//Places the JPanel inside of the JFrame
		//I did this to make the JTextField look bigger
		Font f = new Font("serif", Font.PLAIN, 48);
		Font f2 = new Font("serif", Font.PLAIN, 24);
		//Creating the Objects
		timePanel = new JPanel();
		timeFieldHour = new JTextField(2);												//Holds Hour
		timeFieldMin = new JTextField(2);												//Holds Min
		timeFieldSec = new JTextField(2);												//Holds Sec

		//Counter field is to show how many productivity sessions you have done so far 
		JTextField counterLabel = new JTextField("Counter: ");
		counterPanel = new JPanel();
		counterField = new JTextField(String.valueOf(counter));
		counterLabel.setEditable(false);
		counterField.setEditable(false);
		
//		counterField.setText(String.valueOf(counter));
		counterPanel.add(counterLabel);
		counterPanel.add(counterField);
		
		counterLabel.setFont(f2);
		counterField.setFont(f2);
		
		timeFieldHour.setFont(f);
		timeFieldMin.setFont(f);
		timeFieldSec.setFont(f);
		
		//Makes input start from the right side of the field rather than on the left (Aesthetic Purposes)
		timeFieldHour.setHorizontalAlignment(SwingConstants.RIGHT);
		timeFieldMin.setHorizontalAlignment(SwingConstants.RIGHT);
		timeFieldSec.setHorizontalAlignment(SwingConstants.RIGHT);

		timePanel.add(timeFieldHour);
		timePanel.add(timeFieldMin);
		timePanel.add(timeFieldSec);
		
		//To make the JPanel stay in the center
		Box timePanelBox = new Box(BoxLayout.Y_AXIS);
		timePanelBox.add(Box.createVerticalGlue());
		timePanelBox.add(timePanel);
		timePanelBox.add(counterPanel);
		implementButtons();																//Gives JPanel buttonPanel an object to reference to
		timePanelBox.add(buttonPanel);													//Stores the buttonPanel alongside timePanel for aesthetics		
		
		add(timePanelBox, BorderLayout.CENTER);
		
	}
		
	public void displayFinish() {														//Shows another JFrame that explains the timer has been finished
		
		JPanel finishTextField = new JPanel();
//		JPanel finishResetButton = new JPanel();
		JPanel breakButton = new JPanel();												//Where the break buttons will appear	
																						//Good call on making this a local variable for this method, as it might be odd to keep adding the same button components over and over
																						//By making it local, it gets deleted by the time this method reaches its last line of code
		
		
		//Frame
		finishFrame = new JFrame("Timer Completed");	
		finishFrame.setPreferredSize(new Dimension(500,200));
		finishFrame.pack();
		finishFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finishFrame.setLayout(new FlowLayout());
		finishFrame.setVisible(true);
		
		//Setting up textField
		Font f = new Font("serif", Font.PLAIN, 36);
		
		JTextField finishText = new JTextField("Timer Finished");
		finishText.setHorizontalAlignment(JTextField.CENTER);
		finishText.setEditable(false);
		finishText.setFont(f);
		

		if(sumProductivityMin < 60) {												//Take a short break
			smallBreakButton.setHorizontalAlignment(JButton.CENTER);
			breakButton.add(smallBreakButton);
		}else {																		//Take a long break if you totaled over an hour (60 min)
			longBreakButton.setHorizontalAlignment(JButton.CENTER);
			breakButton.add(longBreakButton);
			sumProductivityMin = 0;													//Starts over
		}
		
		//Gives the option of doing productivity instead of taking a break
		productivityButton.setHorizontalAlignment(JButton.CENTER);
		breakButton.add(productivityButton);
	
		
		
		
		
		
		finishTextField.add(finishText);
		
		Box finishBox = new Box(BoxLayout.Y_AXIS);
		finishBox.add(Box.createVerticalGlue());
		finishBox.add(finishTextField);
		finishBox.add(breakButton);
		
		//Adding Components
		finishFrame.add(finishBox);
		
		finishFrame.repaint();
		finishFrame.revalidate();
		finishFrame.setLocationRelativeTo(null);									//Displays Frame in the center of the screen
	}
	public void startTimer() {
		//Setting Values for Static Variables Hour, Min, and Sec
		if(!timeFieldHour.getText().equals(""))										//Checks to see if the Field is empty
			hour = Integer.parseInt(timeFieldHour.getText());
		else {
			hour = 0;
			timeFieldHour.setText("00");

		}
		
		if(!timeFieldMin.getText().equals(""))
			min = Integer.parseInt(timeFieldMin.getText());
		else {
			min = 0;
			timeFieldMin.setText("00");

		}
		
		if(!timeFieldSec.getText().equals(""))
			sec = Integer.parseInt(timeFieldSec.getText());
		else {
			sec = 0;
			timeFieldSec.setText("00");

		}
		
		//After placing values for Hour, Min, and Sec, we need to make the textFields non-Editable
		//Only if they press stop will it allow the fields to be edited again
		timeFieldHour.setEditable(false);
		timeFieldMin.setEditable(false);
		timeFieldSec.setEditable(false);
		
		//Creating the Timer Object, so that it decreases the time
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				//Where the task is to be written
				
				//Our Task is to decrease the static sec, then min, and then hour (if applicable)
				if(sec != 0){
					sec--;
				}else{//sec = 0
					if(min == 0){ //Checks to see if min is also 0
						if(hour == 0){	//00:00:00
//							System.out.println("Finished");
							//We are going to be using the PlaySound class that I made by looking stuff online
							try {//This is going to play the sound from the PlaySound class
								soundPlay = new PlaySound();
								soundPlay.play();
							} catch (UnsupportedAudioFileException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (LineUnavailableException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(!switchTime) {						//Only increment the counter after a productivity session (not on breaks)
								counter++;
							}
							
							displayFinish();						//Checks which button to add (small break, long break, or productivity)
							sumProductivityMin += productivityMin;	//Every time the timer ends, add 15 minutes to the static int until it becomes > 60
							
							
							counterField.setText(String.valueOf(counter));
							timer.cancel();							//Cancels all Task that are currently running
						}
						else{//Hour > 0
							hour--;
							min = 59;
							sec = 59;
						}//hour
					}
					else{//min > 0
						min--;
						sec = 59;
					}//min
				}//sec
				
				//Update Timer in TextFields
				if(hour < 10)
					timeFieldHour.setText("0" + String.valueOf(hour));
				else
					timeFieldHour.setText(String.valueOf(hour));

				if(min < 10)
					timeFieldMin.setText("0" + String.valueOf(min));
				else
					timeFieldMin.setText(String.valueOf(min));
				
				if(sec < 10)
					timeFieldSec.setText("0" + String.valueOf(sec));
				else
					timeFieldSec.setText(String.valueOf(sec));
				
			}
		}, 0, 1010);									//Second goes by 1000 milliseconds, so it will execute run() every 1 sec
														//I will adjust the time slightly to be a little bit more accurate
		
	}
	
	public void pauseTimer() {
		//When we pause the Timer, save the current values of hold, min and sec
		//Then cancel all previous tasks for the timer
		timer.cancel();
		
	}
	
	public void stopTimer(int dummy) {														//A dummy value to use a overloaded method as I don't want to delete the other method below
		
		initialMin = productivityMin;
		//Overloaded version of stopTimer that replaces the need to change the time
		timer.cancel();
		
		//In pomodoro, user can't change the values of the timer
		timeFieldHour.setEditable(false);
		timeFieldMin.setEditable(false);
		timeFieldSec.setEditable(false);
		
		if(initialMin < 10)																
			timeFieldMin.setText("0" + String.valueOf(initialMin));
		else
			timeFieldMin.setText(String.valueOf(initialMin));
		
		timeFieldSec.setText("00");							

		counterField.setText("0");																		//Stopping a the Pomdoro timer will result in starting the counter all over
		
		
	}
	public void stopTimer() {
		//I want it so that it resets to the initial values that the user inputs to save the hassle of timing it over again
		//This should allow the textFields to be editable again just in case they want to put new values for hour, min, and sec
		
		timer.cancel(); 							//"Pauses" first then resets
		
		timeFieldHour.setEditable(true);
		
		timeFieldMin.setEditable(true);
		timeFieldSec.setEditable(true);
		
		//To reset back to original values, I made seperate variables. They must be written to only once per execution
		if(initialHour < 10)
			timeFieldHour.setText("0" + String.valueOf(initialHour));
		else
			timeFieldHour.setText(String.valueOf(initialHour));

		if(initialMin < 10)
			timeFieldMin.setText("0" + String.valueOf(initialMin));
		else
			timeFieldMin.setText(String.valueOf(initialMin));
		
		if(initialSec < 10)
			timeFieldSec.setText("0" + String.valueOf(initialSec));
		else
			timeFieldSec.setText(String.valueOf(initialSec));
		
//		System.out.println(initialHour + " : " + initialMin + " : " + initialSec);
		
		
	}
	
	public void resetTimer() {					//Stops the Sound, closes the window from finishDisplay(), and then uses the stoptimer()
		try {
			soundPlay.finish();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finishFrame.dispose();

		
		//stopTimer();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		//We only have 3 Actions (Start, Stop, and Reset Button)
		String command = a.getActionCommand();

		switch(command) {
			//Start Takes in the current values of Hour, Min, and Sec and Stores them in Static variables
			case "Start Timer":
				if(executeOnType)
				{
					if(!timeFieldHour.getText().equals(""))					//Checks to see if the Field is empty
						initialHour = Integer.parseInt(timeFieldHour.getText());
					else {
						initialHour = 0;
						timeFieldHour.setText("00");

					}
					
					if(!timeFieldMin.getText().equals(""))
						initialMin = Integer.parseInt(timeFieldMin.getText());
					else {
						initialMin = 0;
						timeFieldMin.setText("00");

					}
					
					if(!timeFieldSec.getText().equals(""))
						initialSec = Integer.parseInt(timeFieldSec.getText());
					else {
						initialSec = 0;
						timeFieldSec.setText("00");

					}
					
					executeOnType = false;
				}
				
				
				startTimer();
				start.setVisible(false);
				pause.setVisible(true);
				stop.setEnabled(true);
				break;

			
			//Pauses the Timer
			case "Pause Timer":
				pauseTimer();
				pause.setVisible(false);
				start.setVisible(true);
				break;
				
			//Resets the Timer to initial input
			case "Stop Timer":
				stopTimer(productivityMin);
				start.setVisible(true);
				pause.setVisible(false);
				break;
			
			case "Reset Timer":
				resetTimer();
				stop.setEnabled(false);
				break;
				
			case "Settings":
				this.dispose();
				timer.cancel();  							//Closes timer to prevent multiple tasks running concurrently (speeds up seconds)
				pomodoroGUI obj = new pomodoroGUI();
				
				//Retains previous inputs onto newly created pomodoroGUI object
				obj.setFields(productivityMin, smallBreakMin, longBreakMin);
				obj.setVisible(true);
				break;
				
			case "Start Small Break":
				activatePomodoro(smallBreakMin);
				resetTimer();								//Just stops the sound from continuing to play and sets visibilitiy of frame w/ timer finished 
				finishFrame.setVisible(false);
				break;
			
			case "Start Long Break":
				activatePomodoro(longBreakMin);
				resetTimer();	
				break;
			
			case "Start Productivity":
				
				activatePomodoro(productivityMin);						
				resetTimer();
				
				break;
		}
		
		repaint();							//Refreshes the JFrame along with its components
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {	//I only need on method to change the boolean to true, so the rest are empty for abstraction reasons
		// TODO Auto-generated method stub
		executeOnType = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void executeTimer() {
		TimerGUI t = new TimerGUI();
		t.setVisible(true);
	}
	
//	public static void main(String args[]) {
//		SwingUtilities.invokeLater(new Runnable() {		//To make Swing Objects More Thread Safe (Helps make our timing more accurate)
//			public void run() {
//				executeTimer();
//			}
//		});
//	}

	


	

	
}
