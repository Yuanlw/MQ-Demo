package simple3;

import java.util.List;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class Consumer {
    private String group;            // 消费组
    private String consumerId;        // 消费者ID

    MQClient mqClient = new MQClient();

    public Consumer(String group, String consumerId) {
        this.group = group;
        this.consumerId = consumerId;
    }

    public Consumer(String group) {
        this.group = group;
    }

    public void consume(String topic, List<Message> msgs) {
        // 消息处理逻辑...
        for (Message msg : msgs) {
            if (msg.getMsgType() == MQConstants.TRANSACTION_MSG) {
                // 事务消息处理
                String result = commitOrRollback(msg);  // 提交或回滚消息
                mqClient.commitOrRollback(msg, result);
            } else {
                // 普通消息处理
            }
        }

        // 确认消息
        for (Message msg : msgs) {
            mqClient.ack(msg);
        }
    }

    private String commitOrRollback(Message msg) {
        // 这里根据业务逻辑判断是否提交还是回滚消息
        return MQConstants.COMMIT_SUCCESS;   // 返回提交或回滚结果
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getGroup() {
        return group;
    }
}


