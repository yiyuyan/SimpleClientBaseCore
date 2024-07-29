package cn.ksmcbrigade.scbc.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundUtils {
    public static void playSoundFromJar(String soundPath) {
        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){System.setProperty("java.awt.headless","false");}
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void ThisToLocal(String THIS, String local) throws IOException {
        InputStream in = SoundUtils.class.getResourceAsStream("/"+THIS);
        if (in != null) {
            FileUtils.writeByteArrayToFile(new File(local), IOUtils.toByteArray(in));
        }
        else{
            throw new IOException("Can't get the file in this jar.");
        }
    }
}
