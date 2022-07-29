import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Панель, на которой буду рисовать игру. Помещаю на фрейм

public class GamePanel extends JPanel implements ActionListener {

    public final int WIDTH = 1200;    // Размеры панели - ширина
    public final int HEIGHT = 700;   // Размеры панели - высота
    Timer mainTimer = new Timer(40, this); // Главный таймер, по которому будет обновляться и перерисовываться панель
    int mouseX;
    int mouseY;
    int buttonX = 150;
    int buttonY = 200;

    boolean pauseFlag;
    boolean inStageButton;

    Image backImage = new ImageIcon("src/img/GamePanel.png").getImage();
    Image menuBackImage = new ImageIcon("src/img/MenuPanel.png").getImage();
    Player player = new Player();
    EnemyCrafter enemyCrafter = new EnemyCrafter();
    BackGroundSoundThread backGroundSoundThread = new BackGroundSoundThread();
    SoundMenu soundMenu = new SoundMenu();
    MenuButton newGameButton = new MenuButton(buttonX, buttonY);

    // При создании панели в конструкторе запускается счетчик, при каждом тиканье которого происходит всякое
    public GamePanel() {
        mainTimer.start();
        setFocusable(true);
        addKeyListener(new PanelListener());

        // Далее слушатели мыши. Пока методов не много, пусть будут здесь. Если разрастется, перенесу
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        soundMenu.menuSoundStarter();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1 && inStageButton)
                newGameButton.changeImageFlag = true;

            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                newGameButton.changeImageFlag = false;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if(!pauseFlag) {
                    StaticValues.clickStartButton = true;
                    enemyCrafter.thread.start();
                    backGroundSoundThread.backgroundSoundThread.start();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (!StaticValues.clickStartButton){
            // TODO нарисовать меню
            g.drawImage(menuBackImage, 0, 0, null);
            g.drawImage(newGameButton.image, newGameButton.x, newGameButton.y, null);

        }else if (StaticValues.clickStartButton){
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
    public void actionPerformed(ActionEvent e) {
        // На каждое изменение счетчика вызывается методы,
        // и запускается заново метод paint(), который снова и снова все перерисовывает,
        // а также методы, за которыми следим, которые делают изменения в игре
        player.isCollision();
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
        newGameButton.pushButton();

        // Отслеживание положения мышии и установка флага, если попал на кнопку "Новая игра"
        if (mouseX>newGameButton.x && mouseX<newGameButton.x+newGameButton.imageWidth
        && mouseY>newGameButton.y && mouseY<newGameButton.y+newGameButton.imageHeight){
            inStageButton=true;
        } else {
            inStageButton=false;
        }
    }

    void collisionWithEnemy() { // Столкновение врагов с игроком
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
    // Управление WASD, space - пинать, enter - вход в игру

    class PanelListener extends KeyAdapter {

        @Override
        public synchronized void keyPressed(KeyEvent e) {
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
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !pauseFlag){
                StaticValues.hit = true;
                if(player.isCollision) {
                    soundThread.start();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                newGameButton.changeImageFlag=true;
            } if (e.getKeyCode() == KeyEvent.VK_P && !pauseFlag){
                mainTimer.stop();
                pauseFlag=true;
            } else if (e.getKeyCode() == KeyEvent.VK_P && pauseFlag){
                mainTimer.start();
                pauseFlag=false;
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
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                newGameButton.changeImageFlag = false;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                StaticValues.clickStartButton = true;
                enemyCrafter.thread.start();
                backGroundSoundThread.backgroundSoundThread.run();
           }
        }
    }
}
