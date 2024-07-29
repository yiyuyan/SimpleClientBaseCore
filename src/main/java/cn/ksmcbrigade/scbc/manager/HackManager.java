package cn.ksmcbrigade.scbc.manager;

import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hacks.ModuleList;
import cn.ksmcbrigade.scbc.hacks.RainbowUi;
import cn.ksmcbrigade.scbc.hacks.Timer;
import net.minecraft.util.math.Vec2f;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static cn.ksmcbrigade.scbc.manager.HackFrame.in;
import static cn.ksmcbrigade.scbc.manager.HackFrame.instance;

public class HackManager {
    public static ArrayList<Hack> hacks = new ArrayList<>();

    public static volatile ModuleList moduleList;
    public static volatile RainbowUi rainbowUi;

    public static volatile Timer timer;

    static {
        try {
            moduleList = new ModuleList();
            rainbowUi = new RainbowUi();
            timer = new Timer();
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean init = false;

    public HackManager(){
        //for(int i=0;i<50;i++){
        while (moduleList == null) {
            Thread.onSpinWait();
        }
        hacks.add(moduleList);
        while (rainbowUi == null) {
            Thread.onSpinWait();
        }
        hacks.add(rainbowUi);
        while (timer == null) {
            Thread.onSpinWait();
        }
        hacks.add(timer);
        init = true;
        //}
    }

    public static Hack get(String name){
        for(Hack hack:hacks){
            if(hack.getEnName().equalsIgnoreCase(name)){
                return hack;
            }
        }
        return null;
    }

    public static boolean enabled(String name){
        for(Hack hack:hacks){
            if(hack.getEnName().equalsIgnoreCase(name)){
                return hack.enabled;
            }
        }
        return false;
    }

    public static void set(String name,boolean s) throws Exception {
        for(Hack hack:hacks){
            if(hack.getEnName().equalsIgnoreCase(name)){
                hack.setEnabled(s);
                break;
            }
        }
    }

    public static ArrayList<Hack> getAll(boolean enabled){
        return enabled?new ArrayList<>(hacks.stream().filter(m -> m.enabled).toList()):hacks;
    }

    public static Hack[] getNewShotAll(boolean enabled){
        ArrayList<Hack> modules = new ArrayList<>();
        if(enabled){
            modules.addAll(hacks.stream().filter(m -> m.enabled).toList());
        }
        else{
            modules.addAll(hacks);
        }
        Hack[] newModules = modules.toArray(new Hack[0]);
        Arrays.sort(newModules, Comparator.comparing(Hack::length).reversed());
        return newModules;
    }

    public static class HackGui extends JFrame {

        public static void open() {
            if (!in) {
                Thread thread = new Thread(() -> {
                    if (Boolean.parseBoolean(System.getProperty("java.awt.headless"))) {
                        System.setProperty("java.awt.headless", "false");
                    }
                    new HackFrame();
                });
                thread.start();
            }
        }

        public static void reopen(){
            if(in && HackFrame.instance!=null && HackFrame.tabbedPane!=null){
                Vec2f vec2f = new Vec2f(instance.getX(), instance.getY());
                int index = HackFrame.tabbedPane.getSelectedIndex();

                in = false;
                instance.dispose();
                instance = null;

                open();

                if(instance!=null){
                    instance.setLocation((int) vec2f.x, (int) vec2f.y);
                }
                if(HackFrame.tabbedPane!=null){
                    HackFrame.tabbedPane.setSelectedIndex(index);
                }
            }
        }
    }
}

