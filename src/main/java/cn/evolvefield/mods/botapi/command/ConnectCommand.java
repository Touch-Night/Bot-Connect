package cn.evolvefield.mods.botapi.command;


import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.service.ClientThreadService;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectCommand {

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandException {
        String[] args = context.getInput().split("\\s+");
        switch(args.length) {
            default: {
                context.getSource().sendFeedback(new LiteralText("参数不合法") {
                }, true);
                break;
            }
            case 4: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if (matcher.find()) {
                    BotConfig.INSTANCE.setWsHOST(matcher.group(1));
                    BotConfig.INSTANCE.setWsPORT(Integer.parseInt(matcher.group(2)));
                    BotConfig.INSTANCE.setKEY(args[3]);
                    context.getSource().sendFeedback(new LiteralText("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendFeedback(new LiteralText("格式错误"), true);
                }
                break;
            }
            case 3: {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
                Matcher matcher = pattern.matcher(args[2]);
                if(matcher.find()) {
                    BotConfig.INSTANCE.setWsHOST(matcher.group(1));
                    BotConfig.INSTANCE.setWsPORT(Integer.parseInt(matcher.group(2)));
                    context.getSource().sendFeedback(new LiteralText("已保存，正在尝试建立连接"), true);
                    ClientThreadService.runWebSocketClient();
                } else {
                    context.getSource().sendFeedback(new LiteralText("格式错误"), true);
                }
                break;
            }
            case 2: {
                context.getSource().sendFeedback(new LiteralText("尝试建立连接"), true);
                ClientThreadService.runWebSocketClient();
                break;
            }
        }
        BotConfig.INSTANCE.setIS_ENABLED(true);
        return 0;
    }
}
