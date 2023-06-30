package simple3;

import java.io.Serializable;
import java.util.UUID;

/**
 * 消息体
 *
 * @Author yuanlw
 **/
public class Message implements Serializable {
    private String msgId;        // 消息ID
    private String msgBody;      // 消息体
    private String topic;        // 消息主题
    private int msgType;         // 消息类型
    private long bornTime;       // 消息产生时间
    private long storeTime;      // 消息存储时间
    private boolean consumed;    // 是否被消费
    private String consumerId;   // 消费者ID

    // getter/setter ...

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    public long getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(long storeTime) {
        this.storeTime = storeTime;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Message() {
    }

    public Message(String topic, String msgBody) {
        this.msgId = UUID.randomUUID().toString();
        this.topic = topic;
        this.msgBody = msgBody;
        this.bornTime = System.currentTimeMillis();
        this.storeTime = this.bornTime;
        this.msgType = MQConstants.NORMAL_MSG;
    }
}


