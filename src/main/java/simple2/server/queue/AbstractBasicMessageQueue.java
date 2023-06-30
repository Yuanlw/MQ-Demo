package simple2.server.queue;

import simple2.Message;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public abstract class AbstractBasicMessageQueue {

    /**
     * 获取队列名称
     **/
    abstract String getTopic();

    /**
     * 消息入队
     **/
    abstract void enqueue(Message msg);

    /**
     * 消息出队
     **/
    abstract Message dequeue();

    /**
     * 进队前
     **/
    protected abstract void beforeEnQueue(Message msg);

    /**
     * 进队后
     **/
    protected abstract void afterEnQueue(Message msg);

    /**
     * 出队前
     **/
    protected abstract void beforeDeQueue(Message msg);

    /**
     * 出队后
     **/
    protected abstract void afterDeQueue(Message msg);
}
