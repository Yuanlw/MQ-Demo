package simple2;

import lombok.Data;

/**
 * 消息
 *
 * @Author yuanlw
 **/
@Data
public class Message {

    /**
     * 消息内容
     */
    private String text;

    public Message(String text) {
        this.text = text;
    }


}
