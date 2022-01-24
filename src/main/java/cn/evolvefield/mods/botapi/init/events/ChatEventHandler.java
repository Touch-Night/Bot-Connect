package cn.evolvefield.mods.botapi.init.events;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.SendMessage;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if(BotApi.config.getCommon().isSEND_ENABLED()){
            if (BotApi.config.getCommon().isS_CHAT_ENABLE() ) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format("[MC]<%s> %s", event.getPlayer().getDisplayName().getString(), event.getMessage()));
            }
        }

    }
}