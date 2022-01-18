package cn.evolvefield.mods.botapi.common.command;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class DisconnectCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("disconnect").executes(DisconnectCommand::execute);
    }
    public static int execute(CommandContext<CommandSource> context) throws CommandException {
        boolean isSuccess = ClientThreadService.stopWebSocketClient();
        if (isSuccess) {
            context.getSource().sendSuccess(new StringTextComponent("WebSocket已断开连接"), true);
        } else {
            context.getSource().sendSuccess(new StringTextComponent("WebSocket目前未连接"), true);
        }
        BotApi.config.getCommon().setEnable(false);
        ConfigManger.saveBotConfig(BotApi.config);
        return 0;
    }
}
