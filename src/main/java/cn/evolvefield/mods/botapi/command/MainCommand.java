package cn.evolvefield.mods.botapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class MainCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("mcbot")
                        .then(
                                CommandManager
                                        .literal("connect").executes(
                                        ConnectCommand::execute
                                )
                                        .then(CommandManager.argument("arguments", StringArgumentType.greedyString())
                                                .executes(ConnectCommand::execute))
                        )
                        .then(
                                CommandManager
                                        .literal("disconnect").executes(
                                        DisconnectCommand::execute
                                )
                        )
                        .then(
                                CommandManager
                                        .literal("setID").executes(
                                        GroupIDCommand::execute
                                )
                                        .then(CommandManager.argument("QQGroupID", IntegerArgumentType.integer())
                                                .executes(ReceiveCommand::execute))
                        )
                        .then(
                                CommandManager
                                        .literal("receive").executes(
                                        ReceiveCommand::execute
                                )
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::execute))
                        )
                        .then(
                                CommandManager
                                        .literal("send").executes(
                                        ConnectCommand::execute
                                )
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(ReceiveCommand::execute))
                        )
                        .then(
                                CommandManager
                                        .literal("status").executes(
                                        ConnectCommand::execute
                                )
                        )
        );
    }
}
