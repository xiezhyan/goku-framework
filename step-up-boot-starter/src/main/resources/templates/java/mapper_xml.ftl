<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package!""}.mapper.${table.javaName?cap_first}Mapper">

    <resultMap type="${table.javaName?cap_first}" id="${table.javaName}Map">
<#list fields as field>
    <#if field.priKey>
        <id column="${field.columnName}" property="${field.javaColumnName}"/>
    <#else>
        <result column="${field.columnName}" property="${field.javaColumnName}"/>
    </#if>
</#list>
    </resultMap>

    <sql id="${table.tableName}_columns">
        <#list fields as field> ${table.tableName}.${field.columnName}<#if field_index + 1 != fields?size>,</#if></#list>
    </sql>

    <sql id="${table.tableName}_where">
        <if test="${table.javaName} != null">
    <#list fields as field>
        <#if (field.javaType!"") == "String">
            <if test="${table.javaName}.${field.javaColumnName} !=null and ${table.javaName}.${field.javaColumnName} != ''">
                <#if field_index != 0>AND </#if>${table.tableName}.${field.columnName} LIKE CONCAT(${r"#{" + "${table.javaName}." + field.javaColumnName + "}"},'%')
            </if>
        <#else>
            <if test="${table.javaName}.${field.javaColumnName} !=null ">
                <#if field_index != 0>AND </#if>${table.tableName}.${field.columnName} = ${r"#{" + "${table.javaName}." + field.javaColumnName + "}"}
            </if>
        </#if>
    </#list>
        </if>
    </sql>

    <sql id="${table.tableName}_del_where">
<#list fields as field>
    <#if (field.javaType!"") == "String">
        <if test="${field.javaColumnName} !=null and ${field.javaColumnName} != ''">
            <#if field_index != 0>AND </#if>${field.columnName} LIKE CONCAT(${r"#{" + field.javaColumnName + "}"},'%')
        </if>
    <#else>
        <if test="${field.javaColumnName} !=null ">
            <#if field_index != 0>AND </#if>${field.columnName} = ${r"#{" + field.javaColumnName + "}"}
        </if>
    </#if>
</#list>
    </sql>

    <select id="findById" resultMap="${table.javaName}Map"
            parameterType="<#list fields as field><#if field.priKey>${field.javaType}</#if></#list>">
        SELECT
        <include refid="${table.tableName}_columns"/>
        FROM ${table.tableName} ${table.tableName}
        WHERE
    <#list fields as field>
        <#if field.priKey>
            ${table.tableName}.${field.columnName} = ${r"#{id}"}
        </#if>
    </#list>
        LIMIT 1
    </select>

    <select id="findList" resultMap="${table.javaName}Map">
        SELECT
        <include refid="${table.tableName}_columns"/>
        FROM ${table.tableName} ${table.tableName}
        <where>
            <include refid="${table.tableName}_where"/>
        </where>
    </select>

    <select id="findListByPage" resultMap="${table.javaName}Map">
        SELECT
        <include refid="${table.tableName}_columns"/>
        FROM ${table.tableName}  ${table.tableName}
        <where>
            <include refid="${table.tableName}_where"/>
        </where>
        LIMIT ${r"#{startPage}"},${r"#{pageSize}"}
    </select>

    <select id="findCount" resultType="java.lang.Integer">
        SELECT
        COUNT(*)
        FROM ${table.tableName} ${table.tableName}
        <where>
            <include refid="${table.tableName}_where"/>
        </where>
    </select>

    <delete id="delete">
        DELETE FROM ${table.tableName}
        <where>
            <include refid="${table.tableName}_del_where"/>
        </where>
    </delete>

    <update id="update" parameterType="${table.javaName?cap_first}">
        UPDATE ${table.tableName}
        <set>
        <#list fields as field>
            <if test="${table.javaName}.${field.javaColumnName} !=null ">
                ${field.columnName} = ${r"#{" + "${table.javaName}." + field.javaColumnName + "}"},
            </if>
        </#list>
        </set>
        WHERE
    <#list fields as field>
        <#if field.priKey>
            ${field.columnName} = ${r"#{" + field.javaColumnName + "}"}
        </#if>
    </#list>
    </update>

    <insert id="save" parameterType="${table.javaName?cap_first}">
        INSERT INTO ${table.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as field>
            <if test="${field.javaColumnName} !=null ">
                ${field.columnName},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list fields as field>
            <if test="${field.javaColumnName} !=null ">
                ${r"#{" + field.javaColumnName + "}"},
            </if>
        </#list>
        </trim>
    </insert>

    <insert id="saveByList">
        INSERT INTO ${table.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fields as field>
            ${field.columnName},
        </#list>
        </trim>
        VALUES
        <foreach collection="${table.javaName}List" item="${table.javaName}" separator=",">
        (
        <#list fields as field>
            ${r"#{" + table.javaName + "." + field.javaColumnName + "}"}<#if field_index + 1 != fields?size>,</#if>
        </#list>
        )
        </foreach>
    </insert>
</mapper>