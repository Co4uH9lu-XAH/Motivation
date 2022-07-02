import javax.swing.*;
import java.awt.*;

public class Enemy {
    int x;
    int y;
    int dx;
    int dy;
    Image image = new ImageIcon("src/img/Enemy1.png").getImage();

    public Enemy (int x, int y, int dx, int dy){
        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;

    }
    public void move(){
        x+=dx;
        if (x<=10) x=10;
        if (x>=1120) x=1120;
        y-=dy;
        if (y<=0) y=0;
        if (y>=500) y=500;
    }
}
