import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SoundThread extends Thread {
    Thread soundThread = new Thread(this);
    Sounds sounds = new Sounds();

    @Override
    public void run() {
            sounds.soundHit();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sounds.soundPain();
    }
}
