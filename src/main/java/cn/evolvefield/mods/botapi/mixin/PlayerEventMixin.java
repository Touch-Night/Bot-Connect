package cn.evolvefield.mods.botapi.mixin;


import cn.evolvefield.mods.botapi.event.PlayerLoggedInEvent;
import cn.evolvefield.mods.botapi.event.PlayerLoggedOutEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerEventMixin {
    @Inject(at = @At("TAIL"), method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V")
    private void playerLoggedIn(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLoggedInEvent.EVENT.invoker().onLoggedInEvent(player);
    }

    @Inject(at = @At("HEAD"), method = "remove(Lnet/minecraft/server/network/ServerPlayerEntity;)V")
    private void playerLoggedOut(ServerPlayerEntity player,CallbackInfo info) {
        PlayerLoggedOutEvent.EVENT.invoker().onLoggedOutEvent(player);
    }
}
