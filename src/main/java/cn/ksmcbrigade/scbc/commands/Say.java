package cn.ksmcbrigade.scbc.commands;

import cn.ksmcbrigade.scbc.command.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.NetworkEncryptionUtils;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageBody;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import java.time.Instant;

public class Say extends Command {
    public Say() {
        super("say",1);
    }

    @Override
    public void onCommand(MinecraftClient MC, PlayerEntity player, String[] args) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for(String message:args){
            if(!first){
                builder.append(" ");
            }
            else{
                first = false;
            }
            builder.append(message);
        }
        String context = builder.toString();
        if(context.isEmpty()) return;
        ClientPlayNetworkHandler handler = MC.getNetworkHandler();
        if(handler==null) return;
        Instant instant = Instant.now();
        long l = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
        LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = handler.lastSeenMessagesCollector.collect();
        MessageSignatureData messageSignatureData = handler.messagePacker.pack(new MessageBody(context, instant, l, lastSeenMessages.lastSeen()));
        handler.sendPacket(new ChatMessageC2SPacket(context, instant, l, messageSignatureData, lastSeenMessages.update()));
    }
}
