package simple3;

import java.io.Serializable;
import java.util.List;

/**
 * Response
 *
 * @Author yuanlw
 **/
public class MQResponse implements Serializable {
    public static final int DELIVER = 1;          // 消息投递
    public static final int COMMIT_RESULT = 2;    // 提交结果
    public static final int ROLLBACK_RESULT = 3;  // 回滚结果

    private int type;                            // 响应类型
    private List<Message> msgs;                  // 消息列表
    private String result;                       // 提交或回滚结果

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
