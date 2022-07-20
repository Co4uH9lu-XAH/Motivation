import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// Панель, на которой буду рисовать игру. Помещаю на фрейм

public class GamePanel extends JPanel implements ActionListener {

    public final int WIDTH = 1200;    // Размеры панели - ширина
    public final int HEIGHT = 700;   // Размеры панели - высота
    Timer mainTimer = new Timer(25, this); // Главный таймер, по которому будет обновляться и перерисовываться панель

    Image backImage = new ImageIcon("src/img/GamePanel.png").getImage();
    Image menuBackImage = new ImageIcon("src/img/MenuPanel.png").getImage();
    Player player = new Player();
    EnemyCrafter enemyCrafter = new EnemyCrafter();
    BackGroundSoundThread backGroundSoundThread = new BackGroundSoundThread();
    SoundMenu soundMenu = new SoundMenu();




    public GamePanel() {             // При создании панели в конструкторе запускается счетчик, при каждом тиканье которого происходит всякое
        mainTimer.start();
        setFocusable(true);
        addKeyListener(new KeyActionListener());
        soundMenu.menuSoundStarter();
    }

    @Override
    public void paint(Graphics g) {
        if (!StaticValues.clickStartButton){
            // TODO нарисовать меню
            g.drawImage(menuBackImage, 0, 0, null);
        }else{
        // Отрисовка игровой панели
        g.drawImage(backImage, 0, 0, null);

        // Отрисовка скриптов человечка при нажатом и отпущенном пробеле
            if(StaticValues.hit) {
                player.image=player.imageLeft;
                g.drawImage(player.image, player.x, player.y, null);
            } else {
            player.image=player.imageNormal;
            g.drawImage(player.image, player.x, player.y, null);
        }
        // Отрисовка вражин
        for (int i = 0; i < StaticValues.enemies.size(); i++) {
            if (StaticValues.enemies.get(i).x < -10 || StaticValues.enemies.get(i).x > WIDTH ||
                    StaticValues.enemies.get(i).y < -10 || StaticValues.enemies.get(i).y > HEIGHT) {
                StaticValues.enemies.remove(StaticValues.enemies.get(i)); // Удаляю объект из коллекции, если он вышел
                                                                            // за пределы экрана
            } else {
                g.drawImage(StaticValues.enemies.get(i).image, StaticValues.enemies.get(i).x, // Если не вышел, рисуем
                        StaticValues.enemies.get(i).y, null);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // На каждое изменение счетчика вызывается методы,
        // и запускается заново метод paint(), который снова и снова все перерисовывает
        player.move();
        repaint();
        player.fight();
        for (int i = 0; i < StaticValues.enemies.size(); i++) {
            StaticValues.enemies.get(i).move();
        }
        collisionWithEnemy();
        collisionEnemy();
        // Остановка проигрывания фоновой музыки меню по событию
        if(StaticValues.clickStartButton){
            soundMenu.menuSoundStopper();
        }
    }

    private void collisionWithEnemy() { // Столкновение врагов с игроком
        Random random = new Random();
        for (int i = 0; i < StaticValues.enemies.size(); i++) {
            if (player.getRectangle().intersects(StaticValues.enemies.get(i).getRectangle())) {
                StaticValues.enemies.get(i).dx = random.nextInt(20) - 10;
                StaticValues.enemies.get(i).dy = random.nextInt(20) - 10;
                StaticValues.enemies.get(i).image = StaticValues.enemies.get(i).imageKnock;
            } else if (!player.getRectangle().intersects(StaticValues.enemies.get(i).getRectangle())) {
                StaticValues.enemies.get(i).image = StaticValues.enemies.get(i).imageNormal;
            }
        }
    }

    private void collisionEnemy() { // Столкновение врагов друг с другом
        Random random = new Random();
        for (int i = 0; i < StaticValues.enemies.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (StaticValues.enemies.get(j).getRectangle().intersects(StaticValues.enemies.get(i).getRectangle())) {
                    StaticValues.enemies.get(j).dx = random.nextInt(20) - 10;
                    StaticValues.enemies.get(i).dy = random.nextInt(20) - 10;
                    StaticValues.enemies.get(i).image = StaticValues.enemies.get(i).imageKnock;
                    StaticValues.enemies.get(j).image = StaticValues.enemies.get(j).imageKnock;
                }
            }
        }
    }

    private class KeyActionListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            SoundThread soundThread = new SoundThread();
            if (e.getKeyCode() == KeyEvent.VK_D) {
                player.dx = 5;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                player.dx = -5;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                player.dy = 5;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                player.dy = -5;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                StaticValues.hit = true;
                soundThread.soundThread.start();
            }
            // TODO подвесить методы входа в игру из меню на другое событие
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                StaticValues.clickStartButton=true;
                enemyCrafter.thread.start();
                backGroundSoundThread.backgroundSoundThread.start();
                System.out.println("sbf");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_D){
                player.dx=0;
            }
            if (e.getKeyCode()==KeyEvent.VK_A){
                player.dx=0;
            }
            if (e.getKeyCode()==KeyEvent.VK_W){
                player.dy=0;
            }
            if (e.getKeyCode()==KeyEvent.VK_S){
                player.dy=0;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                StaticValues.hit = false;
            }
        }
    }
}
