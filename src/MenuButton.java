import javax.swing.*;
import java.awt.*;

public class MenuButton {
    int x;
    int y;
    Image newGame = new ImageIcon("src/img/newgame.png").getImage();
    Image newGamePush = new ImageIcon("src/img/newgamepush.png").getImage();
    Image image = newGame;
    int imageHeight = image.getHeight(null);
    int imageWidth = image.getWidth(null);
    boolean changeImageFlag;

    MenuButton(int x, int y){
        this.x=x;
        this.y=y;
    }



    public void pushButton(){
        if(changeImageFlag) {
            image = newGamePush;
        } else {
            image = newGame;
        }
    }

    public Rectangle getRectangle(){
        return new Rectangle(x, y, imageWidth, imageHeight);
    }
}
