package cn.evolvefield.mods.botapi.service;

import cn.evolvefield.mods.botapi.Botapi;
import cn.evolvefield.mods.botapi.command.Invoke;
import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.event.MsgToPlayerHandler;
import cn.evolvefield.mods.botapi.event.ServerTickHandler;
import cn.evolvefield.mods.botapi.message.MessageJson;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.UUID;

import static cn.evolvefield.mods.botapi.event.MsgToPlayerHandler.broadcastMessage;

public class MessageHandlerService {

    /**
     * 向已连接的服务端发送消息
     * @param event 需要处理的事件
     */
//    public static void sendMessage(ServerChatEvent event) {
//
//        SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(), String.format("[MC]<%s> %s", event.getPlayer().getDisplayName().getString(), event.getMessage()));
//        //sendToAll(new TextWebSocketFrame("/send_group_msg?group_id=" + ModConfig.GROUP_ID.get() + "&message=" + event.getMessage()));
//    }

    /**
     * 处理服务器数据并于本地服务端发送聊天信息
     * @param msg 服务器来源数据
     */
    public static void receiveMessage(String msg) {
        MessageJson serverMessage;
        String text;
        String name;
        long sourceId;
        long groupId;

            serverMessage = new MessageJson(msg);


            text = serverMessage.getRaw_message();

            sourceId = serverMessage.getUser_id();
            groupId = serverMessage.getGroup_id();
            name = serverMessage.getNickname();
            if(groupId == BotConfig.INSTANCE.getGROUP_ID()){
                if(text.startsWith("!")){
                    Invoke.invokeCommand(text);
                }
                else if(!text.startsWith("[CQ:") && BotConfig.INSTANCE.isRECEIVE_ENABLED()){
                    String toSend = String.format("§b[§lQQ§r§b]§a<%s>§f %s", name, text);
                    //MsgToPlayerHandler.getToSendQueue().add(toSend);
                    ServerTickHandler.INSTANCE.newTickTask(()->{
                        try{
                            Text textComponents = new LiteralText(toSend);
                            Botapi.INSTANCE.getServer().getPlayerManager().broadcastChatMessage(textComponents, MessageType.CHAT, UUID.randomUUID());

                        }catch(Exception e){
                            Botapi.LOGGER.error("QQBot error:\n", e);
                        }

                    });
                }
            }



    }
}
