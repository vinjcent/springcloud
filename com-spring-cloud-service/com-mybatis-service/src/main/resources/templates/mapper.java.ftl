package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
 *  ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    /**
     * 查询
     * @param ${entity?uncap_first} 实体条件
     * @return 实体集合
     */
    List<${entity}> query(${entity} ${entity?uncap_first});

}
</#if>
