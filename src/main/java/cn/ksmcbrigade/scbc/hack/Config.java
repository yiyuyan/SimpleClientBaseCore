package cn.ksmcbrigade.scbc.hack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

    public static final File configDir = new File("simple/config/");

    public File file;
    public JsonObject data;

    public Config(File file, JsonObject def) throws IOException {
        this.file = file;
        this.data = def;
        this.save(false);
    }

    public void save(boolean t) throws IOException {
        File pathFile = new File(configDir,file.getPath()+".json");
        if(!pathFile.exists() || t){
            new File("simple").mkdirs();
            new File("simple/config").mkdirs();
            Files.writeString(pathFile.toPath(),data.toString());
        }
    }

    @Nullable
    public JsonElement get(String key){
        if(!data.has(key)){
            return null;
        }
        else{
            return data.get(key);
        }
    }

    public void setData(JsonObject newData) throws IOException{
        this.data = newData;
        this.save(true);
    }

    public void set(String key,JsonElement value) throws IOException{
        if(data.has(key)){
            data.add(key,value);
            save(true);
        }
    }
}
