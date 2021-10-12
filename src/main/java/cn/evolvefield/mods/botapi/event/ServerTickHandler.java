package cn.evolvefield.mods.botapi.event;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class ServerTickHandler {
    public static final ServerTickHandler INSTANCE = new ServerTickHandler();

    private List<Runnable> tasklist = new ArrayList<>();
    private BlockingQueue<Runnable> taskqueue = new LinkedBlockingQueue<>();
    private ServerTickHandler(){}

    public void onTick(MinecraftServer server){
        tasklist.forEach(Runnable::run);
        Runnable task;
        do{
            if((task = taskqueue.poll()) == null){
                break;
            }
            task.run();
        }while(true);
    }

    public void newTickTask(Runnable task){
        taskqueue.offer(task);
    }
    public void addTickHandler(Runnable task){
        tasklist.add(task);
    }
}
