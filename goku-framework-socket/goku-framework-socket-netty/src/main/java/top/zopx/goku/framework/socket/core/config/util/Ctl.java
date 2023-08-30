package top.zopx.goku.framework.socket.core.config.util;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class Ctl {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ctl.class);

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
            options.addOption(
                    Option.builder()
                            .option(config.opt())
                            .longOpt(config.longOpt())
                            .hasArg(true)
                            .required(config.hasRequire)
                            .desc(config.desc)
                            .build()
            );
        }
        final CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parse = parser.parse(options, args);
            return options.getOptions()
                    .stream()
                    .filter(option -> StringUtils.isNotBlank(option.getLongOpt()) && StringUtils.isNotBlank(parse.getOptionValue(option)))
                    .collect(Collectors.toMap(Option::getLongOpt, parse::getOptionValue));
        } catch (ParseException e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    public record Config(
            String opt,
            String longOpt,
            String desc,
            boolean hasRequire
    ) {
    }
}
