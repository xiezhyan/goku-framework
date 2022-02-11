package top.zopx.starter.tools.constants;

/**
 * @author 俗世游子
 * @date 2022/2/11
 * @email xiezhyan@126.com
 */
public class PropertiesCons {

    private static final String ROOT_PREFIX = "square";

    public interface Log {
        String LOG_PROPERTIES = ROOT_PREFIX + ".log";
    }

    public interface Netty {
        String NETTY_PROPERTIES = ROOT_PREFIX + ".netty";
    }

}
