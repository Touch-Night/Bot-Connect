package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class BotIDCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setBot")
                .then(Commands.argument("BotId", LongArgumentType.longArg())
                        .executes(BotIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        long id = context.getArgument("BotId", Long.class);
        BotApi.config.getCommon().setBotId(id);
        BotData.setQQId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置机器人QQ号为:" + id), true);
        return 0;
    }


}
