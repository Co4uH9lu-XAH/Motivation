import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackGroundSound {

    BackGroundSound(){
    File backgroundSound = new File("src/sounds/Background.wav");
    AudioInputStream ais;

        {
            try {
                ais = AudioSystem.getAudioInputStream(backgroundSound);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Зацикливаю фоновый звук
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
