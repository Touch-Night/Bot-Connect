package cn.evolvefield.mods.botapi.event;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
public interface PlayerLoggedOutEvent {
    Event<PlayerLoggedOutEvent> EVENT = EventFactory.createArrayBacked(PlayerLoggedOutEvent.class,
            listeners -> (player) -> {
                for (PlayerLoggedOutEvent listener : listeners) {
                    ActionResult result = listener.onLoggedOutEvent(player);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onLoggedOutEvent(ServerPlayerEntity player);
}
