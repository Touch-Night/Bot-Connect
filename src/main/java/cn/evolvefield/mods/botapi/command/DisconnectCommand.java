package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class DisconnectCommand {

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandException {
        boolean isSuccess = ClientThreadService.stopWebSocketClient();
        if (isSuccess) {
            context.getSource().sendFeedback(new LiteralText("已断开连接"), true);
        } else {
            context.getSource().sendFeedback(new LiteralText("目前未连接"), true);
        }
        BotConfig.INSTANCE.setIS_ENABLED(false);
        return 0;
    }
}
