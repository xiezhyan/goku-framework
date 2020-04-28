<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package!""}.mapper.${table.javaName?cap_first}Mapper">

    <resultMap type="${table.javaName?cap_first}" id="${table.javaName}Map">
<#list fields as field>
    <#if field.columnKey == "PRI">
        <id column="${field.columnName}" property="${field.javaField}"/>
    <#else>
        <result column="${field.columnName}" property="${field.javaField}"/>
    </#if>
</#list>
    </resultMap>

    <sql id="${table.name}_columns">
        <#list fields as field> ${table.name}.${field.columnName}<#if field_index + 1 != fields?size>,</#if></#list>
    </sql>

    <sql id="${table.name}_where">
        <if test="query != null">
    <#list fields as field>
        <#if (field.javaType!"") == "String">
            <if test="query.${field.javaField} !=null and query.${field.javaField} != ''">
                <#if field_index != 0>AND </#if>${table.name}.${field.columnName} LIKE
                CONCAT('%',${r"#{query" + "." + field.javaField + "}"},'%')
            </if>
        <#else>
            <if test="query.${field.javaField} !=null ">
                <#if field_index != 0>AND </#if>${table.name}.${field.columnName}
                = ${r"#{query" + "." + field.javaField + "}"}
            </if>
        </#if>
    </#list>
        </if>
    </sql>

    <sql id="${table.name}_del_where">
<#list fields as field>
    <#if (field.javaType!"") == "String">
        <if test="${field.javaField} !=null and ${field.javaField} != ''">
            <#if field_index != 0>AND </#if>${field.columnName} LIKE CONCAT('%',${r"#{" + field.javaField + "}"}
            ,'%')
        </if>
    <#else>
        <if test="${field.javaField} !=null ">
            <#if field_index != 0>AND </#if>${field.columnName} = ${r"#{" + field.javaField + "}"}
        </if>
    </#if>
</#list>
    </sql>

    <select id="findById" resultMap="${table.javaName}Map"
            parameterType="<#list fields as field><#if field.columnKey == "PRI">${field.javaType}</#if></#list>">
        SELECT
        <include refid="${table.name}_columns"/>
        FROM ${table.name} ${table.name}
        WHERE
    <#list fields as field>
        <#if field.columnKey == "PRI">
            ${table.name}.${field.columnName} = ${r"#{id}"}
        </#if>
    </#list>
        LIMIT 1
    </select>

    <select id="findList" resultMap="${table.javaName}Map">
        SELECT
        <include refid="${table.name}_columns"/>
        FROM ${table.name} ${table.name}
        <where>
            <include refid="${table.name}_where"/>
        </where>
    </select>

    <select id="findListByPage" resultMap="${table.javaName}Map">
        SELECT
        <include refid="${table.name}_columns"/>
        FROM ${table.name}  ${table.name}
        <where>
            <include refid="${table.name}_where"/>
        </where>
        LIMIT ${r"#{startPage}"},${r"#{pageSize}"}
    </select>

    <select id="findCount" resultType="java.lang.Integer">
        SELECT
        COUNT(*)
        FROM ${table.name} ${table.name}
        <where>
            <include refid="${table.name}_where"/>
        </where>
    </select>

    <delete id="delete">
        DELETE FROM ${table.name}
        <where>
            <include refid="${table.name}_del_where"/>
        </where>
    </delete>

    <update id="update" parameterType="${table.javaName?cap_first}">
        UPDATE ${table.name}
        <set>
        <#list fields as field>
            <if test="entity.${field.javaField} !=null ">
                ${field.columnName} = ${r"#{" + "entity." + field.javaField + "}"},
            </if>
        </#list>
        </set>
        WHERE
    <#list fields as field>
        <#if field.columnKey == "PRI">
            ${field.columnName} = ${r"#{" + field.javaField + "}"}
        </#if>
    </#list>
    </update>

    <insert id="save" parameterType="${table.javaName?cap_first}">
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as field>
            <if test="${field.javaField} !=null ">
                ${field.columnName},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list fields as field>
            <if test="${field.javaField} !=null ">
                ${r"#{" + field.javaField + "}"},
            </if>
        </#list>
        </trim>
    </insert>

    <insert id="saveByList">
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as field>
            ${field.columnName},
        </#list>
        </trim>
        VALUES
        <foreach collection="saves" item="${table.javaName}Vo" separator=",">
        (
        <#list fields as field>
            ${r"#{" + table.javaName + "Vo." + field.javaField + "}"}<#if field_index + 1 != fields?size>,</#if>
        </#list>
        )
        </foreach>
    </insert>
</mapper>