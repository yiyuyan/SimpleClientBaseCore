package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.SimpleClientBaseCore;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class ConnectingMixin extends Screen {
    protected ConnectingMixin(Text title) {
        super(title);
    }

    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V",at = @At("HEAD"))
    public void connect(ServerInfo entry, CallbackInfo ci){
        SimpleClientBaseCore.config.lastServer = entry.address;
    }

    @Inject(method = "init",at = @At("TAIL"))
    public void addButton(CallbackInfo ci){
        if(this.client==null) return;
        ButtonWidget button = ButtonWidget.builder(Text.literal("LastServer"), ref-> {
            if(this.client!=null){
                ConnectScreen.connect(this, this.client, ServerAddress.parse(SimpleClientBaseCore.config.lastServer), new ServerInfo("",SimpleClientBaseCore.config.lastServer,false),false);
            }
        }).size(100,20).position(width-154,10).build();
        button.active = !SimpleClientBaseCore.config.lastServer.isEmpty();
        this.addDrawableChild(button);
    }
}
