import java.util.ArrayList;
import java.util.Random;

public class EnemyCrafter implements Runnable{
    Thread thread = new Thread(this);
    static ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    public void run() {
        while (true){
            Random random = new Random();

            enemies.add(new Enemy((random.nextInt(800)+100), (random.nextInt(400)+100), (random.nextInt(20)-10),
                    (random.nextInt(20)-10)));
            try {
                Thread.sleep((random.nextInt(2000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
