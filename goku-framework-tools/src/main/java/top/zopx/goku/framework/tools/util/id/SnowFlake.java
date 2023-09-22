package top.zopx.goku.framework.tools.util.id;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 雪花算法
 *
 * @author xiezhyan
 * @email xiezhyan@126.com
 * @date 2022/1/27
 */
@SuppressWarnings("all")
public class SnowFlake {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = LocalDateTime.now()
            .with(TemporalAdjusters.firstDayOfYear())
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .toInstant(ZoneOffset.of("+8"))
            .toEpochMilli();

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public SnowFlake() {
        this.datacenterId = getDatacenterId(31L);
        this.machineId = getMaxMachineId(this.datacenterId, 31L);
    }

    /**
     * 雪花算法
     *
     * @param datacenterId 数据中心
     * @param machineId    机器标识
     */
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = (255L & (long)mac[mac.length - 1] | 65280L & (long)mac[mac.length - 2] << 8) >> 6;
                id %= maxDatacenterId + 1L;
            }
        } catch (Exception e) {
        }

        return id;
    }


    protected static long getMaxMachineId(long datacenterId, long maxMachineId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String dockerId = getDockerId();
        if (dockerId == null) {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            if (StringUtils.isNotBlank(name)) {
                mpid.append(name.split("@")[0]);
            }
        } else {
            mpid.append(dockerId);
        }

        return (long)(mpid.toString().hashCode() & '\uffff') % (maxMachineId + 1L);
    }

    private static String getDockerId() {
        String osName = System.getProperty("os.name");
        if (StringUtils.isBlank(osName) || !osName.toLowerCase().contains("linux")) {
            return null;
        }

        String command = "cat /etc/hosts";
        try {
            Process process = Runtime.getRuntime().exec(command);

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(process.getInputStream())))) {
                List<String> list = bufferedReader.lines().collect(Collectors.toList());
                if (CollectionUtils.isEmpty(list)) {
                    return null;
                }

                return list.get(list.size() - 1).split("\\s+")[1];
            }
        } catch (Exception e) {
            return null;
        }
    }
}
