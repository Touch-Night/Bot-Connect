package cn.evolvefield.mods.botapi.config;

import cn.evolvefield.mods.botapi.Botapi;
import cn.evolvefield.mods.botapi.util.JsonUtil;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BotConfig {
    public static final BotConfig INSTANCE = new BotConfig();

    static class DataType{
        @SerializedName("groupId")
        private long GROUP_ID = 0;
        @SerializedName("wsHost")
        private String wsHOST = "127.0.0.1";
        @SerializedName("wsPort")
        private int wsPORT = 6700;
        @SerializedName("sendHost")
        private String sendHOST = "127.0.0.1";
        @SerializedName("sendPort")
        private int sendPORT = 5700;
        @SerializedName("enabled")
        private boolean IS_ENABLED = true;
        @SerializedName("key")
        private String KEY = "";
        @SerializedName("receive_enabled")
        private boolean RECEIVE_ENABLED = true;
        @SerializedName("send_enabled")
        private boolean SEND_ENABLED = true;


        public DataType(){}

        public long getGROUP_ID() {
            return GROUP_ID;
        }

        public void setGROUP_ID(long GROUP_ID) {
            this.GROUP_ID = GROUP_ID;
        }

        public String getWsHOST() {
            return wsHOST;
        }

        public void setWsHOST(String wsHOST) {
            this.wsHOST = wsHOST;
        }

        public int getWsPORT() {
            return wsPORT;
        }

        public void setWsPORT(int wsPORT) {
            this.wsPORT = wsPORT;
        }

        public String getSendHOST() {
            return sendHOST;
        }

        public void setSendHOST(String sendHOST) {
            this.sendHOST = sendHOST;
        }

        public int getSendPORT() {
            return sendPORT;
        }

        public void setSendPORT(int sendPORT) {
            this.sendPORT = sendPORT;
        }

        public boolean isSEND_ENABLED() {
            return SEND_ENABLED;
        }

        public void setSEND_ENABLED(boolean SEND_ENABLED) {
            this.SEND_ENABLED = SEND_ENABLED;
        }

        public String getKEY() {
            return KEY;
        }

        public void setKEY(String KEY) {
            this.KEY = KEY;
        }

        public boolean isRECEIVE_ENABLED() {
            return RECEIVE_ENABLED;
        }

        public void setRECEIVE_ENABLED(boolean RECEIVE_ENABLED) {
            this.RECEIVE_ENABLED = RECEIVE_ENABLED;
        }

        public boolean isIS_ENABLED() {
            return IS_ENABLED;
        }

        public void setIS_ENABLED(boolean IS_ENABLED) {
            this.IS_ENABLED = IS_ENABLED;
        }
    }

    private File file;
    private DataType data;
    private BotConfig(){
        this.file = new File(Botapi.INSTANCE.getDataFolder(), "botapi.json");
        this.data = new DataType();
    }

    public long getGROUP_ID() {
        return data.GROUP_ID;
    }

    public void setGROUP_ID(long GROUP_ID) {
        this.data.GROUP_ID = GROUP_ID;
    }

    public String getWsHOST() {
        return data.wsHOST;
    }

    public void setWsHOST(String wsHOST) {
        this.data.wsHOST = wsHOST;
    }

    public int getWsPORT() {
        return data.wsPORT;
    }

    public void setWsPORT(int wsPORT) {
        this.data.wsPORT = wsPORT;
    }

    public String getSendHOST() {
        return data.sendHOST;
    }

    public void setSendHOST(String sendHOST) {
        this.data.sendHOST = sendHOST;
    }

    public int getSendPORT() {
        return data.sendPORT;
    }

    public void setSendPORT(int sendPORT) {
        this.data.sendPORT = sendPORT;
    }

    public boolean isSEND_ENABLED() {
        return data.SEND_ENABLED;
    }

    public void setSEND_ENABLED(boolean SEND_ENABLED) {
        this.data.SEND_ENABLED = SEND_ENABLED;
    }

    public String getKEY() {
        return data.KEY;
    }

    public void setKEY(String KEY) {
        this.data.KEY = KEY;
    }

    public boolean isRECEIVE_ENABLED() {
        return data.RECEIVE_ENABLED;
    }

    public void setRECEIVE_ENABLED(boolean RECEIVE_ENABLED) {
        this.data.RECEIVE_ENABLED = RECEIVE_ENABLED;
    }

    public boolean isIS_ENABLED() {
        return data.IS_ENABLED;
    }

    public void setIS_ENABLED(boolean IS_ENABLED) {
        this.data.IS_ENABLED = IS_ENABLED;
    }


    public void reload(){
        if(!this.file.exists()){
            return;
        }
        try(
                FileReader filer = new FileReader(file);
                JsonReader jreader = new JsonReader(filer)
        ){
            this.data = JsonUtil.GSON.fromJson(jreader, DataType.class);
        }catch(IOException e){
            Botapi.LOGGER.error("load bot config file error:\n", e);
        }
    }

    public void save(){
        if(!this.file.exists()){
            try{
                File dir = this.file.getParentFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }
                this.file.createNewFile();
            }catch(IOException e){
                Botapi.LOGGER.error("Create bot config file error:\n", e);
            }
        }
        try(
                FileWriter filew = new FileWriter(file);
                JsonWriter jwriter = new JsonWriter(filew)
        ){
            JsonUtil.GSON.toJson(this.data, DataType.class, jwriter);
        }catch(IOException e){
            Botapi.LOGGER.error("save bot config file error:\n", e);
        }
    }
}
