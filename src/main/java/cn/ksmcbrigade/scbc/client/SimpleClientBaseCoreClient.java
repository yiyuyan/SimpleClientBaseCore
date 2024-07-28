package cn.ksmcbrigade.scbc.client;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hacks.ModuleList;
import cn.ksmcbrigade.scbc.manager.HackManager;
import cn.ksmcbrigade.scbc.utils.ConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleClientBaseCoreClient implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        SimpleClientBaseCore.config.logger("Mods: "+ Arrays.toString(FabricLoader.getInstance().getAllMods().toArray()));

        ArrayList<String> hacks = new ArrayList<>(ConfigUtils.enablesList);
        for(String h:hacks){
            try {
                HackManager.set(h,true);
            } catch (Exception e) {
                SimpleClientBaseCore.config.error("error in set a hack to enabled.",e);
            }
        }
    }
}
