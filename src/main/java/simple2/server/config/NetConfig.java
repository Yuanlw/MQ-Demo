package simple2.server.config;
import simple2.server.utils.PropsUtils;
import lombok.Getter;
/**
 * TODO
 *
 * @Author yuanlw
 **/
public class NetConfig {
    /**
     * net配置文件属性前缀
     */
    private final static String PREFIX = "server.";

    /**
     * 服务端口
     */
    @Getter
    private static final Integer port;

    static {
        port = PropsUtils.getInt(PREFIX + "port");
    }

}
