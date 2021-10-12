package cn.evolvefield.mods.botapi.command;

import cn.evolvefield.mods.botapi.config.BotConfig;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class GroupIDCommand  {


    public static int execute(CommandContext<ServerCommandSource> context) throws CommandException {
        int id = context.getArgument("QQGroupID",Integer.class);
        BotConfig.INSTANCE.setGROUP_ID(id);
        context.getSource().sendFeedback(
                new LiteralText("已设置互通的群号为:" + id), true);
        return 0;
    }


}
