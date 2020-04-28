package ${package!""}.entity.db;

import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${table.javaName?cap_first!""} implements Serializable {

    /**
     * version: ${table.comment!""}
     *----------------------
     * author: Mr.sanq
     * date: ${nowDate?string("yyyy-MM-dd")}
     */
    private static final long serialVersionUID = 1L;

<#if fields?? && fields?size gt 0>
    <#list fields as field>
    /**
      * ${field.columnComment!""}
      */
    private ${field.javaType!""} ${field.javaField!""};
    </#list>
</#if>
}
