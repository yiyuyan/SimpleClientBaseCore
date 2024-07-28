package cn.ksmcbrigade.scbc.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Command {
    public final String name;
    public int length;

    public Command(String name,int argsLength){
        this.name = name;
        this.length = argsLength;
    }

    public Command(String name){
        this(name,0);
    }

    public void onCommand(MinecraftClient MC, PlayerEntity player, String[] args) throws Exception{}
}
