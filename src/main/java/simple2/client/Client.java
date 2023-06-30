package simple2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import simple2.api.IConsumer;
import simple2.api.INetListener;
import simple2.client.handle.ClientProtocolHandler;
import simple2.client.handle.ProtocolDecoder;
import simple2.client.handle.ProtocolEncoder;

import java.util.concurrent.TimeUnit;

/**
 * Client工具
 *
 * @Author yuanlw
 **/
@Slf4j
public class Client {

    /**
     * 获取服务端IP和Port
     */
    static String addr = "127.0.0.1";

    static Integer port = 9999;

    private Channel channel;

    public Client(IConsumer consumer) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        client.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringEncoder()).addLast(new ProtocolEncoder()).addLast(new StringDecoder()).addLast(new ProtocolDecoder()).addLast(new ClientProtocolHandler(consumer));
            }
        });
        try {
            ChannelFuture sync = client.connect(addr, port).sync();
            sync.addListener((ChannelFutureListener) future -> {
                if (sync.isSuccess()) {
                    System.out.println("【Netty 客户端连接】：连接成功！！");
                    channel = sync.channel();
                } else {
                    sync.channel().close();
                    if (channel != null) {
                        client.clone();
                    }
                }
            });

            // 关闭
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                worker.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Client(INetListener netListener) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        client.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringEncoder()).addLast(new ProtocolEncoder()).addLast(new StringDecoder()).addLast(new ProtocolDecoder());
                //                          .addLast(new ClientProtocolHandler(null));
            }
        });
        try {
            ChannelFuture sync = client.connect(addr, port).sync();
            sync.addListener((ChannelFutureListener) future -> {
                if (sync.isSuccess()) {
                    System.out.println("【Netty 客户端连接】：连接成功！！");
                    channel = sync.channel();
                    netListener.successChannel(channel);
                } else {
                    sync.channel().close();
                    if (channel != null) {
                        client.clone();
                    }
                }
            });

            // 关闭
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                worker.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Channel getChannel() {
        while (this.channel == null) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.channel;
    }

    //    public static void main(String[] args) throws InterruptedException {
    //        Client client = new Client(new ProviderImpl() {
    //            @Override
    //            public void successChannel(Channel channel) {
    //                //                channel.pipeline().addLast(new ClientProtocolHandler());
    //                //                channel.config().setAutoRead(false);
    //                //                channel.read();
    //                //                channel.closeFuture().addListener(new ChannelFutureListener() {
    //                //                    @Override
    //                //                    public void operationComplete(ChannelFuture future) throws Exception {
    //                //                        if (future.isSuccess()) {
    //                //                            channel.read();
    //                //                        } else {
    //                //                            future.cause().printStackTrace();
    //                //                        }
    //                //                    }
    //                //                });
    //                String s = "";
    //                Scanner sc = new Scanner(System.in);
    //                System.out.println("是否继续：Y|N");
    //                while ((s = sc.nextLine()).equals("Y") && channel.isWritable()) {
    //                    Protocol<Message> protocol = new Protocol<>();
    //                    System.out.println("\n操作：cat | enqueue ");
    //                    OperateEnum anEnum = OperateEnum.getEnum(sc.nextLine());
    //                    protocol.setHeader(anEnum.getValue());
    //                    System.out.println("消息topic：");
    //                    protocol.setTopic(sc.nextLine());
    //                    if (anEnum == OperateEnum.ENQUEUE) {
    //                        System.out.println("消息内容：");
    //                        Message message = new Message(sc.nextLine());
    //                        protocol.setData(message);
    //                    }
    //                    //                    System.out.println("protocol:" + protocol);
    //                    channel.writeAndFlush(protocol);
    //                    //                    channel.read();
    //                    System.out.println("是否继续：Y|N");
    //                }
    //                System.out.println("退出");
    //            }
    //        });
    //
    //        //                        IConsumer simpleConsumer = new ConsumerImpl("11");
    //        //                        Client client = new Client(simpleConsumer);
    //        //
    //        //                                new Thread(() -> {
    //        //                                    while (client == null || client.getChannel() == null) {
    //        //                                        try {
    //        //                                            TimeUnit.MILLISECONDS.sleep(100);
    //        //                                        } catch (InterruptedException e) {
    //        //                                            e.printStackTrace();
    //        //                                        }
    //        //                                    }
    //        //                                    Scanner scanner = new Scanner(System.in);
    //        //                                    while (true) {
    //        //                                        String msg = scanner.nextLine();
    //        //
    //        //                                        boolean sendMsg = sendMsg(msg);
    //        //                                        if (sendMsg == false) {
    //        //                                            break;
    //        //                                        }
    //        //                                    }
    //        //
    //        //                                }).start();
    //
    //        //        Client client = new Client(new IConsumer() {
    //        //            @Override
    //        //            public void onMessages(List<Message> messages) {
    //        //
    //        //            }
    //        //
    //        //            @Override
    //        //            public void successChannel(Channel channel) {
    //        //                Protocol<Message> protocol = new Protocol<>();
    //        //                Scanner sc = new Scanner(System.in);
    //        //                OperateEnum anEnum = OperateEnum.getEnum(sc.nextLine());
    //        //                protocol.setHeader(anEnum.getValue());
    //        //                System.out.println("消息topic：");
    //        //                protocol.setTopic(sc.nextLine());
    //        //                while (true) {
    //        //                    channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
    //        //                        @Override
    //        //                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    //        //                            System.out.println("Received from server: " + msg);
    //        //                        }
    //        //                    });
    //        //                }
    //        //
    //        //            }
    //        //        });
    //
    //    }

}
