package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class AddChannelIDCommand {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("addChannelId")
                .then(Commands.argument("ChannelID", LongArgumentType.longArg())
                        .executes(AddChannelIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) {
        String id = context.getArgument("ChannelID", String.class);
        BotApi.config.getCommon().setGuildOn(true);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            context.getSource().sendSuccess(new StringTextComponent("子频道号:" + id + "已经出现了！"), true);
        } else {
            BotApi.config.getCommon().addChannelId(id);
        }
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }


}
