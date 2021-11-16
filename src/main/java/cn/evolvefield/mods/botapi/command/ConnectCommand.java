package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ConnectCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("connect")
                .executes(ConnectCommand::execute)
                .then(Commands.argument("arguments", StringArgumentType.greedyString())
                        .executes(ConnectCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        String[] args = context.getInput().split("\\s+");
        switch (args[0]){
            default:{
                context.getSource().sendSuccess(new StringTextComponent("参数不合法"), true);
                break;
            }
            case "send":
            {
                switch(args.length) {
                    default: {
                        context.getSource().sendSuccess(new StringTextComponent("参数不合法"), true);
                        break;
                    }
                    case 2: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if(matcher.find()) {
                            BotApi.config.getCommon().setSEND_ENABLED(true);
                            BotApi.config.getCommon().setSendHOST(matcher.group(1));
                            BotApi.config.getCommon().setSendPORT(Integer.parseInt(matcher.group(2)));
                            ConfigManger.saveBotConfig(BotApi.config);
                            context.getSource().sendSuccess(new StringTextComponent("已保存，正在尝试建立连接"), true);
                            ClientThreadService.runWebSocketClient();
                        } else {
                            context.getSource().sendSuccess(new StringTextComponent("格式错误"), true);
                        }
                        break;
                    }
                    case 1: {
                        context.getSource().sendSuccess(new StringTextComponent("尝试建立连接"), true);
                        ClientThreadService.runWebSocketClient();
                        break;
                    }
                }
                break;
            }
            case "receive":
            {
                switch(args.length) {
                    default: {
                        context.getSource().sendSuccess(new StringTextComponent("参数不合法"), true);
                        break;
                    }
                    case 3: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if (matcher.find()) {
                            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                            BotApi.config.getCommon().setWsHOST(matcher.group(1));
                            BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                            BotApi.config.getCommon().setKEY(args[2]);
                            ConfigManger.saveBotConfig(BotApi.config);
                            context.getSource().sendSuccess(new StringTextComponent("已保存，正在尝试建立连接"), true);
                            ClientThreadService.runWebSocketClient();
                        } else {
                            context.getSource().sendSuccess(new StringTextComponent("格式错误"), true);
                        }
                        break;
                    }
                    case 2: {
                        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                        Matcher matcher = pattern.matcher(args[1]);
                        if(matcher.find()) {
                            BotApi.config.getCommon().setRECEIVE_ENABLED(true);
                            BotApi.config.getCommon().setWsHOST(matcher.group(1));
                            BotApi.config.getCommon().setWsPORT(Integer.parseInt(matcher.group(2)));
                            ConfigManger.saveBotConfig(BotApi.config);
                            context.getSource().sendSuccess(new StringTextComponent("已保存，正在尝试建立连接"), true);
                            ClientThreadService.runWebSocketClient();
                        } else {
                            context.getSource().sendSuccess(new StringTextComponent("格式错误"), true);
                        }
                        break;
                    }
                    case 1: {
                        context.getSource().sendSuccess(new StringTextComponent("尝试建立连接"), true);
                        ClientThreadService.runWebSocketClient();
                        break;
                    }
                }
                break;
            }
        }
        BotApi.config.getCommon().setENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
}
