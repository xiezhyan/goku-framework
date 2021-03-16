package top.zopx.starter.tools.tools.date;

import org.apache.commons.lang3.RandomUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 整天时间获取
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class LocalDateUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String nowTimeStr() {
        return formatter.format(LocalDateTime.now().withNano(0));
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 向后加 yyyy-MM-dd HH:mm:ss
     *
     * @param year  年
     * @param month 月
     * @param week  星期
     * @param day   日
     * @return LocalDateTime
     */
    public static LocalDateTime nowDateTimePlus(long year, long month, long week, long day) {
        return nowDateTime()
                .plusYears(year)
                .plusMonths(month)
                .plusWeeks(week)
                .plusDays(day);
    }

    /**
     * 向前减 yyyy-MM-dd HH:mm:ss
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDateTime
     */
    public static LocalDateTime nowDateTimeMinus(long year, long month, long week, long day) {
        return nowDateTime()
                .minusYears(year)
                .minusMonths(month)
                .minusWeeks(week)
                .minusDays(day);
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 向后加 yyyy-MM-dd
     *
     * @param year  年
     * @param month 月
     * @param week  星期
     * @param day   日
     * @return LocalDate
     */
    public static LocalDate nowDatePlus(long year, long month, long week, long day) {
        return nowDate()
                .plusYears(year)
                .plusMonths(month)
                .plusWeeks(week)
                .plusDays(day);
    }

    /**
     * 向前减 yyyy-MM-dd
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDate
     */
    public static LocalDate nowDateMinus(long year, long month, long week, long day) {
        return nowDate()
                .minusYears(year)
                .minusMonths(month)
                .minusWeeks(week)
                .minusDays(day);
    }

    /**
     * 向前开始时间
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDateTime
     */
    public static LocalDateTime lastStartDateTime(long year, long month, long week, long day) {
        return nowDate()
                .atTime(0, 0, 0)
                .minusYears(year)
                .minusMonths(month)
                .minusWeeks(week)
                .minusDays(day)
                .withNano(0);
    }

    /**
     * 向前结束时间
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDateTime
     */
    public static LocalDateTime lastEndDateTime(long year, long month, long week, long day) {
        return nowDate()
                .atTime(23, 59, 59)
                .minusYears(year)
                .minusMonths(month)
                .minusWeeks(week)
                .minusDays(day)
                .withNano(0);
    }

    /**
     * 向后开始时间
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDateTime
     */
    public static LocalDateTime startDateTime(long year, long month, long week, long day) {
        return nowDate()
                .atTime(0, 0, 0)
                .plusYears(year)
                .plusMonths(month)
                .plusWeeks(week)
                .plusDays(day)
                .withNano(0);
    }

    /**
     * 向后结束时间
     *
     * @param year  年
     * @param month 月
     * @param week  日
     * @param day   天
     * @return LocalDateTime
     */
    public static LocalDateTime endDateTime(long year, long month, long week, long day) {
        return nowDate()
                .atTime(23, 59, 59)
                .plusYears(year)
                .plusMonths(month)
                .plusWeeks(week)
                .plusDays(day)
                .withNano(0);
    }

    public static Date nowTime() {
        return localDateTimeToDate(LocalDateTime.now());
    }

    public static String dayStartTimeStr(int days) {
        return formatter.format(LocalDate.now().minusDays(days).atTime(0, 0, 0).withNano(0));
    }

    public static Date dayStartTime(int days) {
        return localDateTimeToDate(LocalDate.now().minusDays(days).atTime(0, 0, 0).withNano(0));
    }

    public static String dayEndTimeStr(int days) {
        return formatter.format(LocalDate.now().minusDays(days).atTime(23, 59, 59).withNano(0));
    }

    public static Date dayEndTime(int days) {
        return localDateTimeToDate(LocalDate.now().minusDays(days).atTime(23, 59, 59).withNano(0));
    }

    public static String dayStartTimeStr() {
        return formatter.format(LocalDate.now().atTime(0, 0, 0).withNano(0));
    }

    public static Date dayStartTime() {
        return localDateTimeToDate(LocalDate.now().atTime(0, 0, 0).withNano(0));
    }

    public static String dayEndTimeStr() {
        return formatter.format(LocalDate.now().atTime(23, 59, 59).withNano(0));
    }

    public static Date dayEndTime() {
        return localDateTimeToDate(LocalDate.now().atTime(23, 59, 59).withNano(0));
    }

    public static String lastDayStartTimeStr() {
        return formatter.format(LocalDate.now().atTime(0, 0, 0).minusDays(1L).withNano(0));
    }

    public static Date lastDayStartTime() {
        return localDateTimeToDate(LocalDate.now().atTime(0, 0, 0).minusDays(1L).withNano(0));
    }

    public static String lastDayEndTimeStr() {
        return formatter.format(LocalDate.now().atTime(23, 59, 59).minusDays(1L).withNano(0));
    }

    public static Date lastDayEndTime() {
        return localDateTimeToDate(LocalDate.now().atTime(23, 59, 59).minusDays(1L).withNano(0));
    }

    public static String weekStartTimeStr() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return formatter.format(date.atTime(0, 0, 0).minusDays(week - 1).withNano(0));
    }

    public static Date weekStartTime() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return localDateTimeToDate(date.atTime(0, 0, 0).minusDays(week - 1).withNano(0));
    }

    public static String weekEndTimeStr() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return formatter.format(date.atTime(23, 59, 59).plusDays(7 - week).withNano(0));
    }

    public static Date weekEndTime() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return localDateTimeToDate(date.atTime(23, 59, 59).plusDays(7 - week).withNano(0));
    }

    public static String lastWeekStartTimeStr() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return formatter.format(date.atTime(0, 0, 0).minusWeeks(1L).minusDays(week - 1).withNano(0));
    }

    public static Date lastWeekStartTime() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return localDateTimeToDate(date.atTime(0, 0, 0).minusWeeks(1L).minusDays(week - 1).withNano(0));
    }

    public static String lastWeekEndTimeStr() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return formatter.format(date.atTime(23, 59, 59).minusWeeks(1L).plusDays(7 - week).withNano(0));
    }

    public static Date lastWeekEndTime() {
        LocalDate date = LocalDate.now();
        int week = date.getDayOfWeek().getValue();
        return localDateTimeToDate(date.atTime(23, 59, 59).minusWeeks(1L).plusDays(7 - week).withNano(0));
    }

    public static String monthStartTimeStr() {
        return formatter.format(LocalDate.now().atTime(0, 0, 0).with(TemporalAdjusters.firstDayOfMonth()).withNano(0));
    }

    public static Date monthStartTime() {
        return localDateTimeToDate(LocalDate.now().atTime(0, 0, 0).with(TemporalAdjusters.firstDayOfMonth()).withNano(0));
    }

    public static String monthEndTimeStr() {
        return formatter.format(LocalDate.now().atTime(23, 59, 59).with(TemporalAdjusters.lastDayOfMonth()).withNano(0));
    }

    public static Date monthEndTime() {
        return localDateTimeToDate(LocalDate.now().atTime(23, 59, 59).with(TemporalAdjusters.lastDayOfMonth()).withNano(0));
    }

    public static String lastMonthStartTimeStr() {
        return formatter.format(LocalDate.now().atTime(0, 0, 0).minusMonths(1L).with(TemporalAdjusters.firstDayOfMonth()).withNano(0));
    }

    public static Date lastMonthStartTime() {
        return localDateTimeToDate(LocalDate.now().atTime(0, 0, 0).minusMonths(1L).with(TemporalAdjusters.firstDayOfMonth()).withNano(0));
    }

    public static String lastMonthEndTimeStr() {
        return formatter.format(LocalDate.now().atTime(23, 59, 59).minusMonths(1L).with(TemporalAdjusters.lastDayOfMonth()).withNano(0));
    }

    public static Date lastMonthEndTime() {
        return localDateTimeToDate(LocalDate.now().atTime(23, 59, 59).minusMonths(1L).with(TemporalAdjusters.lastDayOfMonth()).withNano(0));
    }

    public static Date localDateToDate(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date localDateTimeToDate(LocalDateTime date) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = date.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static int to24() {
        LocalTime time = LocalTime.now().withNano(0);
        LocalTime time1 = LocalTime.of(23, 59, 59);
        return time1.toSecondOfDay() - time.toSecondOfDay();
    }

    public static long getTime(double time) {
        return StringUtil.toLong((time + RandomUtils.nextLong(0, 180)));
    }

    public static void main(String[] args) {
        System.out.println("获取当前时间:" + nowTimeStr());
        System.out.println("获取当前时间:" + nowTime().getTime());
        System.out.println("当天开始时间:" + dayStartTimeStr());
        System.out.println("当天开始时间:" + dayStartTime());
        System.out.println("当天结束时间:" + dayEndTimeStr());
        System.out.println("当天结束时间:" + dayEndTime());
        System.out.println("昨天开始时间:" + lastDayStartTimeStr());
        System.out.println("昨天开始时间" + lastDayStartTime());
        System.out.println("昨天结束时间" + lastDayEndTimeStr());
        System.out.println("昨天结束时间" + lastDayEndTime());
        System.out.println("本周开始时间" + weekStartTimeStr());
        System.out.println("本周开始时间" + weekStartTime());
        System.out.println("本周结束时间" + weekEndTimeStr());
        System.out.println("本周结束时间" + weekEndTime());
        System.out.println("上周开始时间" + lastWeekStartTimeStr());
        System.out.println("上周开始时间" + lastWeekStartTime());
        System.out.println("上周结束时间" + lastWeekEndTimeStr());
        System.out.println("上周结束时间" + lastWeekEndTime());
        System.out.println("本月开始时间" + monthStartTimeStr());
        System.out.println("本月开始时间" + monthStartTime());
        System.out.println("本月结束时间" + monthEndTimeStr());
        System.out.println("本月结束时间" + monthEndTime());
        System.out.println("上月开始时间" + lastMonthStartTimeStr());
        System.out.println("上月开始时间" + lastMonthStartTime());
        System.out.println("上月结束时间" + lastMonthEndTimeStr());
        System.out.println("上月结束时间" + lastMonthEndTime());

        System.out.println("===========================================");

        System.out.println("当前时间：" + nowDate());
        System.out.println("当前时间" + nowDateTime());
        System.out.println("当前开始时间" + startDateTime(0, 0, 0, 0));
        System.out.println("当前结束时间" + endDateTime(0, 0, 0, 0));

        System.out.println("昨天开始时间"+ lastStartDateTime(0,0,0, 1));
        System.out.println("昨天开始时间"+ lastEndDateTime(0,0,0, 1));

    }
}
