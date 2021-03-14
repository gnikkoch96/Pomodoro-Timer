package PomodoroTimer;

import java.net.URL;
import javax.swing.*;  // Wild carded for brevity. 
                       // Actual code imports single classes
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
//                URL url = Main.class.getResource(
//                                     "/resource/3DS.JPG");
//                ImageIcon icon = new ImageIcon(url);
                JFrame frame = new JFrame();
//                frame.add(new JLabel(icon));
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}