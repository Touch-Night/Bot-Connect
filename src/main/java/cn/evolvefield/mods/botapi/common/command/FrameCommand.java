package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class FrameCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setFrame")
                .then(Commands.argument("Frame", StringArgumentType.string())
                        .executes(FrameCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String frame = context.getArgument("Frame", String.class);
        BotApi.config.getCommon().setFrame(frame);
        BotData.setBotFrame(frame);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置机器人框架为:" + TextFormatting.LIGHT_PURPLE + frame), true);
        return 0;
    }


}
