package ${package!""}.mapper;

import ${package!""}.entity.db.${table.javaName?cap_first};
import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *	version: ${table.comment!""}
 * ----------------------
 * 	author: Mr.sanq
 * 	date: ${nowDate?string("yyyy-MM-dd")}
 */
public interface ${table.javaName?cap_first}Mapper {

   int save(${table.javaName?cap_first} save);

   int delete(${table.javaName?cap_first}Vo delete);

   int update(@Param("entity") ${table.javaName?cap_first} update, @Param("id") <#list fields as field><#if field.columnKey == "PRI">${field.javaType}</#if></#list> id);

   ${table.javaName?cap_first} findById(<#list fields as field><#if field.columnKey == "PRI">${field.javaType}</#if></#list> id);

   List<${table.javaName?cap_first}> findList(@Param("query") ${table.javaName?cap_first}Vo query);

   List<${table.javaName?cap_first}> findListByPage(@Param("query") ${table.javaName?cap_first}Vo query, @Param("startPage") int startPage, @Param("pageSize") int pageSize);

   int findCount(@Param("query") ${table.javaName?cap_first}Vo query);

   void saveByList(@Param("saves") List<${table.javaName?cap_first}> save);
}