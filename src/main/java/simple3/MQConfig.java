package simple3;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class MQConfig {
    public static final int DEFAULT_QUEUE_CAPACITY = 1000;   // 默认队列容量
    public static final int CONSUME_MSG_COUNT_ONCE = 10;     // 一次消费消息数

    public static final String STORE_PATH = "/tmp/mq/";      // 消息存储路径
    public static final int DELETE_PERIOD = 7;               // 消息删除周期(天)
    public static final int CLEAN_PERIOD = 1;                // 消息清理周期(天)

    public static final int SCHEDULE_PERIOD = 60;            // 延迟消息调度周期(秒)
    public static final int HEARTBEAT_PERIOD = 30;           // 服务端心跳周期(秒)
    public static final int CLIENT_HeartBEAT_TIMEOUT = 90;   // 客户端心跳超时时间(秒)
}
