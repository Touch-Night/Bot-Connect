package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class VerifyKeyCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setVerifyKey")
                .then(Commands.argument("VerifyKey", StringArgumentType.string())
                        .executes(VerifyKeyCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        String id = context.getArgument("VerifyKey", String.class);
        BotApi.config.getMirai().setVerifyKey(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置Mirai框架的VerifyKey为:" + id), true);
        return 0;
    }


}
