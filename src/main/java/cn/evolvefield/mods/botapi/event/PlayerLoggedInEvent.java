package cn.evolvefield.mods.botapi.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerLoggedInEvent {
    Event<PlayerLoggedInEvent> EVENT = EventFactory.createArrayBacked(PlayerLoggedInEvent.class,
            listeners -> (player) -> {
                for (PlayerLoggedInEvent listener : listeners) {
                    ActionResult result = listener.onLoggedInEvent(player);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onLoggedInEvent(ServerPlayerEntity player);
}
