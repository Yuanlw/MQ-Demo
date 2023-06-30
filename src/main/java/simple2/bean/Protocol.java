package simple2.bean;

import lombok.Data;

/**
 * 传输体
 *
 * @Author yuanlw
 **/
@Data
public class Protocol<T> {

    /**
     * 操作头
     */
    private String header;

    /**
     * 主题
     */
    private String topic;

    /**
     * 消息实体
     */
    private T data;

    /**
     * 响应体
     *
     * @param topic
     * @param data
     * @param <K>
     * @return
     */
    public static <K> Protocol<K> buildResponse(String topic, K data) {
        Protocol<K> kProtocol = new Protocol<>();
        kProtocol.setHeader(OperateEnum.RESPONSE.getValue());
        kProtocol.setTopic(topic);
        kProtocol.setData(data);
        return kProtocol;
    }

}
