package cn.evolvefield.mods.botapi.api.message;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/24 21:47
 * Version: 1.0
 */
public class ApiResult {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    private String status;

    private int retCode;

    private Object data;

    private String echo;
}
