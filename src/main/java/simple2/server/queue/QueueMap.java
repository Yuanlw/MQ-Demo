package simple2.server.queue;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import simple2.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author yuanlw
 **/
@Slf4j
public class QueueMap {
    /**
     * topic:队列
     */
    private final Map<String, ArrayMessageQueue> map = new HashMap<>();

    private List<String> topics = new ArrayList<>();

    /**
     * 生产
     *
     * @param message: 消息
     * @param topic:   生产主题
     * @return void
     **/
    public synchronized void produce(Message message, String topic) {
        ArrayMessageQueue arrayMessageQueue = map.get(topic);
        if (ObjectUtil.isNotNull(message)) {
            if (ObjectUtil.isNotNull(arrayMessageQueue)) {
                arrayMessageQueue.enqueue(message);
            } else {
                arrayMessageQueue = new ArrayMessageQueue(topic);
                arrayMessageQueue.enqueue(message);
                map.put(topic, arrayMessageQueue);
                topics.add(topic);
            }
        } else {
            log.error("【入队失败， 消息为空】：{}", message);
        }

    }

    /**
     * 消费
     *
     * @param topic: 消费主题
     **/
    public synchronized Message consume(String topic) {
        ArrayMessageQueue arrayMessageQueue = map.get(topic);
        if (ObjectUtil.isNotNull(arrayMessageQueue) && arrayMessageQueue.getQueueSize() > 0) {
            return arrayMessageQueue.dequeue();
        } else {
            log.error("[出队失败，topic中无消息]：{}", topic);
            return null;
        }
    }

    /**
     * 获取topics
     **/
    public synchronized List<String> getTopics() {
        return topics;
    }

    /**
     * 获取topic下的消息数量
     *
     * @param topic 主题
     * @return java.lang.Integer
     **/
    public synchronized Integer getMessageInTopic(String topic) {
        ArrayMessageQueue arrayMessageQueue = map.get(topic);
        if (ObjectUtil.isNotNull(arrayMessageQueue)) {
            return arrayMessageQueue.getQueueSize();
        } else {
            log.error("【QueueMap 获取队列失败】：topic:{} 不存在。", topic);
            return 0;
        }
    }

}
