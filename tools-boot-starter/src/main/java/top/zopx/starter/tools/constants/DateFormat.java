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
    YYYY_MM("yyyy-MM"),
    YYYY_MM_DD("yyyy-MM-dd"),
    MM_DD("MM-dd"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    HH_MM("HH:mm"),
    HH_MM_SS("HH:mm:ss"),
    ;

    private String format;
}