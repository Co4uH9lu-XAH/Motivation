import javax.swing.*;
import java.awt.*;

public class Player {
    final int FINAL_RIGHT = 0;
    final int FINAL_LEFT = 1120;
    final int FINAL_TOP = 0;
    final int FINAL_FLOOR = 500;


    Image serg = new ImageIcon("src/img/Serg.png").getImage();
    int dickCount;
    int x=600;
    int y=400;
    int dx;
    int dy;


    public void move(){
        x+=dx;
        if (x<=FINAL_RIGHT) x=FINAL_RIGHT;
        if (x>=FINAL_LEFT) x=FINAL_LEFT;
        y-=dy;
        if (y<=FINAL_TOP) y=FINAL_TOP;
        if (y>=FINAL_FLOOR) y=FINAL_FLOOR;
    }
    public void fight(){

    }
}
