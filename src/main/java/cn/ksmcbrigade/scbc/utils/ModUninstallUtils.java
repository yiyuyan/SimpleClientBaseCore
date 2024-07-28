package cn.ksmcbrigade.scbc.utils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.entrypoint.EntrypointStorage;
import net.fabricmc.loader.impl.util.DefaultLanguageAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModUninstallUtils {
    public static boolean uninstall(String modID) {
        boolean ret = false;

        try {

            Class<?> clazz = FabricLoaderImpl.class;
            FabricLoaderImpl instance = FabricLoaderImpl.INSTANCE;

            if (FabricLoader.getInstance().isModLoaded(modID)) {
                List<ModContainerImpl> modList = instance.getModsInternal();
                ArrayList<ModContainerImpl> newList = new ArrayList<>();

                modList.forEach(m -> {
                    if(!m.getMetadata().getId().equals(modID)){
                        newList.add(m);
                    }
                });

                Field listField = clazz.getDeclaredField("mods");
                listField.setAccessible(true);
                listField.set(instance, newList);

                boolean h1 = true;
                if(h1){
                    //Field modField = clazz.getDeclaredField("modCandidates");  //temp field
                    Field mapField = clazz.getDeclaredField("modMap");
                    Field languageMap = clazz.getDeclaredField("adapterMap");
                    //modField.setAccessible(true);
                    mapField.setAccessible(true);
                    languageMap.setAccessible(true);

                    //after the fabric mod setup,the modCandidates will be null.

                    /*try {
                        Iterator<ModCandidate> iterator2 = ((ArrayList<ModCandidate>) modField.get(instance)).iterator();
                        ArrayList<ModCandidate> candidates = new ArrayList<>();
                        while (iterator2.hasNext()) {
                            ModCandidate mod = iterator2.next();
                            if (!mod.getMetadata().getId().equals(modID)) {
                                candidates.add(mod);
                            }
                        }
                        modField.set(instance,candidates);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }*/

                    Map<String, ModContainerImpl> maps = (Map<String, ModContainerImpl>)mapField.get(instance);
                    Map<String,ModContainerImpl> newMap = new HashMap<>();
                    for(String key:maps.keySet()){
                        if(!key.equals(modID)){
                            newMap.put(key,maps.get(key));
                        }
                    }
                    mapField.set(instance,newMap);

                    Map<String, DefaultLanguageAdapter> maps2 = (Map<String, DefaultLanguageAdapter>)languageMap.get(instance);
                    Map<String, DefaultLanguageAdapter> newMap2 = new HashMap<>();
                    for(String key:maps2.keySet()){
                        if(!key.equals(modID)){
                            newMap2.put(key,maps2.get(key));
                        }
                    }
                    languageMap.set(instance,newMap2);

                    Field FieldEntrypointStorage = clazz.getDeclaredField("entrypointStorage");
                    Method method = clazz.getDeclaredMethod("setupMods");
                    method.setAccessible(true);
                    FieldEntrypointStorage.setAccessible(true);
                    FieldEntrypointStorage.set(instance,new EntrypointStorage());

                    method.invoke(instance);

                    ret = true;

                    /*try {
                        Class<?> ModMenuClazz = Class.forName("com.terraformersmc.modmenu.ModMenu");

                        ModMenu.MODS.remove(modID);
                        ModMenu.ROOT_MODS.remove(modID);

                        ModMenu.clearModCountCache();
                    }
                    catch (ClassNotFoundException e){
                        System.out.println("Can't find the mod menu.");
                    }
                    catch (Exception e){
                        System.out.println("error: "+e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }*/
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return ret;
    }

    public static EntrypointContainer<ClientModInitializer> getEntrypoint(String modID){
        for(EntrypointContainer<ClientModInitializer> container:FabricLoader.getInstance().getEntrypointContainers("client", ClientModInitializer.class)){
            if(container.getProvider().getMetadata().getId().equalsIgnoreCase(modID)){
                return container;
            }
        }
        return null;
    }

    public static EntrypointContainer<ModInitializer> getEntrypointMain(String modID){
        for(EntrypointContainer<ModInitializer> container:FabricLoader.getInstance().getEntrypointContainers("main", ModInitializer.class)){
            if(container.getProvider().getMetadata().getId().equalsIgnoreCase(modID)){
                return container;
            }
        }
        return null;
    }
}
