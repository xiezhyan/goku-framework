package top.zopx.goku.framework.socket.util;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public enum CommandLnUtil {


    /**
     * 单例
     */
    INSTANCE,
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLnUtil.class);

    /**
     * 命令行参数获取
     *
     * @param args    命令行参数
     * @param configs 具体命令
     * @return 命令行对象
     */
    public CommandLine create(String[] args, Config... configs) {
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
            return parser.parse(options, args);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static class Config {

        private String opt;
        private String longOpt;
        private String desc;
        private boolean hasArg;

        public Config(String opt, String longOpt, String desc, boolean hasArg) {
            this.opt = opt;
            this.longOpt = longOpt;
            this.desc = desc;
            this.hasArg = hasArg;
        }

        public String getOpt() {
            return opt;
        }

        public void setOpt(String opt) {
            this.opt = opt;
        }

        public String getLongOpt() {
            return longOpt;
        }

        public void setLongOpt(String longOpt) {
            this.longOpt = longOpt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isHasArg() {
            return hasArg;
        }

        public void setHasArg(boolean hasArg) {
            this.hasArg = hasArg;
        }
    }
}
