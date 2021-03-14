package timer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {
	private static Clip clip;
	private static AudioInputStream audioIS;
	
	//The sound that you want to play
	static String filePath = "src/Chime.wav";
	
	public PlaySound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		//Creating AudioInputStream Object
		audioIS = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		
		//Create Clip Reference
		clip = AudioSystem.getClip();
		
		//Open AudioInputStream to the Clip
		clip.open(audioIS);
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);  					//Loops the sounds continuously\
		
	}
	
//	public PlaySound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//		URL defaultSound = getClass().getResource("/src/Chime.wav");
//		
//		//Creating AudioInputStream Object
//		audioIS = AudioSystem.getAudioInputStream(defaultSound);
//		
//		//Create Clip Reference
//		clip = AudioSystem.getClip();
//		
//		//Open AudioInputStream to the Clip
//		clip.open(audioIS);
//		
//		clip.loop(Clip.LOOP_CONTINUOUSLY);  					//Loops the sounds continuously\
//		
//		//Inputting Sound for the Runnable Jar File
//		
//		
//	}
	
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		//Start the Clip
		clip.start();
	}
	
	public void finish() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clip.stop();
		clip.close();
	}
}
