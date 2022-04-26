package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotData;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

public class GuildIDCommand {


    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("setGuild")
                .then(Commands.argument("GuildID", StringArgumentType.greedyString())
                        .executes(GuildIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        var id = context.getArgument("GuildID", String.class);
        BotApi.config.getCommon().setGuildOn(true);
        BotApi.config.getCommon().setGuildId(id);
        BotData.setGuildId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new TextComponent("已设置互通的频道号为:" + id), true);
        return 0;
    }


}
