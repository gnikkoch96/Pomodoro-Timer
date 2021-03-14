package timer;
import java.util.Timer;
import java.util.TimerTask;

public class TimerPractice {
	public static int i = 0;
	
	public static void main(String[] args) {
		Timer timer = new Timer();							//Default Constructor
		timer.schedule(new TimerTask() {
			public void run() {								//This is the "Task"
				System.out.println("Timer Ran " + ++i + " times");
			}
		}, 0, 1000);
	}
}
