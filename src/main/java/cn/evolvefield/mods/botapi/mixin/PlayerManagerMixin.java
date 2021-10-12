package cn.evolvefield.mods.botapi.mixin;

import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {




    @Shadow @Nullable public  ServerPlayerEntity getPlayer(UUID uuid){
        return this.playerMap.get(uuid);
    }

    @Shadow @Final private Map<UUID, ServerPlayerEntity> playerMap;

    @Inject(at=@At("HEAD"),
            method="broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V",
            cancellable=false)
    private void broadcastChatMessage(Text text, MessageType mtype, UUID uuid, CallbackInfo info){
        if(BotConfig.INSTANCE.isSEND_ENABLED() && playerMap != null && !playerMap.isEmpty()){
            SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(), String.format("[MC]<%s> %s", getPlayer(uuid).getDisplayName().getString(),text.getString()));
        }
    }
}
