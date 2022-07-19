
public class BackGroundSoundThread implements Runnable{

    Thread backgroundSoundThread = new Thread(this);

    @Override
    public void run() {
            new BackGroundSound();
    }
}

