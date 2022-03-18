package cn.evolvefield.mods.botapi.api.events;

import net.minecraftforge.eventbus.api.Event;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 18:44
 * Version: 1.0
 */
public class NoticeEvent extends Event {
    private final String Json;//原始文本
    private final String self_id;//机器人qq
    private final String sub_type;//事件子类型
    private final String notice_type;//通知类型
    private final String group_id;//事件触发群
    private final String user_id;//事件触发人QQ
    private final String operator_id;//操作者QQ
    private final String duration;//被禁言时长
    private final String message_id;//消息ID、被撤回的消息ID
    private final String file_name;//上传文件名字
    private final String file_size;//上传文件大小
    private final String target_id;//被戳、运气王QQ


    public NoticeEvent(String Json,String self_id,String sub_type,String notice_type,String group_id,String user_id,String operator_id,String duration,String message_id,String file_name,String file_size,String target_id) {
        this.Json = Json;
        this.self_id = self_id;
        this.sub_type = sub_type;
        this.notice_type = notice_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.operator_id = operator_id;
        this.duration = duration;
        this.message_id = message_id;
        this.file_name = file_name;
        this.file_size = file_size;
        this.target_id = target_id;
    }

    public String getGroup_id() {
        return group_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getJson() {
        return this.Json;
    }
    public String getSelfId() {
        return this.self_id;
    }
    public String getSubType() {
        return this.sub_type;
    }
    public String getNoticeType() {
        return this.notice_type;
    }
    public String getOperatorId() {
        return this.operator_id;
    }
    public String getDuration() {
        return this.duration;
    }
    public String getMessageId() {
        return this.message_id;
    }
    public String getFile_Name() {
        return this.file_name;
    }
    public String getFile_Size() {
        return this.file_size;
    }
    public String getTargetId() {
        return this.target_id;
    }
}
