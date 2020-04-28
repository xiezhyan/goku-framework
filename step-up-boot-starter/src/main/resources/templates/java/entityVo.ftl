package ${package!""}.entity.vo;

import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${table.javaName?cap_first!""}Vo implements Serializable {

	/**
	 * version: ${table.tableComment!""}
	 *----------------------
	 * author: Mr.sanq
	 * date: ${nowDate?string("yyyy-MM-dd")}
	 */

<#if fields?? && fields?size gt 0>
	<#list fields as field>
	/**
	 * ${field.columnComment!""}
	 */
	private ${field.javaType!""} ${field.javaColumnName!""};
	</#list>
</#if>
}
