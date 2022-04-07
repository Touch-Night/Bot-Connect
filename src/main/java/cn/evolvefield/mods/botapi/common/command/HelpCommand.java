package cn.evolvefield.mods.botapi.common.command;


import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class HelpCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("help").executes(HelpCommand::execute);
    }

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {

        String toSend =
                "\n群服互联使用说明:\n"
                        + "如果你是第一次使用请按照以下步骤设置\n"
                        + "1.请先开启机器人框架，go-cqhttp或者mirai\n"
                        + "2.请使用/mcbot setFrame <cqhttp/mirai> 设置框架\n"
                        + "3.请使用/mcbot setGroup <GroupId> 设置互通的群\n"
                        + "4.请使用/mcbot setBot <BotId> 设置机器人的qq号\n"
                        + "5.如果使用的是mirai，同时打开了VerifyKey验证，请输入/mcbot setVerifyKey <VerifyKey> 设置\n"
                        + "6.准备工作完成，请使用/mcbot connect <cqhttp/mirai> <host:port> 与框架对接\n"
                        + "在框架默认配置下，请使用/mcbot connect <cqhttp/mirai>\n"
                        + "*************************************\n"
                        + "全部命令：\n"
                        + "/mcbot connect <cqhttp/mirai> <host:port>\n"
                        + "/mcbot setFrame <cqhttp/mirai>\n"
                        + "/mcbot setGroup <GroupId>\n"
                        + "/mcbot setBot <BotId>\n"
                        + "/mcbot setVerifyKey <VerifyKey>\n"
                        + "/mcbot receive <all|chat|cmd> <true|false>\n"
                        + "/mcbot send <all|join|leave|death|achievements|welcome> <true|false>\n"
                        + "/mcbot help\n"
                        + "*************************************\n"
                        + "感谢您的支持，如有问题请联系我\n"
                        + "QQ群：720975019找群主\n"
                        + "Github：https://github.com/Nova-Committee/Bot-Connect/issues/new 提交问题\n";
        context.getSource().sendSuccess(new StringTextComponent(toSend), true);
        return 1;
    }
}
