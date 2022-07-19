import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SoundThread extends Thread {
    Thread soundThread = new Thread(this);
    Sounds sounds = new Sounds();

    @Override
    public void run() {
        if (Player.isHit()) {
            sounds.soundHit();
            try {
                Thread.sleep(500);
            } catch (InterruptedException en) {
                en.printStackTrace();
            }
            sounds.soundPain();
        }
    }
}