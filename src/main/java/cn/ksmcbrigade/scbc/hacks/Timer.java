package cn.ksmcbrigade.scbc.hacks;

import cn.ksmcbrigade.scbc.hack.Config;
import cn.ksmcbrigade.scbc.hack.Hack;
import cn.ksmcbrigade.scbc.hack.Type;
import cn.ksmcbrigade.scbc.manager.HackManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static cn.ksmcbrigade.scbc.client.SimpleClientBaseCoreClient.MC;

public class Timer extends Hack {
    public Timer() throws IOException, NoSuchFieldException, IllegalAccessException {
        super(Type.OTHER, "Timer", false, InputUtil.GLFW_KEY_Y);
        JsonObject data = new JsonObject();
        data.addProperty("times",2.1f);
        this.setConfig(new Config(new File(this.getName()),data));
    }
}
