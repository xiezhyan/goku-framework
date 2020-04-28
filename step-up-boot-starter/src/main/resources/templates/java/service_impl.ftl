package ${package!""}.service.impl;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import ${package}.entity.db.${table.javaName?cap_first};

import top.zopx.starter.tools.basic.*;

import ${package}.service.${table.javaName?cap_first}Service;
import ${package}.mapper.${table.javaName?cap_first}Mapper;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.copy.BeanCopyUtil;
import org.springframework.util.CollectionUtils;

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

	private ${table.javaName?cap_first} convertVo(${table.javaName?cap_first}Vo ${table.javaName}Vo) {

		${table.javaName?cap_first} ${table.javaName} = new ${table.javaName?cap_first}();

		BeanCopyUtil.copyPropertiesIgnoreNull(${table.javaName}Vo, ${table.javaName});

		return ${table.javaName};
	}

	@Override
	@Transactional
	public int save(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.save(convertVo(${table.javaName}Vo));
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

			BeanCopyUtil.copyPropertiesIgnoreNull(${table.javaName}Vo, old${table.javaName?cap_first});

			return ${table.javaName}Mapper.update(old${table.javaName?cap_first}, <#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>);
		}
		return 0;
	}

	private ${table.javaName?cap_first}Vo convertDB(${table.javaName?cap_first} ${table.javaName}) {
	
		${table.javaName?cap_first}Vo ${table.javaName}Vo = new ${table.javaName?cap_first}Vo();
	
		BeanCopyUtil.copyProperties(${table.javaName}, ${table.javaName}Vo);
		
		return ${table.javaName}Vo;
	}

	@Override
	public ${table.javaName?cap_first}Vo findById(<#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {
		return convertDB(${table.javaName}Mapper.findById(<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>));
	}

	@Override
	public List<${table.javaName?cap_first}Vo> findList(${table.javaName?cap_first}Vo ${table.javaName}Vo) {

        List<${table.javaName?cap_first}> datas = ${table.javaName}Mapper.findList(${table.javaName}Vo);

		return convertList(datas);
	}

	@Override
	public Page<${table.javaName?cap_first}Vo> findListByPage(${table.javaName?cap_first}Vo ${table.javaName}Vo,Pagination pagination) {
		pagination.setTotalCount(findCount(${table.javaName}Vo));

		List<${table.javaName?cap_first}> datas = ${table.javaName}Mapper.findListByPage(${table.javaName}Vo, pagination.getStartPage(), pagination.getPageSize());

		return new Page<${table.javaName?cap_first}Vo>(pagination,convertList(datas));
	}

	@Override
	public int findCount(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.findCount(${table.javaName}Vo);
	}


	private List<${table.javaName?cap_first}Vo> convertList(List<${table.javaName?cap_first}> ${table.javaName}List) {
		return ${table.javaName}List.stream().map((item) -> {

				${table.javaName?cap_first}Vo ${table.javaName}Vo = new ${table.javaName?cap_first}Vo();

				BeanCopyUtil.copyProperties(item, ${table.javaName}Vo);

				return ${table.javaName}Vo;

			}).collect(Collectors.toList());
	}
}