package simple3;

import java.util.*;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class Queue {
    private String topic;                       // 队列所属主题
    private String queueId;                     // 队列ID
    private int capacity;                       // 队列最大容量
    private static List<Message> msgList = new ArrayList<>();   // 消息列表
    private static Set<Consumer> consumers = new HashSet<>();   // 消费者集合

    public Queue(String topic) {
        this.topic = topic;
        this.queueId = UUID.randomUUID().toString();
        this.capacity = MQConfig.DEFAULT_QUEUE_CAPACITY;
    }

    public Queue() {
    }

    // 发送消息
    public void send(Message msg) {
        msg.setTopic(topic);
        if (msgList.size() >= capacity) {
            // 队列满,申请扩容或拒绝消息
        }
        msgList.add(msg);
    }

    public void subscribe(Consumer consumer) {
        consumers.add(consumer);   // 添加订阅消费者
    }

    // 消费消息
    public List<Message> poll(Consumer consumer) {
        List<Message> msgs = new ArrayList<>();
        int count = 0;
        for (Message msg : msgList) {
            if (!msg.isConsumed() && count < MQConfig.CONSUME_MSG_COUNT_ONCE) {
                msg.setConsumed(true);
                msg.setConsumerId(consumer.getConsumerId());
                msgs.add(msg);
                count++;
            }
        }
        return msgs;
    }

    public void pop(Consumer consumer) {
        // 消费并移除消息
        List<Message> result = new ArrayList<>();
        Iterator<Message> iterator = msgList.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if (!msg.isConsumed()) {
                result.add(msg);
                iterator.remove();  // 从队列中删除消息
            }
        }
        consumer.consume(topic,result);    // 调用消费者消费消息
    }

    public boolean contain(Message msg) {
        if (msgList.contains(msg)) {
            return true;
        }
        return false;
    }

    public void remove(Message msg) {
        msgList.remove(msg);  // 从队列中删除消息
    }

    // 获取消息队列
    public List<Message> getMsgList() {
        return msgList;
    }

    // 设置消息队列
    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }
}
