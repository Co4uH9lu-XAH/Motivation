import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {
    int x;
    int y;
    int dx;
    int dy;
    Image image = new ImageIcon("src/img/Enemy1.png").getImage();
    int imageHeight = image.getHeight(null);
    int imageWidth = image.getWidth(null);

    public Rectangle getRectangle (){
        return new Rectangle(x, y, imageWidth, imageHeight );
    }

    public Enemy (int x, int y, int dx, int dy){
        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;

    }
    public void move(){
        Random random = new Random();
        x+=dx;
        if (x<=0) {
            dx=random.nextInt(10);
        }
        if (x>=1100) {
            dx=random.nextInt(10)-10;
        }
        y-=dy;
        if (y<=0){
            dy=random.nextInt(10)-10;
        }
        if (y>=580) {
            dy=random.nextInt(10);
        }
    }
}
