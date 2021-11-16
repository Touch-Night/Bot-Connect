package cn.evolvefield.mods.botapi.event;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;


@Mod.EventBusSubscriber
public class WorldEventHandler {

    @SubscribeEvent
    public static void onEntranceEvent(FMLServerStartingEvent event) {
        if (BotApi.config.getCommon().isENABLED()) {
            ClientThreadService.runWebSocketClient();
        }
    }

    @SubscribeEvent
    public static void onExitEvent(FMLServerStoppingEvent event) {
        ClientThreadService.stopWebSocketClient();
    }

//    @SubscribeEvent
//    public static void onConfigChanged(ModConfig.ModConfigEvent event) {
//        if (event.getModID().equals(BotApi.MODID)) {
//            ConfigManager.sync(BotApi.MODID, Config.Type.INSTANCE);
//        }
//    }
}
