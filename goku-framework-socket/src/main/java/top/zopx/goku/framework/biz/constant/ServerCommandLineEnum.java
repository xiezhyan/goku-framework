package top.zopx.goku.framework.biz.constant;

import top.zopx.goku.framework.util.CommandLnUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 命令行参数
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public enum ServerCommandLineEnum {

    /**
     * --server_id=1001
     * --server_name=proxy_server_1001
     * --server_job_type_set=PASSPORT,HALL,GAME,CLUB,CHAT,RECORD
     * -h 0.0.0.0
     * -p 20480
     * -path /ws
     * -c ../etc/proxyserver_all.conf.json
     */
    /**
     * 服务器ID
     */
    SERVER_ID(null, "server_id", "服务器 Id", true),
    /**
     * 服务器名称
     */
    SERVER_NAME(null, "server_name", "服务器名称", true),
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
    SERVER_PATH("path", "server_path", "服务器请求地址", false),
    /**
     * 配置文件
     */
    CONFIG("c", "config", "使用配置文件", true),
    /**
     * 服务处理类型
     */
    SERVER_JOB_TYPE(null, "server_job_type_set", "服务处理类型", true);

    private final String opt;
    private final String longOpt;
    private final String desc;
    private final boolean hasRequire;

    ServerCommandLineEnum(String opt, String longOpt, String desc, boolean hasRequire) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.desc = desc;
        this.hasRequire = hasRequire;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isHasRequire() {
        return hasRequire;
    }

    public static List<CommandLnUtil.Config> create() {
        return Arrays.stream(values()).map(cons -> new CommandLnUtil.Config(cons.opt, cons.longOpt, cons.desc, cons.isHasRequire())).collect(Collectors.toList());
    }

    public static Map<String, String> createCommandLine(String[] args) {
        return CommandLnUtil.create(args, create().toArray(new CommandLnUtil.Config[0]));
    }
}
