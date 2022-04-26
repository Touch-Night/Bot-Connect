package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class RemoveChannelIDCommand {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("delChannelId")
                .then(Commands.argument("ChannelID", LongArgumentType.longArg())
                        .executes(RemoveChannelIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) {
        String id = context.getArgument("ChannelID", String.class);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            BotApi.config.getCommon().removeChannelId(id);

        } else {
            context.getSource().sendSuccess(new StringTextComponent("子频道号:" + id + "并未出现！"), true);

        }
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }


}
