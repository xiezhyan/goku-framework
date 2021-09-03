package top.zopx.starter.tools.tools.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * 时间操作
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public enum LocalDateUtil {

    /**
     * 单例
     */
    INSTANCE,
    ;

    /**
     * 当前时间
     *
     * @return LocalDateTime
     */
    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 当前时间
     *
     * @return LocalDate
     */
    public LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param localDateTime LocalDateTime
     * @return LocalDate
     */
    public LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * 当前时间加
     *
     * @param plus offset
     * @param dt   条件
     * @return LocalDateTime
     */
    public LocalDateTime getTimeByPlus(int plus, DateTime dt) {
        return nowDateTime().plus(plus, dt.getTemporalUnit());
    }

    /**
     * 当前时间减
     *
     * @param minus offset
     * @param dt    条件
     * @return LocalDateTime
     */
    public LocalDateTime getTimeByMinus(int minus, DateTime dt) {
        return nowDateTime().minus(minus, dt.getTemporalUnit());
    }

    public static enum DateTime {
        /**
         * 年
         */
        YEAR(ChronoUnit.YEARS),
        /**
         * 月
         */
        MONTH(ChronoUnit.MONTHS),
        /**
         * 日
         */
        DAY(ChronoUnit.DAYS),
        /**
         * 时
         */
        HOUR(ChronoUnit.HOURS),
        /**
         * 分
         */
        MINUTE(ChronoUnit.MINUTES),
        /**
         * 秒
         */
        SECOND(ChronoUnit.SECONDS);

        private final TemporalUnit temporalUnit;

        DateTime(TemporalUnit temporalUnit) {
            this.temporalUnit = temporalUnit;
        }

        public TemporalUnit getTemporalUnit() {
            return temporalUnit;
        }
    }

    public static void main(String[] args) {
        System.out.println(LocalDateUtil.INSTANCE.getTimeByPlus(1, DateTime.MINUTE));
    }
}
