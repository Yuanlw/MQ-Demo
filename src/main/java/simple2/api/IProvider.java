package simple2.api;

import simple2.Message;

/**
 * @ClassName IProvider
 * @Description TODO
 * @Author yuanlw
 * @Date 2023/6/26 17:06
 * @Version 1.0
 **/
public interface IProvider {
    void send(Message message);
}
