package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftMixin {
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
    public void hackHandler(CallbackInfo ci){
        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){System.setProperty("java.awt.headless","false");}
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
    public void close(CallbackInfo ci){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.MCClose(getInstance());
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in minecraft closing hack handler.",e);
                }
            }
        });
    }
}
