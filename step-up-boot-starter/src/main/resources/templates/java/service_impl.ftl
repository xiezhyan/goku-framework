package ${package!""}.service.impl;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import ${package}.entity.db.${table.javaName?cap_first};

import top.zopx.starter.tools.basic.*;

import ${package}.service.${table.javaName?cap_first}Service;
import ${package}.service.struct.${table.javaName?cap_first}Struct;
import ${package}.mapper.${table.javaName?cap_first}Mapper;

import java.util.List;
import java.math.BigDecimal;

import top.zopx.starter.tools.exceptions.BusException;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * version: ${table.comment!""}
 *----------------------
 * author: Mr.sanq
 * date: ${nowDate?string("yyyy-MM-dd")}
 */
@Service("${table.javaName}Service")
public class ${table.javaName?cap_first}ServiceImpl implements ${table.javaName?cap_first}Service {

	@Resource
	private ${table.javaName?cap_first}Mapper ${table.javaName}Mapper;
	@Resource
	private ${table.javaName?cap_first}Struct ${table.javaName}Struct;

	@Override
	@Transactional
	public int save(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.save(
						${table.javaName}Struct.to(${table.javaName}Vo)
					);
	}

	@Override
	@Transactional
	public int delete(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.delete(${table.javaName}Vo);
	}

	@Override
	@Transactional
	public int update(${table.javaName?cap_first}Vo ${table.javaName}Vo, <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {
		${table.javaName?cap_first} old${table.javaName?cap_first} = ${table.javaName}Mapper.findById(<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>);

		if(null != old${table.javaName?cap_first} && null != ${table.javaName}Vo) {

			old${table.javaName?cap_first} = ${table.javaName}Struct.to(${table.javaName}Vo);

			return ${table.javaName}Mapper.update(old${table.javaName?cap_first}, <#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>);
		}
		return 0;
	}

	@Override
	public ${table.javaName?cap_first}Vo findById(<#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {

		return ${table.javaName}Struct.to(
						${table.javaName}Mapper.findById(<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>)
					);
	}

	@Override
	public List<${table.javaName?cap_first}Vo> findList(${table.javaName?cap_first}Vo ${table.javaName}Vo) {

        List<${table.javaName?cap_first}> datas = ${table.javaName}Mapper.findList(${table.javaName}Vo);

		return ${table.javaName}Struct.to(datas);
	}

	@Override
	public int findCount(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.findCount(${table.javaName}Vo);
	}
}