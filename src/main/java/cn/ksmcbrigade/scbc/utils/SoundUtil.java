package cn.ksmcbrigade.scbc.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class SoundUtil {
    public static void playSoundFromJar(String soundPath) {
        new Thread(() -> {
            try {
                ClassLoader classLoader = SoundUtil.class.getClassLoader();

                InputStream inputStream = classLoader.getResourceAsStream(soundPath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);

                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();


                while (clip.isRunning()) {
                    Thread.sleep(100);
                }

                clip.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
