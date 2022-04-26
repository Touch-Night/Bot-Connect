package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class GuildIDCommand {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setGuild")
                .then(Commands.argument("GuildID", LongArgumentType.longArg())
                        .executes(GuildIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSource> context) {
        String id = context.getArgument("GuildID", String.class);
        BotApi.config.getCommon().setGuildOn(true);
        BotApi.config.getCommon().setGuildId(id);
        BotData.setGuildId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置互通的频道号为:" + id), true);
        return 0;
    }


}
