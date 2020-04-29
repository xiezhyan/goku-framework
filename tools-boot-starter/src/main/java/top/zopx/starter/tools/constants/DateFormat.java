package top.zopx.starter.tools.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * top.zopx.starter.tools.constants.DateFormat
 *
 * @author sanq.Yan
 * @date 2020/4/29
 */
@Getter
@AllArgsConstructor
public enum DateFormat {

    YYYY("yyyy"),
    YYYYMM("yyyy-MM"),
    YYYYMMDD("yyyy-MM-dd"),
    MMDD("MM-dd"),
    YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss"),
    HHMM("HH:mm"),
    HHMMSS("HH:mm:ss"),
    ;

    private String format;
}