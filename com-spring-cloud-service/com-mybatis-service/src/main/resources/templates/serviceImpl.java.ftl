package ${package.ServiceImpl};

import ${superServiceImplClassPackage};
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vinjcent.api.status.response.ResponseResult;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    private final ${table.mapperName} ${table.mapperName?uncap_first};

    @Autowired
    public ${table.serviceImplName}(${table.mapperName} ${table.mapperName?uncap_first}) {
        this.${table.mapperName?uncap_first} = ${table.mapperName?uncap_first};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> create(${entity} ${entity?uncap_first}) {
        save(${entity?uncap_first});
        return ResponseResult.success(${entity?uncap_first}.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> deleteBatch(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> update(${entity} ${entity?uncap_first}) {
        updateById(${entity?uncap_first});
        return ResponseResult.success();
    }

    @Override
    public ${entity} get(Long id) {
        return getById(id);
    }

    @Override
    public PageInfo<${entity}> query(${entity} ${entity?uncap_first}, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(${table.mapperName?uncap_first}.query(${entity?uncap_first}));
    }
}
</#if>
