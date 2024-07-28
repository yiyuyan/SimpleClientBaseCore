package cn.ksmcbrigade.scbc.commands;

import cn.ksmcbrigade.scbc.command.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Pos extends Command {
    public Pos() {
        super("pos");
    }

    @Override
    public void onCommand(MinecraftClient MC, PlayerEntity player, String[] args) {
        if(player!=null){
            Vec3d pos = player.getPos();
            player.sendMessage(Text.of("XYZ: "+Math.round(pos.x*1000.0D)/1000.0D+", "+Math.round(pos.y*1000.0D)/1000.0D+", "+Math.round(pos.z*1000.0D)/1000.0D));
        }
    }
}
