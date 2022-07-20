import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundMenu {

        File menuSound = new File("src/sounds/start.wav"); // TODO Поставить другую музыку
        AudioInputStream ais;
        Clip clip;

    SoundMenu() {
        {
            try {
                ais = AudioSystem.getAudioInputStream(menuSound);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void menuSoundStarter()  {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void menuSoundStopper(){
        clip.stop();
    }
}
