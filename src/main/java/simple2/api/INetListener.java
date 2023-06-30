package simple2.api;

import io.netty.channel.Channel;

/**
 * @ClassName INetListener
 * @Description 监听器
 * @Author yuanlw
 * @Date 2023/6/26 17:08
 * @Version 1.0
 **/
public interface INetListener {
    void successChannel(Channel channel);
}
