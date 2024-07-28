package cn.ksmcbrigade.scbc.mixin;

import cn.ksmcbrigade.scbc.command.Command;
import cn.ksmcbrigade.scbc.manager.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "sendChatMessage",at = @At("HEAD"), cancellable = true)
    public void chat(String content, CallbackInfo ci) throws Exception {
        if(content.startsWith(".")) {
            MinecraftClient MC = MinecraftClient.getInstance();
            PlayerEntity player = MC.player;
            String name = CommandManager.getName(content);
            Command command = CommandManager.get(name);
            if (command == null && player != null) {
                player.sendMessage(Text.of("Can't found the command: " + name));
            } else if (player != null) {
                String[] args = CommandManager.getArgs(content);
                if (args.length >= command.length) {
                    command.onCommand(MC, player, args);
                } else {
                    player.sendMessage(Text.of("The command requires at least {} args: ".replace("{}", String.valueOf(command.length)) + name));
                }
            }
            ci.cancel();
        }
    }
}
