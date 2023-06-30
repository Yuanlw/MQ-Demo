package simple2.bean;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @ClassName OperateEnum
 * @Description TODO
 * @Author yuanlw
 * @Date 2023/6/26 17:23
 * @Version 1.0
 **/
@AllArgsConstructor
public enum OperateEnum {

    /**
     * 查看
     */
    CAT("cat"),

    /**
     * 入队
     */
    ENQUEUE("enqueue"),

    /**
     * 出队
     */
    DEQUEUE("dequeue"),

    /**
     * 响应
     */
    RESPONSE("response");

    @Getter
    private final String value;

    public static OperateEnum getEnum(String str) {
        if (StrUtil.isNotEmpty(str)) {
            for (OperateEnum operateEnum : OperateEnum.values()) {
                if (StrUtil.equals(operateEnum.value, str)) {
                    return operateEnum;
                }
            }
        }
        return CAT;
    }
}
