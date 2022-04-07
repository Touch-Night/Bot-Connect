package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class GroupIDCommand {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setID")
                .then(Commands.argument("QQGroupID", LongArgumentType.longArg())
                        .executes(GroupIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) {
        long id = context.getArgument("QQGroupID", Long.class);
        BotApi.config.getCommon().setGroupId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置互通的群号为:" + id), true);
        return 0;
    }


}
