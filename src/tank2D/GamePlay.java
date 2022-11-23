package tank2D;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;

public class GamePlay extends JPanel implements ActionListener{
    private Bricks brick;

    private ImageIcon player1;
    private int player1X = 200;
    private int player1Y = 550;
    private boolean player1Right = false;
    private boolean player1Left = false;
    private boolean player1Down = false;
    private boolean player1Up = true;
    private int player1Score = 0;
    private int player1Lives = 5;
    private boolean player1Shoot = false;
    private String bulletShootDir1 = "";

    private ImageIcon player2;
    private int player2X = 400;
    private int player2Y = 550;
    private boolean player2Right = false;
    private boolean player2Left = false;
    private boolean player2Down = false;
    private boolean player2Up = true;
    private int player2Score = 0;
    private int player2Lives = 5;
    private boolean player2Shoot = false;
    private String bulletShootDir2 = "";

    private Timer timer;
    private int delay=8;

    private Player1Listener player1Listener;
    private Player2Listener player2Listener;

    private Player1Bullet player1Bullet = null;
    private Player2Bullet player2Bullet = null;

    private boolean play = true;

    public GamePlay() {
        brick = new Bricks();
        player1Listener = new Player1Listener();
        player2Listener = new Player2Listener();
        setFocusable(true);
        // addKeyListener(this);
        addKeyListener(player1Listener);
        addKeyListener(player2Listener);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // play background
        g.setColor(Color.black);
        g.fillRect(0, 0, 650, 600);

        // right side background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(660, 0, 140, 600);

        // draw solid bricks
        brick.drawSolids(this, g);

        // draw breakable bricks
        brick.draw(this, g);

        if (play) {
            // draw player 1
            if(player1Up) player1 = new ImageIcon("player1_tank_up.png");
            else if(player1Down) player1 = new ImageIcon("player1_tank_down.png");
            else if(player1Left) player1 = new ImageIcon("player1_tank_left.png");
            else if(player1Right) player1 = new ImageIcon("player1_tank_right.png");

            player1.paintIcon(this, g, player1X, player1Y);

            // draw player 2
            if(player2Up) player2 = new ImageIcon("player2_tank_up.png");
            else if(player2Down) player2 = new ImageIcon("player2_tank_down.png");
            else if(player2Left) player2 = new ImageIcon("player2_tank_left.png");
            else if(player2Right) player2 = new ImageIcon("player2_tank_right.png");

            player2.paintIcon(this, g, player2X, player2Y);

            // draw the bullet movement of player 1
            if(player1Bullet != null && player1Shoot) {
                if (bulletShootDir1.equals("")) {
                    if(player1Up) bulletShootDir1 = "up";
                    else if(player1Down) bulletShootDir1 = "down";
                    else if(player1Right) bulletShootDir1 = "right";
                    else if(player1Left) bulletShootDir1 = "left";
                } else {
                    player1Bullet.move(bulletShootDir1);
                    player1Bullet.draw(g);
                }
                // check if the bullet of player 1 hit player 2
                if(new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10).intersects(new Rectangle(player2X, player2Y, 50, 50))) {
                    player1Score += 10;
                    player2Lives -= 1;
                    player1Bullet = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                }
                //  check if the bullet of player 1 hit solid bricks or breakable  bricks
                if(brick.checkCollision(player1Bullet.getX(), player1Bullet.getY()) || brick.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY())) {
                    player1Bullet = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                }
                // check if the bullet of player 1 hit the edges
                if(player1Bullet.getY()<1
                        || player1Bullet.getY() >580
                        || player1Bullet.getX() <1
                        || player1Bullet.getX() >630) {
                    player1Bullet = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                }
            }
            // draw the bullet movement of player 2
            if(player2Bullet != null && player2Shoot) {
                if(bulletShootDir2.equals("")) {
                    if(player2Up) bulletShootDir2 = "up";
                    else if(player2Down) bulletShootDir2 = "down";
                    else if(player2Right) bulletShootDir2 = "right";
                    else if(player2Left) bulletShootDir2 = "left";
                } else {
                    player2Bullet.move(bulletShootDir2);
                    player2Bullet.draw(g);
                }
                // check if player2's bullet hit player 1
                if(new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
                        .intersects(new Rectangle(player1X, player1Y, 50, 50))) {
                    player2Score += 10;
                    player1Lives -= 1;
                    player2Bullet = null;
                    player2Shoot = false;
                    bulletShootDir2 = "";
                }
                // check if player 2's bullet hit solid or breakable bricks
                if(brick.checkCollision(player2Bullet.getX(), player2Bullet.getY())
                        || brick.checkSolidCollision(player2Bullet.getX(), player2Bullet.getY())) {
                    player2Bullet = null;
                    player2Shoot = false;
                    bulletShootDir2 = "";
                }
                // check if player 2's bullet hit the edges
                if(player2Bullet.getY() < 1
                    || player2Bullet.getY() >580
                    || player2Bullet.getX() <1
                    || player2Bullet.getX() >630) {
                    player2Bullet = null;
                    player2Shoot = false;
                    bulletShootDir2 = "";
                }
            }
        }

        // the scores
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 17));
        g.drawString("Scores", 700, 30);
        g.drawString("Player 1 : "+player1Score, 670, 60);
        g.drawString("Player 2: "+player2Score, 670, 90);

        g.drawString("Lives", 700, 150);
        g.drawString("Player 1: "+player1Lives, 670, 180);
        g.drawString("Player 2: "+player2Lives, 670, 210);

        // stop the game if the hp of player 1 or 2 reach 0
        if (player1Lives == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            g.drawString("GAME OVER", 200, 300);
            g.drawString("Player 2 Won!", 180, 380);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("(tab SPACE to RESTART)", 230, 430);
            // g.setColor(Color.white);
            play = false;
        } else if (player2Lives == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            g.drawString("GAME OVER", 200, 300);
            g.drawString("Player 1 Won!", 180, 380);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("(tab SPACE to RESTART)", 230, 430);
            // g.setColor(Color.white);
            play = false;
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    // set the key listener for both players
    // player 1: W, A, S, D for moving, U for shooting
    // player 2: arrow keys for moving, M for shooting
    // Space to restart if a player's hp reach 0
    private class Player1Listener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE && (player1Lives==0 || player2Lives==0)) {
                brick = new Bricks();
                player1X = 200;
                player1Y = 550;
                player1Right = false;
                player1Left = false;
                player1Down = false;
                player1Up = true;

                player2X = 400;
                player2Y = 550;
                player2Right = false;
                player2Left = false;
                player2Down = false;
                player2Up = true;

                player1Score = 0;
                player1Lives = 5;
                player2Score = 0;
                player2Lives = 5;
                play = true;
                repaint();
            }
            if(e.getKeyCode()==KeyEvent.VK_U) {
                if(!player1Shoot) {
                    if(player1Up) player1Bullet = new Player1Bullet(player1X+20, player1Y);
                    else if (player1Down) player1Bullet = new Player1Bullet(player1X+20, player1Y+40);
                    else if (player1Right) player1Bullet = new Player1Bullet(player1X+40, player1Y+20);
                    else if (player1Left) player1Bullet = new Player1Bullet(player1X, player1Y+20);
                    player1Shoot = true;
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_W) {
                player1Right = false;
                player1Left = false;
                player1Down = false;
                player1Up = true;

                if(!(player1Y <10)) player1Y-=10;
            }
            if(e.getKeyCode()==KeyEvent.VK_A) {
                player1Right = false;
                player1Left = true;
                player1Up = false;
                player1Down = false;

                if(!(player1X<10)) player1X -= 10;
            }
            if(e.getKeyCode()==KeyEvent.VK_S) {
                player1Right = false;
                player1Left = false;
                player1Up = false;
                player1Down = true;

                if(!(player1Y >540)) player1Y +=10;
            }
            if(e.getKeyCode()==KeyEvent.VK_D) {
                player1Right = true;
                player1Left = false;
                player1Up = false;
                player1Down = false;

                if(!(player1X > 590)) player1X +=10;
            }
        }
    }

    private class Player2Listener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_M) {
                if(!player2Shoot) {
                    if(player2Up) player2Bullet = new Player2Bullet(player2X+20, player2Y);
                    else if(player2Down) player2Bullet = new Player2Bullet(player2X +20, player2Y +40);
                    else if(player2Right) player2Bullet = new Player2Bullet(player2X+40, player2Y +20);
                    else if(player2Left) player2Bullet = new Player2Bullet(player2X, player2Y+20);
                    player2Shoot = true;
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_UP) {
                player2Right = false;
                player2Left = false;
                player2Up = true;
                player2Down = false;

                if(!(player2Y < 10)) player2Y -=10;
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                player2Right = false;
                player2Left = false;
                player2Up = false;
                player2Down = true;

                if(!(player2Y > 540)) player2Y +=10;
            }
            if(e.getKeyCode()==KeyEvent.VK_LEFT) {
                player2Right = false;
                player2Left = true;
                player2Up = false;
                player2Down = false;

                if(!(player2X <10)) player2X -= 10;
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
                player2Right = true;
                player2Left = false;
                player2Up = false;
                player2Down = false;

                if(!(player2X>590)) player2X+=10;
            }
        }
    }
}


