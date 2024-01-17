import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePannel extends JPanel implements ActionListener {
    //Variables in SCREAMING_SNAKE_CASE
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 75;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePannel() {
        random = new Random();
        //Set size with variables
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MYKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            //To create a grid in order to see better what is going on, let's use a loop
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //creation of the snake body
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }//if i isn't == 0, we're dealing with the snake body
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            //To center text
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score" + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score" + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void move() {
        //Shifting coordinates in to arrays to give the movement impression
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        //use of switch to change directions
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }


    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        //iteration to see if the head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i] && (y[0] == y[i]))) {
                //if this is the case it means it collide with himself
                running = false;
            }
        }
        //iteration to see if the head collides left border
        if (x[0] < 0) {
            running = false;
        }
        //iteration to see if the head collides right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //iteration to see if the head collides top border
        if (y[0] < 0) {
            running = false;
        }
        //iteration to see if the head collides bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        //To center text
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score" + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score" + applesEaten)) / 2, g.getFont().getSize());

        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        //To center text
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }

    public class MYKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    //to avoid bumping into the body, i have to limitate the turn to 90'
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    //to avoid bumping into the body, i have to limitate the turn to 90'
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    //to avoid bumping into the body, i have to limitate the turn to 90'
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    //to avoid bumping into the body, i have to limitate the turn to 90'
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
