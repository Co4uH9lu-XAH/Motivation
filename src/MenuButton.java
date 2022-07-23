import javax.swing.*;
import java.awt.*;

public class MenuButton {
    int x;
    int y;
    Image newGame = new ImageIcon("src/img/newgame.png").getImage();
    Image newGamePush = new ImageIcon("src/img/newgame.png").getImage();
    Image image = newGame;
    int imageHeight = image.getHeight(null);
    int imageWidth = image.getWidth(null);

    MenuButton(int x, int y){
        this.x=x;
        this.y=y;
    }



    public void pushButton(){
        image = newGamePush;
    }

    public Rectangle getRectangle(){
        return new Rectangle(x, y, imageWidth, imageHeight);
    }
}
