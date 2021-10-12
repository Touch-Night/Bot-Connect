package cn.evolvefield.mods.botapi.event;

import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerHandlerEvent {
    public static void registerEnterWorld() {
        PlayerLoggedInEvent.EVENT.register((player) -> {
            if (BotConfig.INSTANCE.isSEND_ENABLED()){
                SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(), player.getDisplayName().getString() + " 加入了服务器");

            }
            return ActionResult.SUCCESS;
        });
    }

    public static void registerLeaveWorld() {
        PlayerLoggedOutEvent.EVENT.register((player) -> {
            if (BotConfig.INSTANCE.isSEND_ENABLED()){
                SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(),player.getDisplayName().getString() + " 离开了服务器");
            }
            return ActionResult.SUCCESS;
        });
    }

    public static void registerDeathWorld() {
        PlayerDeathEvent.EVENT.register((player, source) -> {
            if (player != null && BotConfig.INSTANCE.isSEND_ENABLED()) {
                String message = source.getDeathMessage(player).getString();
                SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(),String.format(message, player.getDisplayName().getString()));
            }
        });
    }

}
