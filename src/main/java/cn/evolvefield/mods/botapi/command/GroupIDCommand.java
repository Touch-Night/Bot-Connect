package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.config.ConfigManger;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import static net.minecraft.command.Commands.literal;

public class GroupIDCommand  {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return literal("setID")
                .then(Commands.argument("QQGroupID", IntegerArgumentType.integer())
                        .executes(GroupIDCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        int id = context.getArgument("QQGroupID",Integer.class);
        BotApi.config.getCommon().setGroupId(id);
        ConfigManger.saveBotConfig(BotApi.config);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置互通的群号为:" + id), true);
        return 0;
    }


}
