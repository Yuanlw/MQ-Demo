package simple2.client.handle;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import simple2.Message;
import simple2.api.IConsumer;
import simple2.bean.Protocol;

import java.util.Arrays;

/**
 * TODO
 *
 * @Author yuanlw
 **/
@Slf4j
public class ClientProtocolHandler extends SimpleChannelInboundHandler<Protocol> {

    private IConsumer consumer;

    public ClientProtocolHandler(IConsumer consumer) {
        this.consumer = consumer;
    }

    public ClientProtocolHandler() {
    }

    private Protocol<Message> parseMsg(String msg) {
        try {
            Protocol protocol = JSONUtil.toBean(msg, Protocol.class);
            if (protocol.getData() instanceof JSONObject) {
                Message message = JSONUtil.toBean((JSONObject) protocol.getData(), Message.class);
                protocol.setData(message);
            }
            return protocol;
        } catch (Exception e) {
            log.info("【自定义协议转化器异常】：", e);
            // 应该终止了
        }
        return null;
    }

    //    @Override
    //    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    //        Protocol<Message> messageProtocol = parseMsg(msg);
    //        log.info("【netty-client获取数据成功】：{}", msg);
    //        // 先简单处理
    //        Object data = messageProtocol.getData();
    //        if (data instanceof Integer) {
    //            // CAT 请求
    //            log.info("【处理分发】 CAT处理：{}", data);
    //        } else if (data instanceof Message) {
    //            // DEQUEUE 请求
    //            log.info("【处理分发】 DEQUEUE处理：{}", data);
    //            Message message = (Message) data;
    //            consumer.onMessages(Arrays.asList(message));
    //        } else {
    //            log.info("【处理分发】 其他处理：{}", msg);
    //        }
    //
    //    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol msg) throws Exception {
        log.info("【netty Client 获取数据成功】：{}", msg);
//        if (msg.getTopic().equals(consumer.getTopic()))
        // 短期内先简单处理
        Object data = msg.getData();
        if (data instanceof Integer) {
            // CAT 请求
            log.info("【处理分发】 CAT 处理：队列数据数 {}", msg.getData());

        } else if (data instanceof Message) {
            // DEQUEUE 请求
            log.info("【处理分发】 DEQUEUE 处理：{}", msg.getData());
            Message message = (Message) data;
            consumer.onMessages(Arrays.asList(message));
        } else {
            log.info("【处理分发】 其他处理：{}", msg);
        }

    }


}
