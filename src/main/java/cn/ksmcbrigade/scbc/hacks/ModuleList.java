package cn.ksmcbrigade.scbc.hacks;

import cn.ksmcbrigade.scbc.hack.Config;
import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hack.Type;
import cn.ksmcbrigade.scbc.manager.HackManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static cn.ksmcbrigade.scbc.client.SimpleClientBaseCoreClient.MC;

public class ModuleList extends Hack {
    public ModuleList() throws IOException, NoSuchFieldException, IllegalAccessException {
        super(Type.RENDER, "ModuleList", true);
        JsonObject data = new JsonObject();
        data.addProperty("color",Color.WHITE.getRGB());
        this.setConfig(new Config(new File(this.getName()),data));
    }

    @Override
    public void renderGame(DrawContext context, float ticks) {
        TextRenderer font = MC.textRenderer;
        Hack[] mods = HackManager.getNewShotAll(true);
        for(int i=0;i<mods.length;i++){
            JsonElement color;
            if(!HackManager.rainbowUi.enabled){
                color = getConfig().get("color");
            }
            else{
                color = RainbowUi.getColor().get("c");
            }
            context.drawText(font,mods[i].getName(),MC.getWindow().getScaledWidth()-font.getWidth(mods[i].getName())-2, 2 + i * font.fontHeight,color==null?Color.WHITE.getRGB():color.getAsInt(),true);
        }
    }
}
