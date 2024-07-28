package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow @Nullable protected MinecraftClient client;

    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.screenTick(this.client);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in screen tick hack handler.",e);
                }
            }
        });
    }

    @Inject(method = "keyPressed",at = @At("HEAD"))
    public void key(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.keyInput(keyCode,true);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in key input hack handler.",e);
                }
            }
        });
    }
}
