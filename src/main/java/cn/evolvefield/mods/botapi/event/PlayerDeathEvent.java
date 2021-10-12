package cn.evolvefield.mods.botapi.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerDeathEvent {
    Event<PlayerDeathEvent> EVENT = EventFactory.createArrayBacked(PlayerDeathEvent.class,
            (listeners) -> (player, source) -> {
                for (PlayerDeathEvent l : listeners) {
                    l.trigger(player, source);
                }
    });

    void trigger(ServerPlayerEntity player, DamageSource source);
}
