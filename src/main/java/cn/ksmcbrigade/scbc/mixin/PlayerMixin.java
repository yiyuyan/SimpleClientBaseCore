package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.client.SimpleClientBaseCoreClient;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.playerTick(SimpleClientBaseCoreClient.MC, MinecraftClient.getInstance().player);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in player tick hack handler.",e);
                }
            }
        });
    }
}
