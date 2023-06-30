package simple2.client.handle;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import simple2.bean.Protocol;
import simple2.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
/**
 * TODO
 *
 * @Author yuanlw
 **/
@Slf4j
public class ProtocolDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        try {
            System.out.println("decode");
            Protocol protocol = JSONUtil.toBean(msg, Protocol.class);
            //安全、校验等等
            if (protocol.getData() instanceof JSONObject){
                Message message = JSONUtil.toBean((JSONObject) protocol.getData(), Message.class);
                protocol.setData(message);
            }
            out.add(protocol);
        } catch (Exception e) {
            log.info("【自定义协议转化器异常】：", e);//需要异常处理
        }

    }
}
