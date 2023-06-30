package simple2.server.net;

import simple2.Message;
import simple2.bean.Protocol;

/**
 * @ClassName IOperationDistributor
 * @Description 操作器
 * @Author yuanlw
 * @Date 2023/6/27 14:25
 * @Version 1.0
 **/
public interface IOperationDistributor {

    Protocol handle(Protocol<Message> protocol);
}
