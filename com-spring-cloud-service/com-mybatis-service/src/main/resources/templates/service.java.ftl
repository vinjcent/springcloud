package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.vinjcent.api.status.response.ResponseResult;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * <p>
 *  ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

   /**
    * 新增实体
    * @param ${entity?uncap_first}
    * @return 响应结果
    */
    ResponseResult<?> create(${entity} ${entity?uncap_first});

   /**
    * 批量删除
    * @param ids 主表id集合
    * @return 响应结果
    */
    ResponseResult<?> deleteBatch(List<Long> ids);

   /**
    * 更新实体
    * @param ${entity?uncap_first}
    * @return 响应结果
    */
    ResponseResult<?> update(${entity} ${entity?uncap_first});

   /**
    * 查询单个实体
    * @param id 主表id
    * @return 响应结果
    */
    ${entity} get(Long id);

   /**
    * 分页查询
    * @param ${entity?uncap_first}
    * @param pageNum 页码
    * @param pageSize 页大小
    * @return 分页结果
    */
    PageInfo<${entity}> query(${entity} ${entity?uncap_first}, int pageNum, int pageSize);

}
</#if>