package cn.evolvefield.mods.botapi.init.events;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PlayerEventHandler {


    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (BotApi.config.getCommon().isS_JOIN_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(), event.getPlayer().getDisplayName().getString() + " 加入了服务器");
        }
    }

    @SubscribeEvent
    public static void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        if (BotApi.config.getCommon().isS_LEAVE_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()){
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),event.getPlayer().getDisplayName().getString() + " 离开了服务器");
        }
    }

    @SubscribeEvent
    public static void playerDeadEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity && BotApi.config.getCommon().isS_DEATH_ENABLE() && BotApi.config.getCommon().isSEND_ENABLED()) {
            String message = event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),String.format(message, event.getEntity().getDisplayName().getString()));
        }
    }

    @SubscribeEvent
    public static void playerAdvancementEvent(AdvancementEvent event) {
        if ( BotApi.config.getCommon().isS_ADVANCE_ENABLE() && event.getAdvancement().getDisplay() != null && BotApi.config.getCommon().isSEND_ENABLED()) {
            String message = new TranslationTextComponent("chat.botapi.type.advancement." + event.getAdvancement().getDisplay().getFrame().getName(), event.getPlayer().getDisplayName(), event.getAdvancement().getChatComponent()).getString();
            SendMessage.Group(BotApi.config.getCommon().getGroupId(),message);
        }
    }
}
