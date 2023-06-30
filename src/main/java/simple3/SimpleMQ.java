package simple3;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 核心功能
 *
 * @Author yuanlw
 **/
public class SimpleMQ {
    private Map<String, Queue> topicQueueMap = new HashMap<>();   // 主题与队列映射
    private Map<String, Set<Consumer>> groupConsumerMap = new HashMap<>(); // 消费组与消费者映射

    // 发送消息
    public void send(String topic, String msgBody) {
        Queue queue = topicQueueMap.getOrDefault(topic, null);
        if (queue == null) {
            queue = new Queue(topic);
            topicQueueMap.put(topic, queue);
        }
        Message msg = new Message();
        msg.setTopic(topic);
        msg.setMsgBody(msgBody);
        queue.send(msg);
    }
    // 消费消息
    public List<Message> poll(String topic, String group) throws InterruptedException {
        return poll(topic, group, false);
    }
    // 消费消息
    public List<Message> poll(String topic, String group, boolean blocking) throws InterruptedException {
        Set<Consumer> consumers = groupConsumerMap.getOrDefault(group, new HashSet<>());
        Iterator<Consumer> iterator = consumers.iterator();
        while (iterator.hasNext()) {
            Consumer consumer = iterator.next();
//            List<Message> msgs = consumer.poll();
//            if (!CollectionUtils.isEmpty(msgs)) {
//                return msgs;
//            }
        }
        if (blocking) {
            // 长轮询,直到有消息或超时
            synchronized (this) {
                wait(30);
            }
            return poll(topic, group);
        }
        return null;
    }
    // 提交或回滚事务消息
    public String commitOrRollback(Message msg) {
//        if (msg.isCommitted()) {
//            return commit(msg);
//        } else {
//            return rollback(msg);
//        }
        return null;
    }


    // 订阅消息
    public void subscribe(String topic, String group, BiConsumer<String, List<Message>> consumer) {
        Set<Consumer> consumers = groupConsumerMap.getOrDefault(group, new HashSet<>());
        Consumer c = new Consumer(group);
        consumers.add(c);
        groupConsumerMap.put(group, consumers);

        Queue queue = topicQueueMap.get(topic);
        if (queue == null) {
            queue = new Queue(topic);
            topicQueueMap.put(topic, queue);
        }
        queue.subscribe(c);   // 订阅消费者

        Queue finalQueue = queue;
        new Thread(() -> {
            while (true) {
                try {
                    List<Message> msgs = finalQueue.poll(c);
                    if (!CollectionUtils.isEmpty(msgs)) {
                        consumer.accept(topic, msgs);
                    } else {
                        Thread.sleep(30);  // 空轮询
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // 事务消息回查
    public String commit(Message msg) {
        if (msg.getMsgType() != MQConstants.TRANSACTION_MSG) {
            return MQConstants.COMMIT_FAILURE;
        }
        msg.setConsumed(true);
        return MQConstants.COMMIT_SUCCESS;
    }

    // 事务消息回滚
    public String rollback(Message msg) {
        if (msg.getMsgType() != MQConstants.TRANSACTION_MSG) {
            return MQConstants.ROLLBACK_FAILURE;
        }
        // 从消息存储中删除该消息
        return MQConstants.ROLLBACK_SUCCESS;
    }



    // 延迟消息调度
    public void schedule() {
        // 定时从消息存储中加载延迟消息,如果达到延迟时间,则投递到相应的队列中
    }

}
