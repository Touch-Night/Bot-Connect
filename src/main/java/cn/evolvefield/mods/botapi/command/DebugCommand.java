package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

/**
 * @author cnlimiter
 * @date 2021/11/17 13:05
 */
public class DebugCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("debug")
                .then(Commands.argument("enabled", BoolArgumentType.bool())
                    .executes(DebugCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setDebuggable(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled) {
            context.getSource().sendSuccess(new StringTextComponent("已开启开发者模式"), true);
        } else {
            context.getSource().sendSuccess(new StringTextComponent("已关闭开发者模式"), true);
        }

        return 0;
    }
}
