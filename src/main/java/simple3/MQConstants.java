package simple3;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class MQConstants {
    public static final int NORMAL_MSG = 1;      // 普通消息
    public static final int DELAY_MSG = 2;       // 延迟消息
    public static final int TRANSACTION_MSG = 3; // 事务消息

    public static final String DELIVER_SUCCESS = "DELIVER_SUCCESS";    // 送达成功
    public static final String DELIVER_FAILURE = "DELIVER_FAILURE";    // 送达失败
    public static final String COMMIT_SUCCESS = "COMMIT_SUCCESS";      // 提交成功
    public static final String COMMIT_FAILURE = "COMMIT_FAILURE";      // 提交失败
    public static final String ROLLBACK_SUCCESS = "ROLLBACK_SUCCESS";  // 回滚成功
    public static final String ROLLBACK_FAILURE = "ROLLBACK_FAILURE";  // 回滚失败
}
