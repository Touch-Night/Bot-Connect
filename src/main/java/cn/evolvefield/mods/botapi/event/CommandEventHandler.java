package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.command.MainCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;


public class CommandEventHandler {
    public static void registerCommandRegisters() {
        // Commands
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, dedicated) -> {
                    MainCommand.register(dispatcher);
                }
        );
    }
}