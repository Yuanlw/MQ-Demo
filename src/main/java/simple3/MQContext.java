package simple3;

/**
 * 上下文
 *
 * @Author yuanlw
 **/
public class MQContext {
    private SimpleMQ mq;

    public MQContext() {
        this.mq = new SimpleMQ();
    }

    public SimpleMQ getMq() {
        return mq;
    }

    public void setMq(SimpleMQ mq) {
        this.mq = mq;
    }
}
