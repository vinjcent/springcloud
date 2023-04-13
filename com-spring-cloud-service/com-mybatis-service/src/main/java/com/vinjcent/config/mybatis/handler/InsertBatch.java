package com.vinjcent.config.mybatis.handler;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author vinjcent
 * @description 批量插入自动映射方法
 * @since 2023/3/22 11:54
 */
@Slf4j
public class InsertBatch extends AbstractMethod {

    /**
     * insert into user(id, name, age) values (1, "a", 17), (2, "b", 18);
     * <script>
     * insert into user(id, name, age) values
     * <foreach collection="list" item="item" index="index" open="(" separator="),(" close=")">
     * #{item.id}, #{item.name}, #{item.age}...
     * </foreach>
     * </script>
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // SQL执行脚本
        final String sql = "<script>insert into %s %s values %s</script>";
        // 数据库映射字段
        final String fieldSql = prepareColumnsSql(tableInfo);
        // 对应数据库字段占位符的值
        final String valueSql = prepareValuesSql(tableInfo);
        // 格式化SQL脚本
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        log.debug("sqlResult -----> {}", sqlResult);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        // 第三个参数必须和RootMapper的自定义方法名一致
        return this.addInsertMappedStatement(mapperClass, modelClass, "insertBatch", sqlSource, new NoKeyGenerator(), null, null);
    }

    /**
     * 拼接插入数据字段
     *
     * @param tableInfo 表信息
     * @return 插入字段对应值拼接字符串
     */
    private String prepareValuesSql(TableInfo tableInfo) {
        StringBuilder valuesSql = new StringBuilder();
        valuesSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
        // 提取主键
        valuesSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
        tableInfo.getFieldList().forEach(field -> valuesSql.append("#{item.").append(field.getProperty()).append("},"));
        // 去除最后一个逗号
        valuesSql.delete(valuesSql.length() - 1, valuesSql.length());
        // foreach闭合标签
        valuesSql.append("</foreach>");
        return valuesSql.toString();
    }

    /**
     * 拼接表字段
     *
     * @param tableInfo 表信息
     * @return 插入字段列表拼接字符串
     */
    private String prepareColumnsSql(TableInfo tableInfo) {
        StringBuilder columnsSql = new StringBuilder();
        // 提取主键
        columnsSql.append(tableInfo.getKeyColumn()).append(",");
        tableInfo.getFieldList().forEach(x -> columnsSql.append(x.getColumn()).append(","));
        // 去除最后一个逗号
        columnsSql.delete(columnsSql.length() - 1, columnsSql.length());
        columnsSql.insert(0, "(");
        columnsSql.append(")");
        return columnsSql.toString();
    }
}
