package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class FrameCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("setFrame")
                .then(Commands.argument("Frame", StringArgumentType.string())
                        .executes(FrameCommand::execute));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var frame = context.getArgument("Frame", String.class);
        BotApi.config.getCommon().setFrame(frame);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new TextComponent("已设置机器人框架为:" + ChatFormatting.LIGHT_PURPLE + frame), true);
        return 0;
    }


}
