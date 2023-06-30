package simple3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class MQServer {
    private SimpleMQ mq = new SimpleMQ();

    public void start() {
        // 启动时加载消息存储文件,恢复消息队列状态
        //        loadMessageStorage();

        // 启动消息接收线程
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8000);

                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(new MQHandler(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
