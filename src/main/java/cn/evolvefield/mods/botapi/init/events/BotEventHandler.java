package cn.evolvefield.mods.botapi.init.events;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.events.NoticeEvent;
import cn.evolvefield.mods.botapi.api.events.PrivateMessageEvent;
import cn.evolvefield.mods.botapi.api.events.RequestEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import cn.evolvefield.mods.botapi.common.command.Invoke;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 19:03
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class BotEventHandler {
    @SubscribeEvent
    public static void GroupEvent(GroupMessageEvent event){

        String message = event.getMessage();

        if(event.getGroupId().equals(BotApi.config.getCommon().getGroupId()) && BotApi.config.getCommon().isRECEIVE_ENABLED()) {
            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("收到群" + event.getGroupId() + "发送消息" + event.getMessage());
            }
            if (event.getMessage().startsWith(BotApi.config.getCommon().getCommandStart()) && BotApi.config.getCommon().isR_COMMAND_ENABLED()) {

                Invoke.invokeCommand(event.getMessage(), event.getUserId());

            } else if (!event.getMessage().startsWith("[CQ:") && BotApi.config.getCommon().isR_CHAT_ENABLE()) {
                String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", event.getNickName(), event.getMessage());
                TickEventHandler.getToSendQueue().add(toSend);
            }
        }
    }

    @SubscribeEvent
    public static void PrivateEvent(PrivateMessageEvent event){

    }

    @SubscribeEvent
    public static void NoticeEvent(NoticeEvent event){
        if(BotApi.config.getCommon().isS_WELCOME_ENABLE()
                && BotApi.config.getCommon().isSEND_ENABLED()
                && event.getGroup_id().equals(BotApi.config.getCommon().getGroupId())){
            if (event.getNoticeType().equals("group_increase")) {
                SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getWelcomeNotice());
            } else if (event.getNoticeType().equals("group_decrease")) {
                SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(), BotApi.config.getCommon().getLeaveNotice());
            }
        }
    }

    @SubscribeEvent
    public static void RequestsEvent(RequestEvent event){

    }
}
