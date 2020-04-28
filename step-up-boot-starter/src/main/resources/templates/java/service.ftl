package ${package!""}.service;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import java.util.List;

import com.sanq.product.cab.basic.Page;
import com.sanq.product.cab.basic.Pagination;

/**
 * version: ${table.comment!""}
 *----------------------
 * author: Mr.sanq
 * date: ${nowDate?string("yyyy-MM-dd")}
 */
public interface ${table.javaName?cap_first}Service {

  int save(${table.javaName?cap_first}Vo save);

  int delete(${table.javaName?cap_first}Vo delete);

  int update(${table.javaName?cap_first}Vo update, <#list fields as field><#if field.columnKey == "PRI">${field.javaType} ${field.javaField}</#if></#list>);

  ${table.javaName?cap_first}Vo findById(<#list fields as field><#if field.columnKey == "PRI">${field.javaType} ${field.javaField}</#if></#list>);

  List<${table.javaName?cap_first}Vo> findList(${table.javaName?cap_first}Vo query);

  Page<${table.javaName?cap_first}Vo> findListByPage(${table.javaName?cap_first}Vo query, Pagination pagination);

  int findCount(${table.javaName?cap_first}Vo query);

}