package ${package!""}.service.impl;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import ${package}.entity.db.${table.javaName?cap_first};
import com.sanq.product.cab.basic.Page;
import com.sanq.product.cab.basic.Pagination;
import ${package}.service.${table.javaName?cap_first}Service;
import ${package}.mapper.${table.javaName?cap_first}Mapper;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.sanq.product.cab.exceptions.BusException;
import com.sanq.product.cab.tools.copy.BeanCopyUtils;
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

	@Override
	@Transactional
	public int save(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.save(vo2Db(${table.javaName}Vo));
	}

	@Override
	@Transactional
	public int delete(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.delete(${table.javaName}Vo);
	}

	@Override
	@Transactional
	public int update(${table.javaName?cap_first}Vo ${table.javaName}Vo, <#list fields as field><#if field.columnKey == "PRI">${field.javaType} ${field.javaField}</#if></#list>) {
		${table.javaName?cap_first} old${table.javaName?cap_first} = ${table.javaName}Mapper.findById(<#list fields as field><#if field.columnKey == "PRI">${field.javaField}</#if></#list>);

		if(null != old${table.javaName?cap_first} && null != ${table.javaName}Vo) {

			BeanCopyUtils.copyProperties(${table.javaName}Vo, old${table.javaName?cap_first});

			return ${table.javaName}Mapper.update(old${table.javaName?cap_first}, <#list fields as field><#if field.columnKey == "PRI">${field.javaField}</#if></#list>);
		}
		return 0;
	}

	@Override
	public ${table.javaName?cap_first}Vo findById(<#list fields as field><#if field.columnKey == "PRI">${field.javaType} ${field.javaField}</#if></#list>) {
		return db2Vo(${table.javaName}Mapper.findById(<#list fields as field><#if field.columnKey == "PRI">${field.javaField}</#if></#list>));
	}

	@Override
	public List<${table.javaName?cap_first}Vo> findList(${table.javaName?cap_first}Vo ${table.javaName}Vo) {

        List<${table.javaName?cap_first}> datas = ${table.javaName}Mapper.findList(${table.javaName}Vo);

		return list2Vo(datas);
	}

	@Override
	public Page<${table.javaName?cap_first}Vo> findListByPage(${table.javaName?cap_first}Vo ${table.javaName}Vo,Pagination pagination) {
		pagination.setTotalCount(findCount(${table.javaName}Vo));

		List<${table.javaName?cap_first}> datas = ${table.javaName}Mapper.findListByPage(${table.javaName}Vo, pagination.getStartPage(), pagination.getPageSize());

		return new Page<${table.javaName?cap_first}Vo>(pagination,list2Vo(datas));
	}

	@Override
	public int findCount(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		return ${table.javaName}Mapper.findCount(${table.javaName}Vo);
	}

	// 将前端传过来的数据VO转换成DB类型
	private ${table.javaName?cap_first} vo2Db(${table.javaName?cap_first}Vo ${table.javaName}Vo) {
		${table.javaName?cap_first} ${table.javaName} = new ${table.javaName?cap_first}();
		BeanCopyUtils.copyProperties(${table.javaName}Vo, ${table.javaName});
		return ${table.javaName};
	}

	// 将从数据库中查询出来的DB类型转成前端需要的VO
	private ${table.javaName?cap_first}Vo db2Vo(${table.javaName?cap_first} ${table.javaName}) {
		${table.javaName?cap_first}Vo ${table.javaName}Vo = new ${table.javaName?cap_first}Vo();

		if (null != ${table.javaName})
			BeanCopyUtils.copyProperties(${table.javaName}, ${table.javaName}Vo);

		return ${table.javaName}Vo;
	}

	private List<${table.javaName?cap_first}Vo> list2Vo(List<${table.javaName?cap_first}> ${table.javaName}List) {
		return ${table.javaName}List.stream().map((item) -> {
				${table.javaName?cap_first}Vo ${table.javaName}Vo = new ${table.javaName?cap_first}Vo();

				BeanCopyUtils.copyProperties(item, ${table.javaName}Vo);
				return ${table.javaName}Vo;

			}).collect(Collectors.toList());
	}
}