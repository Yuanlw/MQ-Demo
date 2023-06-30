package simple2.api.comsumer;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import simple2.Message;
import simple2.api.IConsumer;
import simple2.api.INetListener;
import simple2.bean.OperateEnum;
import simple2.bean.Protocol;
import simple2.client.Client;
import simple2.client.handle.ClientProtocolHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * consumer端
 *
 * @Author yuanlw
 **/
@Slf4j
public class ConsumerImpl implements IConsumer, INetListener {

    private Client client;

    private Channel channel;

    private String topic;

    private String pullRequest;

    /**
     * 线程同步
     */
    private volatile boolean pullAble = false;
    private Message latestMessage; // 保存最新的消息

    public ConsumerImpl(String topic) {
        log.info("【消费者 正在启动】：topic为:{}", topic);
        this.topic = topic;
        client = new Client((INetListener) this);
        //        client = new Client((IConsumer) this);

    }

    public ConsumerImpl() {
        client = new Client((IConsumer) this);
    }

    @Override
    public void onMessages(List<Message> messages) {
        for (Message message : messages) {
            log.info("【onMessages获取到数据：message:{}】", message);
            pullAble = true;
        }
    }

    @Override
    public void successChannel(Channel channel) {
        //        while (channel.isWritable()) {
        //            log.info("监听者topic:{} 可连接状态", topic);
        //            // 发送拉取消息信息
        //            //            if (pullAble) {
        //            channel.writeAndFlush(getPullProtocol());
        //            pullRequest = "";
        //            //                pullAble = false;
        //            //            }
        //            try {
        //                TimeUnit.SECONDS.sleep(1);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }
        //        while (channel.isWritable()) {
        //            channel.eventLoop().execute(() -> {
        //                channel.pipeline().addLast(topic, new ClientProtocolHandler(this));
        //                //            channel.subscribe(topic);
        //                channel.writeAndFlush(getPullProtocol());
        //            });
        this.channel = channel;
        channel.pipeline().addLast(topic, new ClientProtocolHandler(this));
        channel.pipeline().channel().eventLoop().scheduleAtFixedRate(() -> {
            log.info("监听者topic:{} 可连接状态", topic);
            channel.writeAndFlush(getPullProtocol());
        }, 0, 5, TimeUnit.SECONDS);

        //            try {
        //                TimeUnit.SECONDS.sleep(2);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }

    }

    private String getPullProtocol() {
        if (StrUtil.isBlank(pullRequest) || StrUtil.isEmpty(pullRequest)) {
            Protocol<Message> protocol = new Protocol<>();
            protocol.setTopic(this.topic);
            protocol.setHeader(OperateEnum.DEQUEUE.getValue());
            protocol.setData(null);
            pullRequest = JSONUtil.toJsonStr(protocol);
        }
        return pullRequest;
    }

    /**
     * 模拟消费端，pull消息
     *
     * @param args
     */
    public static void main(String[] args) {

        IConsumer simpleConsumer = new ConsumerImpl("11");
        Client client = new Client(simpleConsumer);
        //        new Client(new IConsumer() {
        //            @Override
        //            public void onMessages(List<Message> messages) {
        //
        //            }
        //
        //            @Override
        //            public void successChannel(Channel channel) {
        //                channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
        //                    @Override
        //                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //                        System.out.println("Received from server: " + msg);
        //                    }
        //                });
        //            }
        //        });

        //                new Client((IConsumer) new ConsumerImpl("11"));

    }
}
