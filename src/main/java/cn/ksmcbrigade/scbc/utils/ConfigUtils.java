package cn.ksmcbrigade.scbc.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {

    public static final File uninstalls = new File("simple/uni.json");
    public static final File hides = new File("simple/hides.json");

    public static final File enables = new File("simple/enabledModules.json");
    public static final File keys = new File("simple/keys.json");

    public static final File config = new File("simple");
    public static final File moduleConfigs = new File("simple/config");

    public static ArrayList<String> enablesList = new ArrayList<>();
    public static Map<String,Integer> keysList = new HashMap<>();

    public static Map<String,Integer> launchKeysList = new HashMap<>();

    public static boolean init = false;

    public ConfigUtils() throws IOException {
        if(!init){
            config.mkdirs();
            moduleConfigs.mkdirs();

            FileUtils.write(enables,"[]",false);
            FileUtils.write(uninstalls,"[\"jarsauth\"]",false);
            FileUtils.write(hides,"[\"scbc\",\"wurst\",\"meteor-client\"]",false);
            FileUtils.write(keys,"{}",false);

            JsonParser.parseString(FileUtils.read(enables)).getAsJsonArray().forEach(f -> enablesList.add(f.getAsString()));

            JsonObject keysL = JsonParser.parseString(FileUtils.read(keys)).getAsJsonObject();
            keysL.keySet().forEach(f -> {
                keysList.put(f,keysL.get(f).getAsInt());
                launchKeysList.put(f,keysL.get(f).getAsInt());
            });

            init = true;
        }
    }

    public ArrayList<String> getArrays(String key) throws NoSuchFieldException, IllegalAccessException, IOException {
        File k = (File) this.getClass().getDeclaredField(key).get(null);
        ArrayList<String> list = new ArrayList<>();
        JsonParser.parseString(FileUtils.read(k)).getAsJsonArray().forEach(f -> list.add(f.getAsString()));
        return list;
    }

    public void savaArrays(String key) throws NoSuchFieldException, IllegalAccessException, IOException {
        Field kf = this.getClass().getDeclaredField(key);
        Field lf = this.getClass().getDeclaredField(key+"List");
        kf.setAccessible(true);
        lf.setAccessible(true);
        File k = (File) kf.get(null);
        ArrayList<String> c = (ArrayList<String>) lf.get(null);
        JsonArray array = new JsonArray();
        c.forEach(array::add);
        FileUtils.write(k,array.toString(),true);
    }

    public void savaObject(String key) throws NoSuchFieldException, IllegalAccessException, IOException {
        Field kf = this.getClass().getDeclaredField(key);
        Field lf = this.getClass().getDeclaredField(key+"List");
        kf.setAccessible(true);
        lf.setAccessible(true);
        File k = (File) kf.get(null);
        Map<String,Integer> c = (Map<String,Integer>) lf.get(null);
        JsonObject object = new JsonObject();
        c.keySet().forEach(m -> object.addProperty(m,c.get(m)));
        FileUtils.write(k,object.toString(),true);
    }

    public static void putKeys(String key,int code){
        if(!launchKeysList.containsKey(key)){
            keysList.put(key,code);
        }
    }

    public static void addOrRemoveAllEnabled(boolean add,String name){
        if(add){
            if(!enablesList.contains(name)){
                enablesList.add(name);
            }
        }
        else{
            while (enablesList.contains(name)){
                enablesList.remove(name);
            }
        }
    }
}
