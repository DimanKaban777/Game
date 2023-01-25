import javax.swing.*;
import javax.swing.text.GapContent;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow()  {
        setTitle("Snake Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(340,360);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
    }
}
