package top.zopx.starter.tools.tools.ip;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFactory {

    private static final Logger logger;
    static {
        logger = Logger.getLogger("stdout");
        logger.setLevel(Level.ALL);
    }

    public static void log(String info, Level level, Throwable ex) {
        logger.log(level, info, ex);
    }

    public static Level  getLogLevel(){
        return logger.getLevel();
    }
}
