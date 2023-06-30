package simple2.client.handle;

import cn.hutool.json.JSONUtil;
import simple2.bean.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class ProtocolEncoder extends MessageToMessageEncoder<Protocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, List<Object> out) throws Exception {
        String s = JSONUtil.parse(msg).toString();
        System.out.println("encode");
        System.out.println(msg);
        out.add(s);
    }
}
