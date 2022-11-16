package tank2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Player2Bullet {
        private double x, y;

        public Player2Bullet(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void move(String face) {
            switch (face) {
                case "right" -> x += 5;
                case "left" -> x -= 5;
                case "up" -> y -= 5;
                default -> y += 5;
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.green);
            g.fillOval((int)x, (int)y, 10, 10);
        }

        public int getX() {return (int)x;}

        public int getY() {return (int)y;}

}
