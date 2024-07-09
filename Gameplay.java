package JavaProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerx = 310;
    private int ballposx = 120;
    private int ballposy = 350;
    private int ballxdir = -2;
    private int ballydir = -4;
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(0, 0, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);  // Draw the score in the top right corner

        g.setColor(Color.green);
        g.fillRect(playerx, 550, 100, 8);

        g.setColor(Color.yellow);
        g.fillOval(ballposx, ballposy, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won: " + score, 260, 300);  // Display the score when the game is won

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if(ballposy > 570) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: " + score, 190, 300);  // Display the score when the game is over

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballposx, ballposy, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8))) {
                ballydir = -ballydir;
            }

            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickx = j * map.brickwidth + 80;
                        int bricky = i * map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
                        Rectangle ballRect = new Rectangle(ballposx, ballposy, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposx + 19 <= brickRect.x || ballposx + 1 >= brickRect.x + brickRect.width) {
                                ballxdir = -ballxdir;
                            } else {
                                ballydir = -ballydir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposx += ballxdir;
            ballposy += ballydir;
            if(ballposx < 0) {
                ballxdir = -ballxdir;
            }
            if(ballposy < 0) {
                ballydir = -ballydir;
            }
            if(ballposx > 670) {
                ballxdir = -ballxdir;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerx >= 600) {
                playerx = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerx < 10) {
                playerx = 10;
            } else {
                moveLeft();
            }
        }

        // Fixed assignment to comparison operator
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true; 
                ballposx = 120;
                ballposy = 350;
                ballxdir = -1;
                ballydir = -2;
                playerx = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerx += 20;
    }

    public void moveLeft() {
        play = true;
        playerx -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
