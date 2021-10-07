package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.command.*;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandEventHandler {
    public static void onCommandRegister(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("mcbot")
                        .requires(commandSource -> commandSource.hasPermission(2))
                        .then(ConnectCommand.register())
                        .then(DisconnectCommand.register())
                        .then(ReceiveCommand.register())
                        .then(SendCommand.register())
                        .then(StatusCommand.register())
        );
    }
}