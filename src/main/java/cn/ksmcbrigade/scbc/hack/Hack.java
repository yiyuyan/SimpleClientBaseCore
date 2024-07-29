package cn.ksmcbrigade.scbc.hack;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import cn.ksmcbrigade.scbc.utils.ConfigUtils;
import cn.ksmcbrigade.scbc.utils.SoundUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class Hack {
    public final String name;
    public boolean enabled = false;
    public int key = -1;
    private Config config;

    public final Type type;

    public Hack(Type type, String name, boolean enabled, int key, @Nullable Config config, boolean nameConfig) throws IOException, NoSuchFieldException, IllegalAccessException {
        this.type = type;
        this.name = name;
        this.enabled = enabled;
        this.key = key;
        this.config = config;
        if(config!=null && nameConfig){
            this.config.file = new File(this.name);
        }
        ConfigUtils.putKeys(this.getEnName(),this.key);
    }

    public Hack(Type type, String name, boolean enabled, int key, Config config) throws IOException, NoSuchFieldException, IllegalAccessException {
        this(type,name,enabled,key,config,true);
    }

    public Hack(Type type, String name, boolean enabled, int key) throws IOException, NoSuchFieldException, IllegalAccessException {
        this(type,name,enabled,key,null,false);
    }

    public Hack(Type type, String name, boolean enabled) throws IOException, NoSuchFieldException, IllegalAccessException {
        this(type,name,enabled,-1,null,false);
    }

    public Hack(Type type, String name) throws IOException, NoSuchFieldException, IllegalAccessException {
        this(type,name,false,-1,null,false);
    }

    public void setEnabled(boolean enabled) throws Exception {
        final boolean change = !(this.enabled==enabled);
        this.enabled = enabled;
        MinecraftClient MC = MinecraftClient.getInstance();
        if(enabled){
            enabled(MC);
            ConfigUtils.addOrRemoveAllEnabled(true,this.getEnName());
            //ConfigUtils.enablesList.add(this.getEnName());
            SoundUtils.playSoundFromJar("simple/ding.wav");
            SimpleClientBaseCore.config.logger("play a wav.");
        }
        else{
            disabled(MC);
            ConfigUtils.addOrRemoveAllEnabled(false,this.getEnName());
            //ConfigUtils.enablesList.remove(this.getEnName());
        }
        //SimpleClientBaseCore.config.config.savaArrays("enables");
        if(change) HackManager.HackGui.reopen();
    }

    public String getName(){return I18n.translate(this.name);}

    public String getEnName(){return I18n.translate(this.name, Language.DEFAULT_LANGUAGE);}

    public int getKey() {return key;}

    public void setKey(int key) {this.key = key;}

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public int length(){
        return getName().length();
    }

    @Override
    public String toString() {
        return this.getName()+": "+(enabled?"ON":"OFF");
    }

    public void enabled(MinecraftClient MC) throws Exception{}

    public void render() throws Exception{}
    public void renderGame(DrawContext context,float ticks) throws Exception{}

    public void keyInput(int key, boolean screen) throws Exception{}

    public void clientTick(MinecraftClient MC) throws Exception{}
    public void worldTick(MinecraftClient MC,@Nullable World world) throws Exception{}
    public void playerTick(MinecraftClient MC,@Nullable PlayerEntity player) throws Exception{}
    public void screenTick(MinecraftClient MC) throws Exception{}

    public void disabled(MinecraftClient MC) throws Exception{}

    public void MCClose(MinecraftClient MC) throws Exception{}
}
