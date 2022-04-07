package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("connect")
                .then(Commands.literal("cqhttp")
                        .executes(ConnectCommand::cqhttpCommonExecute)
                        .then(Commands.argument("parameter", StringArgumentType.greedyString())
                                .executes(ConnectCommand::cqhttpExecute)
                        )
                )
                .then(Commands.literal("mirai")
                        .executes(ConnectCommand::miraiCommonExecute)
                        .then(Commands.argument("parameter", StringArgumentType.greedyString())
                                .executes(ConnectCommand::miraiExecute)
                        )
                );
    }

    public static int cqhttpExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String parameter = context.getArgument("parameter", String.class);


        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotData.setWs("ws://" + parameter);
            BotData.setBotFrame("cqhttp");
            context.getSource().sendSuccess(new StringTextComponent("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "cqhttp"), true);
            WebSocketService.main(BotData.getWs());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);

            return 1;

        } else {
            context.getSource().sendSuccess(new StringTextComponent(TextFormatting.RED + "参数错误❌"), true);
            return 0;
        }
    }

    public static int miraiExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String parameter = context.getArgument("parameter", String.class);

        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            BotData.setWs("ws://" + parameter);
            BotData.setBotFrame("mirai");
            context.getSource().sendSuccess(new StringTextComponent("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "mirai"), true);
            WebSocketService.main(BotData.getWs() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
            BotApi.config.getStatus().setRECEIVE_ENABLED(true);
            ConfigManger.saveBotConfig(BotApi.config);

            return 1;

        } else {
            context.getSource().sendSuccess(new StringTextComponent(TextFormatting.RED + "参数错误"), true);
            return 0;
        }
    }

    public static int cqhttpCommonExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {

        context.getSource().sendSuccess(new StringTextComponent("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "cqhttp"), true);
        WebSocketService.main(BotApi.config.getCommon().getWsCommon());
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);
        return 1;

    }

    public static int miraiCommonExecute(CommandContext<CommandSource> context) throws CommandSyntaxException {


        context.getSource().sendSuccess(new StringTextComponent("尝试链接框架" + TextFormatting.LIGHT_PURPLE + "mirai"), true);
        WebSocketService.main(BotApi.config.getMirai().getWsMirai() + "/all?verifyKey=" + BotData.getVerifyKey() + "&qq=" + BotData.getQQId());
        BotApi.config.getStatus().setRECEIVE_ENABLED(true);
        ConfigManger.saveBotConfig(BotApi.config);

        return 1;

    }
}
