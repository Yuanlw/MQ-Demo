package simple3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * client
 *
 * @Author yuanlw
 **/
public class MQClient {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public void start() {
        try {
            socket = new Socket("127.0.0.1", 8000);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic, String group, Consumer consumer) {
        MQRequest request = new MQRequest();
        request.setType(MQRequest.SUBSCRIBE);
        request.setTopic(topic);
        request.setGroup(group);
        try {
            oos.writeObject(request);

            new Thread(() -> {
                while (true) {
                    try {
                        MQResponse response = (MQResponse)ois.readObject();
                        if (response.getType() == MQResponse.DELIVER) {
                            consumer.consume(topic, response.getMsgs());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String topic, String msg) {
        MQRequest request = new MQRequest();
        request.setType(MQRequest.SEND);
        request.setTopic(topic);
        request.setMsgBody(msg);
        try {
            oos.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Message> pull(String topic, String group) {
        MQRequest request = new MQRequest();
        request.setType(MQRequest.PULL);
        request.setTopic(topic);
        request.setGroup(group);
        try {
            oos.writeObject(request);
            MQResponse response = (MQResponse) ois.readObject();
            if (response.getType() == MQResponse.DELIVER) {
                return response.getMsgs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void commitOrRollback(Message msg, String result) {
        MQRequest request = new MQRequest();
        request.setMsg(msg);   // 设置事务消息
        if (MQConstants.COMMIT_SUCCESS.equals(result)) {
            request.setType(MQRequest.COMMIT);     // 设置请求类型为COMMIT
        } else {
            request.setType(MQRequest.ROLLBACK);  // 设置请求类型为ROLLBACK
        }
        try {
            oos.writeObject(request);      // 发送请求
            MQResponse response = (MQResponse)ois.readObject();
            if (response.getType() == MQResponse.COMMIT_RESULT
                      || response.getType() == MQResponse.ROLLBACK_RESULT) {
                String r = response.getResult();
                if (!MQConstants.COMMIT_SUCCESS.equals(r)
                          && !MQConstants.ROLLBACK_SUCCESS.equals(r)) {
                    // 事务操作失败,进行重试或补偿
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ack(Message msg) {
        // 这里首先需要根据消息存储方式构造确认消息的请求
        // 然后发送请求进行确认,如果失败需要进行重试
    }
}
