package cn.evolvefield.mods.botapi.init.events;
import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ChatEventHandler {
    @SubscribeEvent
    public static void onChatEvent(ServerChatEvent event) {
        if (BotApi.config.getCommon().isS_CHAT_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()) {
            SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(),String.format("[" + BotApi.config.getCommon().getQqPrefix() + "]<%s> %s", event.getUsername(), event.getMessage()));
        }
    }
}