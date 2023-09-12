package top.zopx.goku.framework.socket.tools.circuit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.socket.tools.circuit.chain.ParseRequestHandler;
import top.zopx.goku.framework.socket.tools.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.core.constant.enums.CommandLineEnum;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 17:01
 */
public class Context {

    private static final LinkedList<RequestHandler> CHAIN = new LinkedList<>();
    private static final Map<String, Object> MAP = new ConcurrentHashMap<>(64);

    public Context(String[] args) {
        // 解析命令行参数，必须先执行，否则无法从Context中获取到相关信息
        new ParseRequestHandler(args)
                .handleRequest(this);
    }

    public void add(RequestHandler requestHandler) {
        if (null == requestHandler) {
            return;
        }
        CHAIN.add(requestHandler);
    }

    public void add(int index, RequestHandler requestHandler) {
        if (null == requestHandler) {
            return;
        }
        CHAIN.add(index, requestHandler);
    }

    public void execute() {
        if (CollectionUtils.isEmpty(CHAIN)) {
            return;
        }

        CHAIN.forEach(handle -> handle.handleRequest(this));
    }

    public void attr(String key, Object value) {
        if (StringUtils.isEmpty(key) || Objects.isNull(value)) {
            throw new BusException("attr存储异常", IBus.ERROR_CODE);
        }

        MAP.put(key, value);
    }

    public Object attr(String key) {
        if (StringUtils.isEmpty(key) ||
                Boolean.TRUE.equals(isEmpty())
        ) {
            throw new BusException("attr获取异常", IBus.ERROR_CODE);
        }

        return MAP.get(key);
    }

    public Boolean isEmpty() {
        return MapUtils.isEmpty(MAP);
    }

    public Boolean hasKey(String... args) {
        for (String s : args) {
            if (!MAP.containsKey(s)) {
                return false;
            }
        }
        return true;
    }

    public static int getServerId() {
        check(CommandLineEnum.SERVER_ID);
        return Integer.parseInt(MAP.get(CommandLineEnum.SERVER_ID.getLongOpt()).toString());
    }

    public static Set<String> getServerJobTypeSet() {
        check(CommandLineEnum.SERVER_JOB_TYPE);

        return Arrays.stream(MAP.get(CommandLineEnum.SERVER_JOB_TYPE.getLongOpt()).toString()
                        .split(","))
                .distinct()
                .collect(Collectors.toSet());
    }

    public static String getServerHost() {
        check(CommandLineEnum.SERVER_HOST);
        return MAP.get(CommandLineEnum.SERVER_HOST.getLongOpt()).toString();
    }

    public static Integer getServerPort() {
        check(CommandLineEnum.SERVER_PORT);
        return Integer.parseInt(MAP.get(CommandLineEnum.SERVER_PORT.getLongOpt()).toString());
    }

    public static String getServerPath() {
        check(CommandLineEnum.SERVER_PATH);
        return MAP.get(CommandLineEnum.SERVER_PATH.getLongOpt()).toString();
    }

    public static double getLoadCount() {
        if (MapUtils.isEmpty(MAP)) {
            throw new BusException("attr参数为空", IBus.ERROR_CODE);
        }

        return Double.parseDouble(MAP.getOrDefault("load_count", "0").toString());
    }

    public static void setLoadCount(double loadCount) {
        MAP.put("load_count", loadCount);
    }

    private static void check(CommandLineEnum cli) {
        if (MapUtils.isEmpty(MAP)) {
            throw new BusException("attr参数为空", IBus.ERROR_CODE);
        }

        if (!MAP.containsKey(cli.getLongOpt())) {
            throw new BusException(cli.getLongOpt() + " 尚未初始化成功", IBus.ERROR_CODE);
        }
    }
}
