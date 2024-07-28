package cn.ksmcbrigade.scbc;

import cn.ksmcbrigade.scbc.client.SimpleClientBaseCoreClient;
import cn.ksmcbrigade.scbc.manager.CommandManager;
import cn.ksmcbrigade.scbc.manager.HackManager;
import cn.ksmcbrigade.scbc.utils.ModUninstallUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleClientBaseCore implements PreLaunchEntrypoint {

    public static final Config config;

    public static EntrypointContainer<ClientModInitializer> selfPoint;

    public static ArrayList<EntrypointContainer<ClientModInitializer>> hidesClient = new ArrayList<>();
    public static ArrayList<EntrypointContainer<ModInitializer>> hidesMain = new ArrayList<>();

    static {
        try {
            config = new Config();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final HackManager hackManager = new HackManager();

    public static final CommandManager commandManager = new CommandManager();

    @Override
    public void onPreLaunch() {

        config.logger("Hello Simple Client!");

        try {
            config.config.getArrays("uninstalls").forEach(f -> {
                if(f.equalsIgnoreCase("scbc")){
                    selfPoint = ModUninstallUtils.getEntrypoint(f);
                }
                ModUninstallUtils.uninstall(f);
            });
            config.logger("Uninstalled mods done.");
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            config.error("Can't uninstall the mod:"+e.getMessage(),e);
        }

        try {
            config.config.getArrays("hides").forEach(f -> {
                EntrypointContainer<ModInitializer> main = ModUninstallUtils.getEntrypointMain(f);
                EntrypointContainer<ClientModInitializer> client = ModUninstallUtils.getEntrypoint(f);
                if(main!=null) hidesMain.add(main);
                if(client!=null) hidesClient.add(client);
                ModUninstallUtils.uninstall(f);
            });
            config.logger("Hidden mods done.");
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            config.error("Can't hidden the mod:"+e.getMessage(),e);
        }

        WorldTickCallback.EVENT.register(world -> HackManager.hacks.forEach(f -> {
            if(f.enabled){
                try {
                    f.worldTick(SimpleClientBaseCoreClient.MC,world);
                } catch (Exception e) {
                    SimpleClientBaseCore.config.error("error in world tick hack handler.",e);
                }
            }
        }));

        config.logger("Events registered done.");
    }
}
