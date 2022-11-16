package tank2D;

import java.awt.Color;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame gameScreen = new JFrame();
        GamePlay gamePlay = new GamePlay();

        gameScreen.setBounds(10, 10, 800, 630);
        gameScreen.setTitle("Tanks 2D");
        gameScreen.setBackground(Color.gray);
        gameScreen.setResizable(false);
        gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameScreen.add(gamePlay);
        gameScreen.setVisible(true);


    }
}