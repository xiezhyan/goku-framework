package ${package!""}.service;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import java.util.List;

import top.zopx.starter.tools.basic.*;

/**
 * version: ${table.tableComment!""}
 *----------------------
 * author: Mr.sanq
 * date: ${nowDate?string("yyyy-MM-dd")}
 */
public interface ${table.javaName?cap_first}Service {

      int save(${table.javaName?cap_first}Vo ${table.javaName}Vo);

      int delete(${table.javaName?cap_first}Vo ${table.javaName?cap_first}Vo);

      int update(${table.javaName?cap_first}Vo ${table.javaName?cap_first}Vo, <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>);

      ${table.javaName?cap_first}Vo findById(<#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>);

      List<${table.javaName?cap_first}Vo> findList(${table.javaName?cap_first}Vo ${table.javaName}Vo);

      int findCount(${table.javaName?cap_first}Vo ${table.javaName}Vo);

}