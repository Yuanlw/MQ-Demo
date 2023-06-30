package simple1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 */
public class Server implements Runnable {

    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            String info = bf.readLine();
            System.out.println("run " + info);
            if (info == null) return;
            if (info.equals("CONSUME")) {
                String msg = SimpleMQ.consume();
                if (msg == null) {
                    pw.println("获取消息失败: 消息队列已空");
                } else {
                    pw.println(msg);

                }
                pw.flush();
            } else {
                SimpleMQ.produce(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器启动，等待连接！");
        System.out.println("===========================================");
        // 为了保证服务器可以与多个客户端建立连接，即服务器一致处于等待连接的状态
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket);
            Server server = new Server(socket);
            // 使用不同的线程处理请求，提高响应速度

            new Thread(server).start();

        }
    }
}
