package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import cn.ksmcbrigade.scbc.manager.HackManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Inject(method = "onKeyPressed",at = @At("HEAD"))
    private static void key(InputUtil.Key key, CallbackInfo ci) {
        if(key.getCode()==InputUtil.GLFW_KEY_RIGHT_ALT) HackManager.HackGui.open();
        HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.keyInput(key.getCode(),false);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in key input hack handler.",e);
                }
            }
            if(key.getCode()==f.key){
                try {
                    f.setEnabled(!f.enabled);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in key set a hack.",e);
                }
            }
        });
    }
}
