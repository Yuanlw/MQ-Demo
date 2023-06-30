package simple2.server;

import simple2.server.net.Server;

/**
 * TODO
 *
 * @Author yuanlw
 **/
public class Bootstrap {
    public static void main(String[] args) {
        // 一个服务线程
        new Thread(() -> Server.start()).start();
    }
}
