import javax.swing.*;

public class Frame extends JFrame{

    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(450, 150);
        this.setSize(1200, 700);
        this.setResizable(false);
        this.add(new GamePanel());
        this.setVisible(true);
        //this.pack();
    }
}
