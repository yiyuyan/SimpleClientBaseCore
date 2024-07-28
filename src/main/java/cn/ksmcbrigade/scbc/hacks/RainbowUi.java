package cn.ksmcbrigade.scbc.hacks;

import cn.ksmcbrigade.scbc.hack.Config;
import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hack.Type;
import cn.ksmcbrigade.scbc.manager.HackManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static cn.ksmcbrigade.scbc.client.SimpleClientBaseCoreClient.MC;

public class RainbowUi extends Hack {

    public static ArrayList<Color> colors;

    protected static int sleep = 300;

    public static int nowS = 300;
    public static int now = 0;

    public static boolean down = false;

    public RainbowUi() throws IOException, NoSuchFieldException, IllegalAccessException {
        super(Type.FUN, "RainbowUi");
        JsonObject data = new JsonObject();
        data.addProperty("sleep",15);
        setConfig(new Config(new File("RainbowUi"),data));
        colors = new ArrayList<>();
        Field[] fields = Color.class.getDeclaredFields();
        for(Field field:fields){
            if(Modifier.isStatic(field.getModifiers()) && field.getType().equals(Color.class)){
                colors.add(((Color)field.get(null)));
            }
        }
        now = colors.size()-1;
    }

    @Override
    public void enabled(MinecraftClient MC) throws Exception {
        JsonElement sl = getConfig().get("sleep");
        sleep = sl!=null?sl.getAsInt():15;
        nowS = sl!=null?sl.getAsInt():15;
        if(!HackManager.moduleList.enabled){
            HackManager.moduleList.setEnabled(true);
        }
    }

    public static JsonObject getColor(){
        JsonObject re = new JsonObject();
        try {
            if(now<0){
                down = false;
            }
            if(now==colors.size()){
                now--;
                nowS = sleep;
                down = true;
            }
            else{
                if(nowS!=0){
                    nowS--;
                }
                else{
                    if(down){
                        now--;
                    }
                    else{
                        now++;
                    }
                    nowS = sleep;
                }
            }
            re.addProperty("c",colors.get(now==-1?0:now==colors.size()?(colors.size()-1):now).getRGB());
            if(now==-1){
                now = 0;
                down = false;
            }
            if(now==colors.size()){
                now = colors.size()-1;
                down = true;
            }
            return re;
        }
        catch (Exception e){
            re.addProperty("c",Color.WHITE.getRGB());
            return re;
        }
    }

    public static void setColor(int SColor){
        now = SColor;
        nowS = sleep;
    }
}
