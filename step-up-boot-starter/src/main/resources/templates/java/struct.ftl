package ${package!""}.struct;

import ${package!""}.entity.db.User;
import ${package!""}.entity.vo.UserVo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
*    version: ${table.tableComment!""}
* ----------------------
*    author: Mr.sanq
*    date: ${nowDate?string("yyyy-MM-dd")}
*/
@Mapper(componentModel = "spring")
public interface ${table.javaName?cap_first!""}Struct {

    ${table.javaName?cap_first!""}Struct INSTANCE = Mappers.getMapper( ${table.javaName?cap_first!""}Struct.class );

    ${table.javaName?cap_first!""}Vo to(${table.javaName?cap_first!""} ${table.javaName});

    ${table.javaName?cap_first!""} to(${table.javaName?cap_first!""}Vo ${table.javaName}Vo);

    List<${table.javaName?cap_first!""}Vo> to(List<${table.javaName?cap_first!""}> ${table.javaName}List);

    List<${table.javaName?cap_first!""}> to(List<${table.javaName?cap_first!""}Vo> ${table.javaName}VOlist);
}