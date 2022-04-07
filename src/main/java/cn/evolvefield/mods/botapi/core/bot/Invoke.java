package cn.evolvefield.mods.botapi.core.bot;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.data.BindApi;
import cn.evolvefield.mods.botapi.api.events.GroupMessageEvent;
import cn.evolvefield.mods.botapi.api.message.SendMessage;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.WhiteList;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Invoke {


    public static void invokeCommand(GroupMessageEvent event) {
        String message = "";
        String bindCommand = BotApi.config.getCmd().getBindCommand();
        String whiteListCommand = BotApi.config.getCmd().getWhiteListCommand();

        if (BotData.getBotFrame().equalsIgnoreCase("cqhttp")) {
            message = event.getMessage();
            String[] formatMsg = message.split(" ");
            String commandBody = formatMsg[0].substring(1);

            if (!event.getRole().equals("member")) {
                masterMsgParse(whiteListCommand, formatMsg, commandBody);
            }
            memberMsgParse(event, message, bindCommand, commandBody, formatMsg);

        } else if (BotData.getBotFrame().equalsIgnoreCase("mirai")) {
            message = event.getMiraiMessage().get(1).getMessage();
            String[] formatMsg = message.split(" ");
            String commandBody = formatMsg[0].substring(1);

            if (!event.getPermission().equals("MEMBER")) {
                masterMsgParse(whiteListCommand, formatMsg, commandBody);
            }
            memberMsgParse(event, message, bindCommand, commandBody, formatMsg);

        }

    }

    private static void memberMsgParse(GroupMessageEvent event, String message, String bindCommand, String commandBody, String[] formatMsg) {
        if (commandBody.equals("tps")) {
            double overTickTime = mean(BotApi.SERVER.getTickTime(World.OVERWORLD)) * 1.0E-6D;
            double overTPS = Math.min(1000.0 / overTickTime, 20);
            double netherTickTime = mean(BotApi.SERVER.getTickTime(World.NETHER)) * 1.0E-6D;
            double netherTPS = Math.min(1000.0 / netherTickTime, 20);
            double endTickTime = mean(BotApi.SERVER.getTickTime(World.END)) * 1.0E-6D;
            double endTPS = Math.min(1000.0 / endTickTime, 20);

            String outPut = String.format("主世界 TPS: %.2f", overTPS)
                    + "\n" + String.format("下界 TPS: %.2f", netherTPS)
                    + "\n" + String.format("末地 TPS: %.2f", endTPS);


            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("处理命令tps:" + outPut);
            }
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), outPut);
        } else if (commandBody.equals("list")) {
            List<ServerPlayerEntity> users = BotApi.SERVER.getPlayerList().getPlayers();

            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                ITextComponent userList = users.stream()
                        .map(PlayerEntity::getDisplayName)
                        .reduce(new StringTextComponent(""), (listString, user) ->
                                listString.getString().length() == 0 ? user : new StringTextComponent(listString.getString() + ", " + user.getString())
                        );
                result += "\n" + "玩家列表: " + userList.getString();
            }
            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("处理命令list:" + result);
            }
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), result);
        } else if (commandBody.startsWith(bindCommand)) {


            if (formatMsg.length == 1) {
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), "请输入有效的游戏名");
                return;
            }

            String BindPlay = formatMsg[1];
            List<String> msg = new ArrayList<>();


            if (BotApi.SERVER.getPlayerList().getPlayerByName(BindPlay) == null) {
                String m = BotApi.config.getCmd().getBindNotOnline();
                msg.add(m.replace("%Player%", BindPlay));
                SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                return;
            }

            if (BindApi.addBind(event.getUserId(), BindPlay)) {
                String m = BotApi.config.getCmd().getBindSuccess();
                msg.add(m.replace("%Player%", BindPlay));

            } else {
                String m = BotApi.config.getCmd().getBindFail();
                msg.add(m.replace("%Player%", BindPlay));
            }

            if (BotApi.config.getCommon().isDebuggable()) {
                BotApi.LOGGER.info("处理命令bind:" + msg + "PlayerName:" + BindPlay);
            }

            SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);

        }
    }

    private static void masterMsgParse(String whiteListCommand, String[] formatMsg, String commandBody) {
        if (commandBody.startsWith(whiteListCommand)) {
            String subCmd = formatMsg[1];
            switch (subCmd) {
                case "add": {
                    String playerName = formatMsg[2];
                    int success = BotApi.SERVER.getCommands().performCommand(new CommandSource(ICommandSource.NULL, Vector3d.ZERO, Vector2f.ZERO, BotApi.SERVER.overworld(), 4, "",
                            new StringTextComponent(""), Objects.requireNonNull(BotApi.SERVER), null), "whitelist add " + playerName);

                    if (success == 0) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单失败或已经添加了白名单！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "添加" + playerName + "至白名单成功！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white add " + playerName);
                    }
                    break;
                }
                case "del": {
                    String playerName = formatMsg[2];
                    int success = BotApi.SERVER.getCommands().performCommand(new CommandSource(ICommandSource.NULL, Vector3d.ZERO, Vector2f.ZERO, BotApi.SERVER.overworld(), 4, "",
                            new StringTextComponent(""), Objects.requireNonNull(BotApi.SERVER), null), "whitelist remove " + playerName);

                    if (success == 0) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "失败或已经从白名单移除！");
                    } else {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "从白名单移除" + playerName + "成功！");
                    }
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white del " + playerName);
                    }
                    break;
                }
                case "list": {
                    String[] strings = BotApi.SERVER.getPlayerList().getWhiteListNames();
                    List<String> msg = new ArrayList<>();
                    msg.add("当前服务器白名单：\n");
                    msg.addAll(Arrays.asList(strings));
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), msg);
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white list");
                    }
                    break;
                }
                case "on": {
                    PlayerList playerList = BotApi.SERVER.getPlayerList();
                    if (playerList.isUsingWhitelist()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "已经打开了白名单！哼~");
                    } else {
                        playerList.setUsingWhiteList(true);
                        BotApi.SERVER.setEnforceWhitelist(true);
                        kickUnlistedPlayers();
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "打开白名单成功！");

                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white on");
                    }
                    break;
                }
                case "off": {
                    PlayerList playerList = BotApi.SERVER.getPlayerList();
                    if (!playerList.isUsingWhitelist()) {
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "白名单早就关了！");
                    } else {
                        playerList.setUsingWhiteList(false);
                        BotApi.SERVER.setEnforceWhitelist(false);
                        SendMessage.Group(BotApi.config.getCommon().getGroupId(), "关闭白名单成功！");
                    }

                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white off");
                    }
                    break;
                }
                case "reload": {
                    BotApi.SERVER.getPlayerList().reloadWhiteList();
                    kickUnlistedPlayers();
                    SendMessage.Group(BotApi.config.getCommon().getGroupId(), "刷新白名单成功！");
                    if (BotApi.config.getCommon().isDebuggable()) {
                        BotApi.LOGGER.info("处理命令white reload");
                    }
                    break;
                }
            }
        }
    }

    public static void kickUnlistedPlayers() {
        if (BotApi.SERVER.isEnforceWhitelist()) {
            PlayerList playerList = BotApi.SERVER.getPlayerList();
            WhiteList userWhiteList = playerList.getWhiteList();
            List<ServerPlayerEntity> list = Lists.newArrayList(playerList.getPlayers());

            for (ServerPlayerEntity serverPlayer : list) {
                if (!userWhiteList.isWhiteListed(serverPlayer.getGameProfile())) {
                    serverPlayer.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.not_whitelisted"));
                }
            }

        }
    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, Long::sum);

        return sum / values.length;
    }

}
