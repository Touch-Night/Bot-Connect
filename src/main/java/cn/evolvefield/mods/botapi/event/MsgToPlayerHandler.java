package cn.evolvefield.mods.botapi.event;


import cn.evolvefield.mods.botapi.Botapi;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static cn.evolvefield.mods.botapi.Botapi.SERVER_UUID;

public class MsgToPlayerHandler {
    private static final Queue<String> toSendQueue = new LinkedList<>();;
    public static Queue<String> getToSendQueue() {
        return toSendQueue;
    }



    public static void broadcastMessage(){
        String toSend = toSendQueue.poll();
        if ( toSend != null) {
            Text textComponents = new LiteralText(toSend);
            Botapi.INSTANCE.getServer().getPlayerManager().broadcastChatMessage(textComponents,MessageType.CHAT,UUID.randomUUID());
        }
    }


    public void sendMessage(final GameProfile player, final String msg){

        for(ServerPlayerEntity p: Botapi.INSTANCE.getServer().getPlayerManager().getPlayerList()){
            tellPlayer(p, msg, player.getId());
        }
    }

    public static void tellPlayer(final ServerPlayerEntity player, final String message){
        tellPlayer(player, message, SERVER_UUID);
    }

    public static void tellPlayer(final ServerPlayerEntity player, final String message, final UUID uuid){
        player.sendMessage(createText(message), MessageType.byId((byte)(0)), uuid);
    }

    public static Text createText(final String text){
        return new LiteralText(text);
    }

}