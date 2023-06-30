package simple3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 核心执行类
 *
 * @Author yuanlw
 **/
public class MQHandler implements Runnable , BiConsumer<String, List<Message>> {
    private Socket socket;
//    private MQContext context;    // MQ上下文
    private SimpleMQ mq;

    public MQHandler(Socket socket) {
        this.socket = socket;
        this.mq = new SimpleMQ();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            MQRequest request = (MQRequest)ois.readObject();

            if (request.getType() == MQRequest.SUBSCRIBE) {
                // 订阅消息
                mq.subscribe(request.getTopic(), request.getGroup(), this);
            } else if (request.getType() == MQRequest.SEND) {
                // 发送消息
                mq.send(request.getTopic(), request.getMsgBody());
            } else if (request.getType() == MQRequest.PULL) {
                // 拉取消息
//                List<Message> msgs = mq.poll(request.getTopic(), request.getGroup(),this);
                MQResponse response = new MQResponse();
                response.setType(MQResponse.DELIVER);
//                response.setMsgs(msgs);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(response);
            } else if (request.getType() == MQRequest.COMMIT) {
                // 提交事务消息
                String result = mq.commit(request.getMsg());
                MQResponse response = new MQResponse();
                response.setType(MQResponse.COMMIT_RESULT);
                response.setResult(result);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(response);
            } else if (request.getType() == MQRequest.ROLLBACK) {
                // 回滚事务消息
                String result = mq.rollback(request.getMsg());
                MQResponse response = new MQResponse();
                response.setType(MQResponse.ROLLBACK_RESULT);
                response.setResult(result);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void accept(String s, List<Message> msgs) {
        try {
            for (Message msg : msgs) {
                // 消费消息
                if (msg.getMsgType()==MQConstants.TRANSACTION_MSG) {
                    // 事务消息处理
                    String result = mq.commitOrRollback(msg);
                    MQResponse response = new MQResponse();
                    response.setType(result.equals(MQConstants.COMMIT_SUCCESS)
                                               ? MQResponse.COMMIT_RESULT
                                               : MQResponse.ROLLBACK_RESULT);
                    response.setResult(result);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(response);
                } else {
                    // 普通消息处理
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msg.getMsgBody());
                }
            }
            MQResponse response = new MQResponse();
//            response.setType(MQResponse.CONSUME_SUCCESS);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(response);   // 确认消费
        } catch (Exception e) {
            MQResponse response = new MQResponse();
//            response.setType(MQResponse.CONSUME_FAIL);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public BiConsumer<String, List<Message>> andThen(BiConsumer<? super String, ? super List<Message>> after) {
        return null;
    }
}
