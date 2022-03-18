package cn.evolvefield.mods.botapi.core.network.WebSocket;


import cn.evolvefield.mods.botapi.BotApi;
import cn.evolvefield.mods.botapi.api.message.ApiResult;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebSocketChannelSupervise {
    private static final ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();
    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(),channel.id());
    }
    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
    }
    public static Channel findChannel(String id) {
        return GlobalGroup.find(ChannelMap.get(id));
    }

    public static void sendToAll(TextWebSocketFrame tws) {
        GlobalGroup.writeAndFlush(tws);
    }

    public static void sendToChannel(String channelId ,TextWebSocketFrame tws) {
        GlobalGroup.find(findChannel(channelId).id()).writeAndFlush(tws);
    }

    public static void sendToModChannel(TextWebSocketFrame tws) {
        Map.Entry<String,ChannelId> entry = ChannelMap.entrySet().iterator().next();
        String key= entry.getKey();
        sendToChannel(key, tws);
    }


    private final Lock lock = new ReentrantLock();
    protected final Map<String, CompletableFuture<ApiResult>> completableFutureMap = new ConcurrentHashMap<>();

    private long lastInvokeTime;

    public ApiResult invokeApi(BaseApi baseApi) {
        this.lock.lock();
        try {
            if (baseApi.needSleep() && System.currentTimeMillis() - lastInvokeTime < 3000) {
                try {
                    Thread.sleep(System.currentTimeMillis() - lastInvokeTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(String.format("调用api出错: %s",  e.getMessage()));
                }
                GlobalGroup.writeAndFlush(new TextWebSocketFrame(baseApi.buildJson()));
                lastInvokeTime = System.currentTimeMillis();
            } else {
                GlobalGroup.writeAndFlush(new TextWebSocketFrame(baseApi.buildJson()));
            }
            CompletableFuture<ApiResult> completableFuture = new CompletableFuture<>();
            completableFutureMap.put(baseApi.getEcho(), completableFuture);
            ApiResult apiResult = getApiResult(baseApi.getEcho());
            if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
                throw new RuntimeException(String.format("调用api出错: %s",  apiResult));
            }
            return apiResult;
        } finally {
            this.lock.unlock();
        }
    }

    private ApiResult getApiResult(String echo) {
        CompletableFuture<ApiResult> completableFuture = completableFutureMap.get(echo);
        if (completableFuture == null) {
            return null;
        }
        try {
            ApiResult apiResult = completableFuture.get(1, TimeUnit.MINUTES);
            completableFutureMap.remove(echo);
            return apiResult;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            BotApi.LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
