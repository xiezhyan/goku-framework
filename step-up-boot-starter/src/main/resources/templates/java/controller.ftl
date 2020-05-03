package ${package!""}.controller;

import org.springframework.web.bind.annotation.PathVariable;

import ${package}.entity.vo.${table.javaName?cap_first}Vo;
import ${package}.service.${table.javaName?cap_first}Service;

import top.zopx.starter.tools.annotations.*;
import top.zopx.starter.tools.basic.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *	version: ${table.tableComment!""}
 * ----------------------
 * 	author: Mr.sanq
 * 	date: ${nowDate?string("yyyy-MM-dd")}
 */
@RestController
@RequestMapping("/api/${table.javaName}")
public class ${table.javaName?cap_first}Controller {

	@Resource
	private ${table.javaName?cap_first}Service ${table.javaName}Service;

	@LogAnnotation(description = "通过ID查询详情-${table.tableComment!""}")
	@GetMapping("/{id}")
	@AuthorityAnnotation(keys={"${table.javaName}:get"})
	public Response get(@PathVariable("id") <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {
		// 查询详情
		${table.javaName?cap_first}Vo ${table.javaName}Vo = ${table.javaName}Service.findById(<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>);

		return ${table.javaName}Vo != null ?
                    Response.builder().build().success(${table.javaName}Vo) :
                    Response.builder().build().failure(String.format("ID：%s no data ", <#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>));
	}

	@LogAnnotation(description = "查询列表-${table.tableComment!""}")
	@GetMapping(value="/")
	@AuthorityAnnotation(keys={"${table.javaName}:list"})
	public Response findList(${table.javaName?cap_first}Vo ${table.javaName}Vo, Pagination pagination) {

		if (null != pagination && 0 < pagination.getCurrentIndex()) {
			// 分页查询列表
			Page<${table.javaName?cap_first}Vo> page = ${table.javaName}Service.findListByPage(${table.javaName}Vo, pagination);

			return Response.builder().build().success(page);
		}

		List<${table.javaName?cap_first}Vo> list = ${table.javaName}Service.findList(${table.javaName}Vo);

		return Response.builder().build().success(list);
	}

	@LogAnnotation(description = "通过ID删除-${table.tableComment!""}")
	@DeleteMapping(value="/{id}")
	@AuthorityAnnotation(keys={"${table.javaName}:delete"})
	public Response deleteById(@PathVariable("id") <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {

		${table.javaName?cap_first}Vo ${table.javaName}Vo = new ${table.javaName?cap_first}Vo();
		${table.javaName}Vo.set<#list fields as field><#if field.priKey>${field.javaColumnName?cap_first}(<#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>)</#if></#list>;

		int result = ${table.javaName}Service.delete(${table.javaName}Vo);

		return result != 0 ?
					Response.builder().build().success() :
					Response.builder().build().failure(String.format("删除ID：%s出错", <#list fields as field><#if field.columnKey == "PRI">${field.javaColumnName}</#if></#list>));
	}

	@LogAnnotation(description = "新增-${table.tableComment!""}")
	@PostMapping(value="/")
	@AuthorityAnnotation(keys={"${table.javaName}:save"})
	public Response add(@RequestBody ${table.javaName?cap_first}Vo ${table.javaName}Vo) {

		int result = ${table.javaName}Service.save(${table.javaName}Vo);

		return result != 0 ?
					Response.builder().build().success() :
					Response.builder().build().failure();
	}

	@LogAnnotation(description = "通过ID修改-${table.tableComment!""}")
	@PutMapping(value = "/{id}")
	@AuthorityAnnotation(keys={"${table.javaName}:update"})
	public Response updateByKey(@RequestBody ${table.javaName?cap_first}Vo ${table.javaName}Vo,
								@PathVariable("id") <#list fields as field><#if field.priKey>${field.javaType} ${field.javaColumnName}</#if></#list>) {

		int result = ${table.javaName}Service.update(${table.javaName}Vo, <#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>);

		return result != 0 ?
                    Response.builder().build().success() :
                    Response.builder().build().failure(String.format("修改ID：%s error", <#list fields as field><#if field.priKey>${field.javaColumnName}</#if></#list>));
	}
}