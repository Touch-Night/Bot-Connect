package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class StatusCommand {

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandException {
        boolean clientEnabled = BotConfig.INSTANCE.isIS_ENABLED();
        boolean receiveEnabled = BotConfig.INSTANCE.isRECEIVE_ENABLED();
        boolean sendEnabled = BotConfig.INSTANCE.isSEND_ENABLED();
        boolean connected = ClientThreadService.client != null;
        String host = BotConfig.INSTANCE.getWsHOST();
        int port = BotConfig.INSTANCE.getWsPORT();
        String key = BotConfig.INSTANCE.getKEY();
        String toSend = "GO_CQHTTP服务器:" + host + ":" + port + "\n"
                + "key:" + key + "\n"
                + "全局服务开启:" + clientEnabled + "\n"
                + "接收消息开启:" + receiveEnabled + "\n"
                + "发送消息开启:" + sendEnabled + "\n"
                + "连接状态:" + connected;
        context.getSource().sendFeedback(new LiteralText(toSend), true);
        return 0;
    }
}
