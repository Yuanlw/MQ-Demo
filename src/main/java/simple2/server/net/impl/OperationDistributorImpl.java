package simple2.server.net.impl;

import simple2.Message;
import simple2.bean.OperateEnum;
import simple2.bean.Protocol;
import simple2.server.net.IOperationDistributor;
import simple2.server.queue.QueueMap;

/**
 * 分发处理实现类
 *
 * @Author yuanlw
 **/
public class OperationDistributorImpl implements IOperationDistributor {

    private static QueueMap queueMap = new QueueMap();

    @Override
    public Protocol handle(Protocol<Message> protocol) {
        String header = protocol.getHeader();
        OperateEnum operateEnum = OperateEnum.getEnum(header);
        Protocol reProtocol = null;
        switch (operateEnum) {
            case CAT:
                return handleCat(protocol);
            case DEQUEUE:
                return handleDequeue(protocol);
            case ENQUEUE:
                return handleEnqueue(protocol);
            default:
                break;
        }
        return reProtocol;
    }

    /**
     * 查看主题
     **/
    private Protocol handleCat(Protocol protocol) {
        Integer messageInTopic = queueMap.getMessageInTopic(protocol.getTopic());
        return Protocol.buildResponse(protocol.getTopic(), messageInTopic);
    }

    /**
     * 出队
     **/
    private Protocol handleDequeue(Protocol protocol) {
        Message consume = queueMap.consume(protocol.getTopic());
        return Protocol.buildResponse(protocol.getTopic(), consume);
    }

    /**
     * 入队
     **/
    private Protocol handleEnqueue(Protocol<Message> protocol) {
        queueMap.produce(protocol.getData(), protocol.getTopic());
        return Protocol.buildResponse(protocol.getTopic(), "200");
    }
}
