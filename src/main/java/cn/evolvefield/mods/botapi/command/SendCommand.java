package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.config.BotConfig;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;


public class SendCommand {

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotConfig.INSTANCE.setSEND_ENABLED(isEnabled);
        context.getSource().sendFeedback(
                new LiteralText("发送消息开关已被设置为 " + isEnabled), true);
        return 0;
    }
}
