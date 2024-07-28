package cn.ksmcbrigade.scbc.commands;

import cn.ksmcbrigade.scbc.command.Command;
import cn.ksmcbrigade.scbc.manager.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class Help extends Command {
    public Help() {
        super("help");
    }

    @Override
    public void onCommand(MinecraftClient MC, PlayerEntity player, String[] args) {
        if(player!=null){
            CommandManager.getAll().forEach(c -> player.sendMessage(Text.of("command: "+c.name+"     args: "+c.length)));
        }
    }
}
