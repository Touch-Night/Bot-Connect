package cn.evolvefield.mods.botapi.common.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.commands.Commands.literal;

public class RemoveChannelIDCommand {


    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return literal("delChannelId")
                .then(Commands.argument("ChannelID", StringArgumentType.greedyString())
                        .executes(RemoveChannelIDCommand::execute));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        var id = context.getArgument("ChannelID", String.class);
        if (BotApi.config.getCommon().getChannelIdList().contains(id)) {
            BotApi.config.getCommon().removeChannelId(id);

        } else {
            context.getSource().sendSuccess(new TextComponent("子频道号:" + id + "并未出现！"), true);

        }
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }


}
