package simple2.server.net.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import simple2.bean.Protocol;
import simple2.server.net.IOperationDistributor;
import simple2.server.net.impl.OperationDistributorImpl;

/**
 * 业务分发（业务handler要绑定到ChannelHandler上）
 *
 * @Author yuanlw
 **/
@Slf4j
public class ServerProtocolHandler extends SimpleChannelInboundHandler<Protocol> {

    /**
     * 分发器
     */
    public static IOperationDistributor operationDistributor = new OperationDistributorImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Protocol msg) throws Exception {
        log.info("【netty Server 获取数据成功】：{}", msg);
        // 协议处理器
        Protocol handle = operationDistributor.handle(msg);
        //返回客户端消息
        ctx.writeAndFlush(handle);
    }
}
