package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(RenderTickCounter.class)
public class TimerMixin {
    @Mutable
    @Shadow @Final private float tickTime;

    @Inject(method = "beginRenderTick",at = @At("HEAD"))
    public void tick(long timeMillis, CallbackInfoReturnable<Integer> cir){
        try {
            this.tickTime = 1000.0F / (20.0F*(HackManager.timer.enabled? Objects.requireNonNull(HackManager.timer.getConfig().get("times")).getAsFloat():1.0F));
        }
        catch (Exception e){
            SimpleClientBaseCore.config.error("error in timer hack mixin.",e);
        }
    }
}
