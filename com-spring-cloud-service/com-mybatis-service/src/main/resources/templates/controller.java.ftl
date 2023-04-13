package ${package.Controller};

import com.github.pagehelper.PageInfo;
import com.vinjcent.api.status.response.ResponseResult;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */

@RestController
<#--@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")-->
@RequestMapping("/${entity?uncap_first}")
@Api(tags = "${table.comment!}")
public class ${table.controllerName} {

    private final ${table.serviceName} ${table.serviceName?uncap_first};

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${table.serviceName?uncap_first}) {
        this.${table.serviceName?uncap_first} = ${table.serviceName?uncap_first};
    }

    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ResponseResult<?> create(@RequestBody ${entity} ${entity?uncap_first}) { return ${table.serviceName?uncap_first}.create(${entity?uncap_first}); }

    @ApiOperation(value = "根据ID集合批量删除", httpMethod = "POST")
    @PostMapping(value = "/deleteBatch")
    public ResponseResult<?> deleteBatch(@RequestBody List<Long> ids) {
        return ${table.serviceName?uncap_first}.deleteBatch(ids);
    }

    @ApiOperation(value = "修改", httpMethod = "POST")
    @PostMapping(value = "/update")
    public ResponseResult<?> update(@RequestBody ${entity} ${entity?uncap_first}) {
        return ${table.serviceName?uncap_first}.update(${entity?uncap_first});
    }

    @ApiOperation(value = "根据主键ID查询", httpMethod = "GET")
    @GetMapping(value = "/get")
    public ResponseResult<${entity}> get(@ApiParam(value="id") @RequestParam(value = "id") Long id) {
        return ResponseResult.success(${table.serviceName?uncap_first}.get(id));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "当前页码", required = true, dataType = "int"),
        @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", required = true, dataType = "int")
    })
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "/query")
    public ResponseResult
    <PageInfo<#noparse><</#noparse>${entity}>> query(@RequestBody ${entity} ${entity?uncap_first}, int pageNum, int pageSize) {
        PageInfo<#noparse><</#noparse>${entity}> pageInfo = ${table.serviceName?uncap_first}.query(${entity?uncap_first}, pageNum, pageSize);
        return ResponseResult.success(pageInfo);
    }
}
