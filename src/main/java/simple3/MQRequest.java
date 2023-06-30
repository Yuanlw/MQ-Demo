package simple3;

import java.io.Serializable;

/**
 * Request
 *
 * @Author yuanlw
 **/
public class MQRequest implements Serializable {
    public static final int SUBSCRIBE = 1;   // 订阅
    public static final int UNSUBSCRIBE = 2; // 取消订阅
    public static final int SEND = 3;        // 发送消息
    public static final int PULL = 4;        // 拉取消息
    public static final int COMMIT = 5;      // 提交消息
    public static final int ROLLBACK = 6;    // 回滚消息

    private int type;                // 请求类型
    private String topic;           // 主题
    private String msgBody;         // 消息体
    private Message msg;            // 消息对象
    private String group;           // 消费组

    // getter/setter...

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public MQRequest(int type, String topic) {
        this.type = type;
        this.topic = topic;
    }

    public MQRequest() {
    }
}
