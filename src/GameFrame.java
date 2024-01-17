import javax.swing.*;

public class GameFrame extends JFrame {
    // Constructor for GameFrame class.
    // Initializes a new GameFrame and adds a new instance of GamePanel to it.
    GameFrame(){
        this.add(new GamePannel());
        this.setTitle("JSnake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        /*
        Calling pack() on the JFrame causes it to resize itself based on the preferred sizes of its components,
        ensuring that everything is laid out and displayed properly
         */
        this.pack();
        //to see the window
        this.setVisible(true);
        //to center the window in the center
        this.setLocationRelativeTo(null);
    }
}
