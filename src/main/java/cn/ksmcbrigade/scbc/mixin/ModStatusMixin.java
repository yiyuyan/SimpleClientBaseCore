package cn.ksmcbrigade.scbc.mixin;

import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModStatus.class)
public class ModStatusMixin {
    /**
     * @author KSmc_brigade
     * @reason fake it
     */
    @Overwrite
    public boolean isModded() {
        return false;
    }
}
