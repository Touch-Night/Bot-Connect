package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.event.CommandEventHandler;
import cn.evolvefield.mods.botapi.event.MsgToPlayerHandler;
import cn.evolvefield.mods.botapi.event.PlayerHandlerEvent;
import cn.evolvefield.mods.botapi.event.ServerTickHandler;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.UUID;

public class Botapi implements ModInitializer {


    public static final UUID SERVER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static Botapi INSTANCE = null;
    public static final String MOD_ID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger("BotApi");

    private MinecraftServer server = null;
    private File folder;

    public Botapi(){
        this.folder = new File("qqBotapi");
        if(!this.folder.exists()){
            this.folder.mkdirs();
        }
        INSTANCE = this;
    }
    public MinecraftServer getServer(){
        return this.server;
    }

    public File getDataFolder(){
        return this.folder;
    }

    @Override
    public void onInitialize() {
        CommandEventHandler.registerCommandRegisters();
        LOGGER.info("QQBotApi is onInitialize");
        ServerLifecycleEvents.SERVER_STARTING.register(this::onStarting);
        ServerLifecycleEvents.SERVER_STARTED.register(this::onStarted);
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register(this::onReloadCall);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onStopping);
        ServerTickEvents.START_SERVER_TICK.register(ServerTickHandler.INSTANCE::onTick);
        PlayerHandlerEvent.registerDeathWorld();
        PlayerHandlerEvent.registerEnterWorld();
        PlayerHandlerEvent.registerLeaveWorld();

    }


    public void onStarting(MinecraftServer server){
        this.server = server;
        this.onReload();
    }

    public void onStarted(MinecraftServer server){
        if (BotConfig.INSTANCE.isIS_ENABLED()) {
            ClientThreadService.runWebSocketClient();
        }
    }



    public void onReloadCall(MinecraftServer server, ServerResourceManager serverResourceManager){
        LOGGER.info("QQBotApi is reloading...");
        this.onReload();
    }

    public void onReload(){
        BotConfig.INSTANCE.reload();
        if (BotConfig.INSTANCE.isIS_ENABLED()) {
            ClientThreadService.runWebSocketClient();
        }
    }

    public void onSave(){
        BotConfig.INSTANCE.save();
    }

    public void onStopping(MinecraftServer server){
        this.onSave();
        this.server = null;
        ClientThreadService.stopWebSocketClient();
    }
}
