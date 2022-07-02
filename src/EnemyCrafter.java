import java.util.ArrayList;
import java.util.Random;

public class EnemyCrafter implements Runnable{
    Thread thread = new Thread(this);
    static ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    public void run() {
        while (true){
            Random rand = new Random();

            enemies.add(new Enemy(rand.nextInt(1200),rand.nextInt(700), rand.nextInt(10), rand.nextInt(10)));
            try {
                Thread.sleep(rand.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
