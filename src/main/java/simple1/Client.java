package simple1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 */
public class Client {
    //建立连接，生产
    public void produce(String msg) {

        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            pw.println(msg);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接，消费
     */
    public void consume() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("CONSUME");
            pw.flush();
            String msg = bf.readLine();
            System.out.println("客户端获取到的消息: " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择要进行的操作:");
            System.out.println("[1]发送消息");
            System.out.println("[2]获取消息");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    System.out.println("请输入要发送的内容: ");
                    scanner.nextLine();
                    String msg = scanner.nextLine();
                    client.produce(msg);
                    System.out.println("发送成功！" + msg);
                    break;
                case 2:
                    client.consume();
                    break;
                default:
                    System.out.println("输入的操作有误");
            }
        }
    }
}
