package top.zopx.goku.framework.util;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public class CommandLnUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLnUtil.class);

    /**
     * 命令行参数获取
     *
     * @param args    命令行参数
     * @param configs 具体命令
     * @return 命令行对象
     */
    public static Map<String, String> create(String[] args, Config... configs) {
        final Options options = new Options();
        for (Config config : configs) {
            options.addRequiredOption(
                    config.getOpt(),
                    config.getLongOpt(),
                    config.isHasArg(),
                    config.getDesc()
            );
        }
        final CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parse = parser.parse(options, args);
            return options.getOptions()
                    .stream()
                    .collect(Collectors.toMap(Option::getLongOpt, parse::getOptionValue));
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> map = create(args, new Config("h", "server_host", "服务器主机", true));
        System.out.println(map.get("server_host"));
    }
    public static class Config {

        //  SERVER_HOST("h", "server_host", "服务器主机地址", true),
        private final String opt;
        private final String longOpt;
        private final String desc;
        private final boolean hasArg;

        public Config(String opt, String longOpt, String desc, boolean hasArg) {
            this.opt = opt;
            this.longOpt = longOpt;
            this.desc = desc;
            this.hasArg = hasArg;
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

        public boolean isHasArg() {
            return hasArg;
        }
    }
}
