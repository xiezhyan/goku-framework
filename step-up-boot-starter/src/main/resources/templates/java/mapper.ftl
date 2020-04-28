package ${package!""}.mapper;

import ${package!""}.entity.db.${table.javaName?cap_first};
import ${package!""}.entity.vo.${table.javaName?cap_first}Vo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *	version: ${table.comment!""}
 * ----------------------
 * 	author: Mr.sanq
 * 	date: ${nowDate?string("yyyy-MM-dd")}
 */
public interface ${table.javaName?cap_first}Mapper {

   int save(${table.javaName?cap_first} ${table.javaName});

   int delete(${table.javaName?cap_first}Vo ${table.javaName});

   int update(@Param("${table.javaName}") ${table.javaName?cap_first} ${table.javaName}, @Param("<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>") <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>);

   ${table.javaName?cap_first} findById(<#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>);

   List<${table.javaName?cap_first}> findList(@Param("${table.javaName}") ${table.javaName?cap_first}Vo ${table.javaName});

   List<${table.javaName?cap_first}> findListByPage(@Param("${table.javaName}") ${table.javaName?cap_first}Vo ${table.javaName}, @Param("startPage") int startPage, @Param("pageSize") int pageSize);

   int findCount(@Param("${table.javaName}") ${table.javaName?cap_first}Vo ${table.javaName});

   void saveByList(@Param("${table.javaName}List") List<${table.javaName?cap_first}> ${table.javaName}List);

}