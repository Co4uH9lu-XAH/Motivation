import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// Панель, на которой буду рисовать игру. Помещаю на фрейм

public class GamePanel extends JPanel implements ActionListener {

    public static final int WIDTH = 1200;    // Размеры панели - ширина
    public static final int HEIGHT = 700;   // Размеры панели - высота
    Timer mainTimer = new Timer(30, this); // Главный таймер, по которому будет обновляться и перерисовываться панель

    Image backImage = new ImageIcon("src/img/Panel.png").getImage();
    Player player = new Player();
    EnemyCrafter enemyCrafter = new EnemyCrafter();



    public GamePanel(){             // При создании панели в конструкторе запускается счетчик, при каждом тиканье которого происходит всякое
        mainTimer.start();
        setFocusable(true);
        addKeyListener(new KeyActionListener());
        enemyCrafter.thread.start();

    }
    @Override
    public  void paint (Graphics g){
        g = (Graphics2D) g;
        g.drawImage(backImage, 0,0, null);
        g.drawImage(player.serg, player.x, player.y, null);

        for (int i = 0; i< EnemyCrafter.enemies.size(); i++ ){
            EnemyCrafter.enemies.get(i).move();
            g.drawImage(EnemyCrafter.enemies.get(i).image, EnemyCrafter.enemies.get(i).x, EnemyCrafter.enemies.get(i).y, null);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) { // На каждое изменение счетчика вызывается метод move() и перерисовывается метод paint()
        player.move();
        repaint();
        for (int i = 0; i< EnemyCrafter.enemies.size(); i++ ){
            EnemyCrafter.enemies.get(i).move();
        }
        collisionWithEnemy();
        }

    private void collisionWithEnemy() {
        Random random = new Random();
        for (int i=0; i<EnemyCrafter.enemies.size(); i++){
            if (player.getRectangle().intersects(EnemyCrafter.enemies.get(i).getRectangle())){
                EnemyCrafter.enemies.get(i).dx = random.nextInt(20)-10;
                EnemyCrafter.enemies.get(i).dy = random.nextInt(20)-10;
            }
    }

}
    private class KeyActionListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // TODO поставить пробел на пинок
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_D){
                player.dx=5;
            } else if (e.getKeyCode()==KeyEvent.VK_A){
                player.dx=-5;
            } else if (e.getKeyCode()==KeyEvent.VK_W){
                player.dy=5;
            } else if (e.getKeyCode()==KeyEvent.VK_S){
                player.dy=-5;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_D){
                player.dx=0;
            } else if (e.getKeyCode()==KeyEvent.VK_A){
                player.dx=0;
            } else if (e.getKeyCode()==KeyEvent.VK_W){
                player.dy=0;
            } else if (e.getKeyCode()==KeyEvent.VK_S){
                player.dy=0;
            }
        }
    }
}
