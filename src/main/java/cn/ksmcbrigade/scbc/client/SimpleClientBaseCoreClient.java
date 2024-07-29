package cn.ksmcbrigade.scbc.client;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

public class SimpleClientBaseCoreClient implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        SimpleClientBaseCore.config.logger("Mods: "+ Arrays.toString(FabricLoader.getInstance().getAllMods().toArray()));
    }
}
