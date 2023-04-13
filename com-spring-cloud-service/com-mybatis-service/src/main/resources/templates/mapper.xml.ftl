<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
</#if>

    <!-- 通用查询映射结果 -->
    <resultMap id="${entity?uncap_first}_result_map" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id property = "${field.propertyName}" column = "${field.name}"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
            <result property = "${field.propertyName}" column = "${field.name}" />
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
                <result property = "${field.propertyName}" column = "${field.name}" />
            </#if>
        </#list>
    </resultMap>

    <select id="query" resultMap="${entity?uncap_first}_result_map" >
        select
        <include refid="basic_column_list"/>
        from `${table.name}`
        <where>
            <#list table.fields as field>
                <#if field.columnType?index_of("DATE") != -1>
            <if test="${field.propertyName} != null">
                        and ${field.name} = DATE_FORMAT(${'#'}{${field.propertyName}}, '%Y-%m-%d %H:%i:%s')
            </if>
                <#elseif field.columnType?index_of("INTEGER") != -1 || field.columnType?index_of("LONG") != -1>
            <if test="${field.propertyName} != null">
                and ${field.name} = ${'#'}{${field.propertyName}}
            </if>
                <#else>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                and ${field.name} like concat('%', ${'#'}{${field.propertyName}}, '%')
            </if>
                </#if>
            </#list>
        </where>
    </select>

    <!-- 通用查询结果列 -->
    <sql id="basic_column_list">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>

    <#--<select id="get" resultMap="${entity?uncap_first}_result_map">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        <where>
            <if test="id != null">
                and id = <#noparse>#{id}</#noparse>
            </if>
        </where>
    </select>

    <delete id="delete">
        delete from ${table.name}
        where id = <#noparse>#{id}</#noparse>
    </delete>

    <insert id="create" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
        insert into ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.fields as field>
        <#if field.columnType?index_of("DATE")!=-1>
            <if test="${field.propertyName}!=null">
                ${field.name},
            </if>
        <#elseif field.columnType?index_of("INTEGER")!=-1 || field.columnType?index_of("LONG")!=-1>
            <if test="${field.propertyName}!=null">
                ${field.name},
            </if>
        <#else>
            <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                ${field.name},
            </if>
        </#if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list table.fields as field>
        <#if field.columnType?index_of("DATE")!=-1>
            <if test="${field.propertyName}!=null">
                ${'#'}{${field.propertyName}},
            </if>
        <#elseif field.columnType?index_of("INTEGER")!=-1 || field.columnType?index_of("LONG")!=-1>
            <if test="${field.propertyName}!=null">
                ${'#'}{${field.propertyName}},
            </if>
        <#else>
            <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                ${'#'}{${field.propertyName}},
            </if>
        </#if>
        </#list>
        </trim>
    </insert>

    <update id="update">
        update ${table.name}
        <set>
        <#list table.fields as field>
        <#if field.columnType?index_of("DATE")!=-1>
            <if test="${field.propertyName}!=null">
                ${field.name} = ${'#'}{${field.propertyName}},
            </if>
        <#elseif field.columnType?index_of("INTEGER")!=-1 || field.columnType?index_of("LONG")!=-1>
            <if test="${field.propertyName}!=null">
                ${field.name} = ${'#'}{${field.propertyName}},
            </if>
        <#else>
            <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                ${field.name} = ${'#'}{${field.propertyName}},
            </if>
        </#if>
        </#list>
        </set>
        where id = <#noparse>#{id}</#noparse>
    </update>-->

</mapper>
