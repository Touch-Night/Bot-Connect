package cn.evolvefield.mods.botapi.core.network.WebSocket;

import cn.evolvefield.mods.botapi.util.id.SnowFlakeIdGenerator;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/24 21:39
 * Version: 1.0
 */
public abstract class BaseApi {
    public abstract String getAction();

    public abstract Object getParams();

    public boolean needSleep() {
        return true;
    }

    private String echo = null;

    public String getEcho() {
        if (StringUtils.isEmpty(echo)) {
            SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator();
            this.echo = idGenerator.createStrId();
        }
        return this.echo;
    }

    public String buildJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("action", this.getAction());
        map.put("params", this.getParams());
        map.put("echo", this.getEcho());
        return  JSONObject.fromObject(map).toString();
    }
}
