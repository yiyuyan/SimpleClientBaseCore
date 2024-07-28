package cn.ksmcbrigade.scbc.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    public static void write(File file,String context,boolean ex) throws IOException {
        if(!file.exists() || ex){
            Files.writeString(file.toPath(),context);
        }
    }

    public static String read(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
