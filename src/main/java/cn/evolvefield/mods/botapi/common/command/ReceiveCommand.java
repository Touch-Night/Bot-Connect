package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import static net.minecraft.command.Commands.literal;

public class ReceiveCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return literal("receive")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::allExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::chatExecute)))
                .then(literal("cmd")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::cmdExecute)))
                ;
    }
    public static int allExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setRECEIVE_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局接收群消息开关已被设置为打开"), true);
        }
        else
        {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局接收群消息开关已被设置为关闭"), true);
        }
        return 0;
    }

    public static int chatExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setR_CHAT_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内聊天消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内聊天消息开关已被设置为关闭"), true);
        }
        return 0;

    }
    public static int cmdExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setR_COMMAND_ENABLED(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内命令消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("接收群内命令消息开关已被设置为关闭"), true);
        }
        return 0;
    }


}
