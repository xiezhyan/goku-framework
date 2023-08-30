package top.zopx.goku.framework.socket.core.constant.enums;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 17:21
 */
public enum CommandLineEnum {

    /**
     * --server_job_type_set=GATEWAY
     * --server_id=1001
     * --server_host=127.0.0.1
     * --server_port=54321
     * --server_path=/websocket
     * --conf=../etc/proxyserver_all.conf.json
     */
    SERVER_ID("server_id", "服务ID"),
    SERVER_HOST("server_host", "主机地址"),
    SERVER_PORT("server_port", "启动端口"),
    SERVER_PATH("server_path", "请求地址"),
    SERVER_JOB_TYPE("server_job_type", "服务职能"),
    SERVER_CONF("conf", "配置文件"),
    ;

    private final String longOpt;
    private final String desc;

    CommandLineEnum(String longOpt, String desc) {
        this.longOpt = longOpt;
        this.desc = desc;
    }

    public static void parse(Context context, String... args) {
        final Options options = new Options();
        for (CommandLineEnum cli : values()) {
            options.addOption(
                    Option.builder()
                            .option(cli.longOpt)
                            .longOpt(cli.longOpt)
                            .hasArg(true)
                            .required(true)
                            .desc(cli.desc)
                            .build()
            );
        }
        final CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parse = parser.parse(options, args);

            options.getOptions()
                    .stream()
                    .filter(option -> StringUtils.isNotBlank(option.getLongOpt()) && StringUtils.isNotBlank(parse.getOptionValue(option)))
                    .forEach(option -> context.attr(option.getLongOpt(), parse.getOptionValue(option)));
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    public String getLongOpt() {
        return longOpt;
    }
}
