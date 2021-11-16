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


public class SendCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("send")
                .then(Commands.argument("<all|join|leave|death|achievements>", StringArgumentType.greedyString())
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(SendCommand::execute))
                )
                ;
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        String type = context.getArgument("<all|join|leave|death|achievements>", String.class);
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        switch (type){
            default:{
                        context.getSource().sendSuccess(
                new StringTextComponent("参数不合法"), true);
                break;
            }
            case "all":{
                BotApi.config.getCommon().setSEND_ENABLED(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                            context.getSource().sendSuccess(
                new StringTextComponent("发送消息开关已被设置为打开"), true);
                }
                else
                {
                            context.getSource().sendSuccess(
                new StringTextComponent("发送消息开关已被设置为关闭"), true);
                }
                break;
            }
            case "join":{
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
                break;
            }
            case "leave":{
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
                break;
            }
            case "death":{
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
                break;
            }
            case "chat":{
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
                break;
            }
            case "achievements":{
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
                break;
            }
        }
        return 0;
    }
}
