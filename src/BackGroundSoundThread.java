
public class BackGroundSoundThread implements Runnable{

    Thread soundThread = new Thread(this);

    @Override
    public void run() {
            new BackGroundSound();
    }
}

