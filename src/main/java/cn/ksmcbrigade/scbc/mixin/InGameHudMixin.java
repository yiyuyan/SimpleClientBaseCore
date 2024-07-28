package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "tick()V",at = @At("HEAD"))
    public void renderTick(CallbackInfo ci){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.render();
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in render tick hack handler.",e);
                }
            }
        });
    }

    @Inject(method = "render",at = @At("HEAD"))
    public void renderGameTick(DrawContext context, float tickDelta, CallbackInfo ci){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.renderGame(context,tickDelta);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in render tick game hack handler.",e);
                }
            }
        });
    }
}
