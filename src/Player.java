import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    final int FINAL_RIGHT = 0;
    final int FINAL_LEFT = 1120;
    final int FINAL_TOP = 0;
    final int FINAL_FLOOR = 500;
    private static volatile boolean hit;

    public static synchronized boolean isHit() {
        return hit;
    }

    public static synchronized void setHit(boolean hit) {
        Player.hit = hit;
    }

    Image imageNormal = new ImageIcon("src/img/Serg.png").getImage();
    Image imageLeft = new ImageIcon("src/img/SergLeft.png").getImage();
    Image imageRight = new ImageIcon("src/img/SergRight.png").getImage();
    Image image = imageNormal;


    int dickCount;
    int x=600;
    int y=400;
    int dx;
    int dy;
    int imageHeight = image.getHeight(null);
    int imageWidth = image.getWidth(null);
    SoundThread soundsThread = new SoundThread();

    Player (){
        soundsThread.soundThread.start();
    }
    public Rectangle getRectangle (){
        return new Rectangle(x, y, imageWidth, imageHeight );
    }

    public void move(){
        x+=dx;
        if (x<=FINAL_RIGHT) x=FINAL_RIGHT;
        if (x>=FINAL_LEFT) x=FINAL_LEFT;
        y-=dy;
        if (y<=FINAL_TOP) y=FINAL_TOP;
        if (y>=FINAL_FLOOR) y=FINAL_FLOOR;
    }
    public void fight()  {

        for(int i= 0; i<EnemyCrafter.enemies.size(); i++){
            if(getRectangle().intersects(EnemyCrafter.enemies.get(i).getRectangle()) && hit){
                EnemyCrafter.enemies.remove(EnemyCrafter.enemies.get(i));
            }
        }
    }
}
