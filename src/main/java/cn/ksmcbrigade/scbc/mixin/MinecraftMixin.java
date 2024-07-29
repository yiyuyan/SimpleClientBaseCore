package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import cn.ksmcbrigade.scbc.utils.ConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(MinecraftClient.class)
public abstract class MinecraftMixin {

    @Unique
    public boolean scbc$init = false;

    @Shadow
    public static MinecraftClient getInstance() {
        return null;
    }

    @Inject(method = {"<init>"},at = @At(value = "INVOKE",target = "Lnet/minecraft/client/resource/DefaultClientResourcePackProvider;<init>(Ljava/nio/file/Path;)V",shift = At.Shift.BEFORE))
    public void init(RunArgs args, CallbackInfo ci){
        if(SimpleClientBaseCore.selfPoint!=null)SimpleClientBaseCore.selfPoint.getEntrypoint().onInitializeClient();
        for(EntrypointContainer<ClientModInitializer> clientPoint:SimpleClientBaseCore.hidesClient){
            clientPoint.getEntrypoint().onInitializeClient();
        }
        for(EntrypointContainer<ModInitializer> mainPoint:SimpleClientBaseCore.hidesMain){
            mainPoint.getEntrypoint().onInitialize();
        }
    }

    @Inject(method = "tick",at = @At("HEAD"))
    public void hackHandler(CallbackInfo ci) throws IOException, NoSuchFieldException, IllegalAccessException {
        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){System.setProperty("java.awt.headless","false");}

        if(!scbc$init && ConfigUtils.init && HackManager.init){

            ArrayList<String> hacks = new ArrayList<>(ConfigUtils.enablesList);
            for(String h:hacks){
                try {
                    HackManager.set(h,true);
                    SimpleClientBaseCore.config.logger("Set a hack to enabled: "+h);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in set a hack to enabled.",e);
                }
            }
            SimpleClientBaseCore.config.config.savaArrays("enables");
            SimpleClientBaseCore.config.logger("Set hacks enabled done.");

            Map<String,Integer> keys = new HashMap<>(ConfigUtils.keysList);
            for(String k:keys.keySet()){
                try {
                    Objects.requireNonNull(HackManager.get(k)).key = keys.get(k);
                    SimpleClientBaseCore.config.logger("Set name hack key bind to ".replace("name",k) + keys.get(k));
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in set a hack's key bind.",e);
                }
            }
            SimpleClientBaseCore.config.config.savaObject("keys");
            SimpleClientBaseCore.config.logger("Set hacks key bind done.");

            scbc$init = true;
        }

        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.clientTick(getInstance());
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in client tick hack handler.",e);
                }
            }
        });
    }

    @Inject(method = "close",at = @At("HEAD"))
    public void close(CallbackInfo ci) throws IOException, NoSuchFieldException, IllegalAccessException {
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.MCClose(getInstance());
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in minecraft closing hack handler.",e);
                }
            }
        });
        SimpleClientBaseCore.config.config.savaArrays("enables");
        SimpleClientBaseCore.config.config.savaObject("keys");
    }
}
