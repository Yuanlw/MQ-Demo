package simple2.api.provider;

import io.netty.channel.Channel;
import simple2.Message;
import simple2.api.INetListener;
import simple2.api.IProvider;
import simple2.bean.OperateEnum;
import simple2.bean.Protocol;
import simple2.client.Client;

import java.util.Scanner;

/**
 * provider端
 *
 * @Author yuanlw
 **/
public class ProviderImpl implements IProvider, INetListener {

    @Override
    public void send(Message message) {

    }

    @Override
    public void successChannel(Channel channel) {

    }

    /**
     * 模拟provider 生成消息
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(new ProviderImpl() {
            @Override
            public void successChannel(Channel channel) {

                String s = "";
                Scanner sc = new Scanner(System.in);
                System.out.println("是否继续：Y|N");
                while ((s = sc.nextLine()).equals("Y") && channel.isWritable()) {
                    Protocol<Message> protocol = new Protocol<>();
                    System.out.println("\n操作：cat | enqueue ");
                    OperateEnum anEnum = OperateEnum.getEnum(sc.nextLine());
                    protocol.setHeader(anEnum.getValue());
                    System.out.println("消息topic：");
                    protocol.setTopic(sc.nextLine());
                    if (anEnum == OperateEnum.ENQUEUE) {
                        System.out.println("消息内容：");
                        Message message = new Message(sc.nextLine());
                        protocol.setData(message);
                    }
                    //                    System.out.println("protocol:" + protocol);
                    channel.writeAndFlush(protocol);
                    //                    channel.read();
                    System.out.println("是否继续：Y|N");
                }
                System.out.println("退出");
            }
        });

    }
}
