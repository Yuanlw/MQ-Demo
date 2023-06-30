package simple2.api;

import simple2.Message;

import java.util.List;

/**
 * @ClassName IConsumer
 * @Description consumer端
 * @Author yuanlw
 * @Date 2023/6/26 17:07
 * @Version 1.0
 **/
public interface IConsumer {

    void onMessages(List<Message> messages);

//     void successChannel(Channel channel) ;
}
