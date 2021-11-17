package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import static net.minecraft.command.Commands.literal;


public class SendCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return literal("send")
                .then(literal("all")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::allExecute)))
                .then(literal("join")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::joinExecute)))
                .then(literal("leave")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::leaveExecute)))
                .then(literal("death")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::deathExecute)))
                .then(literal("chat")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::chatExecute)))
                .then(literal("achievements")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::achievementsExecute)))
                ;
    }


    public static int allExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setSEND_ENABLED(isEnabled);
        ConfigManger.saveBotConfig(BotApi.config);
        if (isEnabled)
        {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局发送消息开关已被设置为打开"), true);
        }
        else
        {
            context.getSource().sendSuccess(
                    new StringTextComponent("全局发送消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int joinExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_JOIN_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家加入游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家加入游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int leaveExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_LEAVE_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家离开游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家离开游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int deathExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_DEATH_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家死亡游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家死亡游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int chatExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_CHAT_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家聊天游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家聊天游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }
    public static int achievementsExecute(CommandContext<CommandSource> context) throws CommandException {
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        BotApi.config.getCommon().setS_ADVANCE_ENABLE(isEnabled);
        if (isEnabled)
        {
            BotApi.config.getCommon().setSEND_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家成就游戏消息开关已被设置为打开"), true);
        }
        else
        {
            ConfigManger.saveBotConfig(BotApi.config);
            context.getSource().sendSuccess(
                    new StringTextComponent("发送玩家成就游戏消息开关已被设置为关闭"), true);
        }
        return 0;
    }

}
