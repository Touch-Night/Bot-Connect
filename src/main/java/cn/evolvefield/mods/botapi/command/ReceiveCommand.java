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

public class ReceiveCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("receive")
                .then(Commands.argument("<all|chat|cmd>", StringArgumentType.greedyString())
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(ReceiveCommand::execute))
                )
                ;
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        String[] args = context.getInput().split("\\s+");
        String type = context.getArgument("<all|chat|cmd>", String.class);
        boolean isEnabled = context.getArgument("enabled", Boolean.class);
        switch (type){
            default:{
                        context.getSource().sendSuccess(
                new StringTextComponent("参数不合法"), true);
                break;
            }
            case "all":{
                BotApi.config.getCommon().setRECEIVE_ENABLED(isEnabled);
                ConfigManger.saveBotConfig(BotApi.config);
                if (isEnabled)
                {
                            context.getSource().sendSuccess(
                new StringTextComponent("接收所有群消息开关已被设置为打开"), true);
                }
                else
                {
                            context.getSource().sendSuccess(
                new StringTextComponent("接收所有群消息开关已被设置为关闭"), true);
                }
                break;
            }
            case "chat":{
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
                break;
            }
            case "cmd":{
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
                break;
            }
        }
        return 0;
    }
}
