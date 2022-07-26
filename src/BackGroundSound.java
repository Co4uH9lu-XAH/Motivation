import javax.sound.sampled.*;
import java.io.File;

// Фоновый звук непосредственно игры
public class BackGroundSound {

    BackGroundSound(){
    File backgroundSound = new File("src/sounds/Background.wav");
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(backgroundSound);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                FloatControl volumeLevel = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeLevel.setValue(-10.0f);
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Зацикливаю фоновый звук
                clip.start();
                ais.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

