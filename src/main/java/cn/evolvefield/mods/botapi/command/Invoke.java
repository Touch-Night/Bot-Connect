package cn.evolvefield.mods.botapi.command;




import cn.evolvefield.mods.botapi.Botapi;
import cn.evolvefield.mods.botapi.config.BotConfig;
import cn.evolvefield.mods.botapi.message.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class Invoke {


    public static void invokeCommand(String command) {
        String commandBody = command.substring(1);

//        if("tps".equals(commandBody)) {
//            double overTickTime = mean(Botapi.INSTANCE.getServer().getTicks()) * 1.0E-6D;
//            double overTPS = Math.min(1000.0 / overTickTime, 20);
//            double netherTickTime = mean(Botapi.INSTANCE.getServer().getTickTime(World.NETHER)) * 1.0E-6D;
//            double netherTPS = Math.min(1000.0 / netherTickTime, 20);
//            double endTickTime = mean(Botapi.INSTANCE.getServer().getTickTime(World.END)) * 1.0E-6D;
//            double endTPS = Math.min(1000.0 / endTickTime, 20);
//
//            String outPut = String.format("主世界 TPS: %.2f", overTPS)
//                    +"\n" + String.format("下界 TPS: %.2f", netherTPS)
//                    +"\n" + String.format("末地 TPS: %.2f", endTPS);
//            //Botapi.LOGGER.info(outPut);
//            SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(), outPut);
//        }
//
//        else
            if("list".equals(commandBody)) {
            List<ServerPlayerEntity> users = Botapi.INSTANCE.getServer().getPlayerManager().getPlayerList();

            String result = "在线玩家数量: " + users.size();

            if (users.size() > 0) {
                Text userList = users.stream()
                        .map(PlayerEntity::getDisplayName)
                        .reduce(new LiteralText(""), (listString, user) ->
                                listString.getString().length() == 0 ? user : new LiteralText(listString.getString() + ", " + user.getString())
                        );
                result += "\n" + "玩家列表: " + userList.getString();
            }
            //Botapi.LOGGER.info(result);
            SendMessage.Group(BotConfig.INSTANCE.getGROUP_ID(), result);
        }

    }

    private static long mean(long[] values) {
        long sum = Arrays.stream(values)
                .reduce(0L, (total, item) -> total + item);

        return sum / values.length;
    }

}
