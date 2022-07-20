import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

// звуки при пинках
public class Sounds {

    File hit = new File("src/sounds/Hit.wav");
    File pain = new File("src/sounds/pain.wav");

    void soundHit(){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(hit);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl volumeLevel = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeLevel.setValue(5);
            clip.setFramePosition(0);
            clip.start();
            ais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void soundPain(){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(pain);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl volumeLevel = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeLevel.setValue(5);
            clip.setFramePosition(0);
            clip.start();
            ais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
