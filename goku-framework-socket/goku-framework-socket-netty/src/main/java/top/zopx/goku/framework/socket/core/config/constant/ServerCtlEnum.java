package top.zopx.goku.framework.socket.core.config.constant;

import top.zopx.goku.framework.socket.core.config.util.Ctl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 命令行参数
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public enum ServerCtlEnum implements IServerCommandLineKey {

    /**
     * --server_name=gateway
     * --server_job_type_set=GATEWAY
     * --server_id=1001
     * --server_host=127.0.0.1
     * --server_port=54321
     * --server_path=/websocket
     * --conf=../etc/proxyserver_all.conf.json
     */
    SERVER_ID("id", "server_id", "服务器ID", true),
    /**
     * 服务器名称
     */
    SERVER_NAME(null, "server_name", "服务器名称", true),
    /**
     * 服务处理类型
     */
    SERVER_JOB_TYPE(null, "server_job_type_set", "服务处理类型", true),
    /**
     * 服务器主机地址
     */
    SERVER_HOST("h", "server_host", "服务器主机地址", true),
    /**
     * 服务器端口号
     */
    SERVER_PORT("p", "server_port", "服务器端口号", true),
    /**
     * 服务器请求地址
     */
    SERVER_PATH("path", "server_path", "服务器请求地址", true),
    /**
     * 服务配置文件
     */
    SERVER_CONFIG("c", "conf", "服务配置文件", true)
    ;

    private final String opt;
    private final String longOpt;
    private final String desc;
    private final boolean hasRequire;

    ServerCtlEnum(String opt, String longOpt, String desc, boolean hasRequire) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.desc = desc;
        this.hasRequire = hasRequire;
    }

    @Override
    public String getOpt() {
        return opt;
    }

    @Override
    public String getLongOpt() {
        return longOpt;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public boolean isHasRequire() {
        return hasRequire;
    }

    /**
     * 处理命令行参数
     *
     * @param commandLineKeys 额外参数
     * @return List<CommandLnUtil.Config>
     */
    public static List<Ctl.Config> create(IServerCommandLineKey... commandLineKeys) {
        List<IServerCommandLineKey> serverCommandLineKeys = new ArrayList<>(Arrays.asList(commandLineKeys));
        serverCommandLineKeys.addAll(Arrays.asList(values()));
        return serverCommandLineKeys.stream()
                .map(cons -> new Ctl.Config(cons.getOpt(), cons.getLongOpt(), cons.getDesc(), cons.isHasRequire()))
                .toList();
    }

    static Map<String, String> map;
    /**
     * 将命令行参数封装
     *
     * @param args            命令行获取到的参数
     * @param commandLineKeys 额外参数
     */
    public static void parse(String[] args, IServerCommandLineKey... commandLineKeys) {
        map = Ctl.create(args, create(commandLineKeys).toArray(new Ctl.Config[0]));
    }

    public String get() {
        return get(this.longOpt);
    }

    public static String get(String key) {
        return map.get(key);
    }
}
