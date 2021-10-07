package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.config.ModConfig;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class GroupIDCommand  {


    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("setID")
                .then(Commands.argument("QQGroupID", BoolArgumentType.bool())
                        .executes(ReceiveCommand::execute));
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        int id = context.getArgument("QQGroupID",Integer.class);
        ModConfig.GROUP_ID.set(id);
        context.getSource().sendSuccess(
                new StringTextComponent("已设置互通的群号为:" + id), true);
        return 0;
    }


}
