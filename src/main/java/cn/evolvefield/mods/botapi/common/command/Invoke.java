package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Invoke {


    public static void invokeCommand(String message, String userid) {
        String commandBody = message.substring(1);
        String cmd = BotApi.config.getCommon().getBindCommand();

        if("tps".equals(commandBody)) {
            double overTickTime = mean(BotApi.SERVER.getTickTime(Level.OVERWORLD)) * 1.0E-6D;
            double overTPS = Math.min(1000.0 / overTickTime, 20);
            double netherTickTime = mean(BotApi.SERVER.getTickTime(Level.NETHER)) * 1.0E-6D;
            double netherTPS = Math.min(1000.0 / netherTickTime, 20);
            double endTickTime = mean(BotApi.SERVER.getTickTime(Level.END)) * 1.0E-6D;
            double endTPS = Math.min(1000.0 / endTickTime, 20);

            String outPut = String.format("主世界 TPS: %.2f", overTPS)
                    +"\n" + String.format("下界 TPS: %.2f", netherTPS)
                    +"\n" + String.format("末地 TPS: %.2f", endTPS);
            if(BotApi.config.getCommon().isDebuggable()){
                BotApi.LOGGER.info("处理命令tps:" + outPut);
            }
            SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(), outPut);
        }

        else if("list".equals(commandBody)) {
            List<ServerPlayer> users = BotApi.SERVER.getPlayerList().getPlayers();

            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                Component userList = users.stream()
                        .map(Player::getDisplayName)
                        .reduce(new TextComponent(""), (listString, user) ->
                                listString.getString().length() == 0 ? user : new TextComponent(listString.getString() + ", " + user.getString())
                        );
                result += "\n" + "玩家列表: " + userList.getString();
            }
            if(BotApi.config.getCommon().isDebuggable()){
                BotApi.LOGGER.info("处理命令list:" + result);
            }
            SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(), result);
        }
        else if(BotApi.config.getCommon().getBindCommand().equals(commandBody)){

                String BindPlay = message.substring(cmd.length());
                List<String> msg = new ArrayList<>();

                if(BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay) == null || !BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay).hasDisconnected() || !BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay).getName().getString().equalsIgnoreCase(BindPlay)){
                    String m = BotApi.config.getCommon().getBindNotOnline();
                    msg.add(m.replace("%Player%",BindPlay));
                    SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(),msg);
                    return;
                }

                if(BindApi.addBind(userid,BindPlay)){
                    String m = BotApi.config.getCommon().getBindSuccess();
                    msg.add(m.replace("%Player%",BindPlay));

                }else {
                    String m = BotApi.config.getCommon().getBindFail();
                    msg.add(m.replace("%Player%",BindPlay));
                }

                SendMessage.SendGroupMsg(BotApi.config.getCommon().getGroupId(),msg);

        }

    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, (total, item) -> total + item);

        return sum / values.length;
    }

}
