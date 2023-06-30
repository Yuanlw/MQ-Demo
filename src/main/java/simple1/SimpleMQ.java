package simple1;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 存储、协调类 进队出队
 */
public class SimpleMQ {
    private static final Integer maxNum = 3;
    private static final ArrayBlockingQueue<String> mq = new ArrayBlockingQueue<>(maxNum);

    /**
     * 生产
     *
     * @param msg
     */
    public static void produce(String msg) {
        if (mq.offer(msg)) {
            System.out.println("[" + msg + "]" + "放入消息队列成功");
        } else {
            System.out.println("[" + msg + "]" + "放入消息队列失败: 消息队列已满");
        }
        System.out.println("===========================================");
    }

    /**
     * 消费
     *
     * @return
     */
    public static String consume() {
        String msg = mq.poll();
        if (msg == null) {
            System.out.println("获取消息失败: 消息队列已空");
        } else {
            System.out.println("获取消息成功: " + msg);
        }
        System.out.println("===========================================");
        return msg;
    }
}
