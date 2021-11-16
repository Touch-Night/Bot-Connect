package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
public class StatusCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("status").executes(StatusCommand::execute);
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        boolean clientEnabled = BotApi.config.getCommon().isENABLED();
        boolean receiveEnabled = BotApi.config.getCommon().isRECEIVE_ENABLED();
        boolean sendEnabled = BotApi.config.getCommon().isSEND_ENABLED();
        boolean connected = ClientThreadService.client != null;
        String host = BotApi.config.getCommon().getWsHOST();
        int port = BotApi.config.getCommon().getWsPORT();
        String key = BotApi.config.getCommon().getKEY();
        String toSend = "GO_CQHTTP服务器:" + host + ":" + port + "\n"
                + "key:" + key + "\n"
                + "全局服务开启:" + clientEnabled + "\n"
                + "接收消息开启:" + receiveEnabled + "\n"
                + "发送消息开启:" + sendEnabled + "\n"
                + "连接状态:" + connected;
        context.getSource().sendSuccess(new StringTextComponent(toSend), true);
        return 0;
    }
}
