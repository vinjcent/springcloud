package com.vinjcent.config.sentinel;

import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author vinjcent
 * @description Sentinel规则持久化
 * <br>首先Sentinel控制台通过API将规则推送至客户端并更新到内存中，接着注册的写数据源会将新的规则保存在本地的文件中</br>
 * @since 2023/4/28 10:18
 */
@Slf4j
public class RulePersistence implements InitFunc {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void init() throws Exception {
        // sentinel规则目录
        String ruleDir = System.getProperty("usr.home") + "/sentinel-rules/" + appName;

        // 流控规则路径
        String flowRulePath = ruleDir + "/flow-rule.json";

        // 降级规则路径
        String degradeRulePath = ruleDir + "/degrade-rule.json";

        // 系统规则路径
        String systemRulePath = ruleDir + "/system-rule.json";

        // 权限规则路径
        String authorityRulePath = ruleDir + "/authority-rule.json";

        // 参数流控规则路径
        String paramFlowRulePath = ruleDir + "/param-flow-rule.json";

        // 文件夹不存在则创建
        this.mkdirIfNotExist(ruleDir);

        // 创建规则文件
        this.createFileIfNotExist(flowRulePath);
        this.createFileIfNotExist(degradeRulePath);
        this.createFileIfNotExist(systemRulePath);
        this.createFileIfNotExist(authorityRulePath);
        this.createFileIfNotExist(paramFlowRulePath);

        // 流控规则"读"、"写"数据源,并注册到流控管理、写数据源
        ReadableDataSource<String, List<FlowRule>> flowRuleRds = new FileRefreshableDataSource<>(flowRulePath, flowRuleListParser);
        WritableDataSource<List<FlowRule>> flowRuleWds = new FileWritableDataSource<>(flowRulePath, this::encodeJson);
        FlowRuleManager.register2Property(flowRuleRds.getProperty());
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWds);

        // 降级规则"读"、"写"数据源,并注册到降级管理、写数据源
        ReadableDataSource<String, List<DegradeRule>> degradeRuleRds = new FileRefreshableDataSource<>(degradeRulePath, degradeRuleListParser);
        WritableDataSource<List<DegradeRule>> degradeRuleWds = new FileWritableDataSource<>(degradeRulePath, this::encodeJson);
        DegradeRuleManager.register2Property(degradeRuleRds.getProperty());
        WritableDataSourceRegistry.registerDegradeDataSource(degradeRuleWds);

        // 系统规则"读"、"写"数据源,并注册到系统管理、写数据源
        ReadableDataSource<String, List<SystemRule>> systemRuleRds = new FileRefreshableDataSource<>(systemRulePath, systemRuleListParser);
        WritableDataSource<List<SystemRule>> systemRuleWds = new FileWritableDataSource<>(systemRulePath, this::encodeJson);
        SystemRuleManager.register2Property(systemRuleRds.getProperty());
        WritableDataSourceRegistry.registerSystemDataSource(systemRuleWds);

        // 权限规则"读"、"写"数据源,并注册到权限管理、写数据源
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleRds = new FileRefreshableDataSource<>(authorityRulePath, authorityRuleListParser);
        WritableDataSource<List<AuthorityRule>> authorityRuleWds = new FileWritableDataSource<>(authorityRulePath, this::encodeJson);
        AuthorityRuleManager.register2Property(authorityRuleRds.getProperty());
        WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWds);

        // 参数流控规则"读"、"写"数据源,并注册到参数流控管理、写数据源
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleRds = new FileRefreshableDataSource<>(paramFlowRulePath, paramRuleListParser);
        WritableDataSource<List<ParamFlowRule>> paramFlowRuleWds = new FileWritableDataSource<>(paramFlowRulePath, this::encodeJson);
        ParamFlowRuleManager.register2Property(paramFlowRuleRds.getProperty());
        ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramFlowRuleWds);

    }

    /**
     * 流控规则列表转换器
     */
    private final Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<FlowRule>>() {

            }
    );

    /**
     * 降级规则列表转换器
     */
    private final Converter<String, List<DegradeRule>> degradeRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<DegradeRule>>() {

            }
    );

    /**
     * 系统规则列表转换器
     */
    private final Converter<String, List<SystemRule>> systemRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<SystemRule>>() {

            }
    );

    /**
     * 权限规则列表转换器
     */
    private final Converter<String, List<AuthorityRule>> authorityRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<AuthorityRule>>() {

            }
    );

    /**
     * 参数流控规则列表转换器
     */
    private final Converter<String, List<ParamFlowRule>> paramRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<ParamFlowRule>>() {

            }
    );


    /**
     * 文件夹不存在则创建
     *
     * @param filePath 文件路径
     */
    private void mkdirIfNotExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                log.error("Sentinel持久化规则'文件夹'创建失败");
            }
        }
    }

    /**
     * 文件不存在则创建
     *
     * @param filePath 文件路径
     */
    private void createFileIfNotExist(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.createNewFile();
            if (!result) {
                log.error("=====Sentinel持久化规则'文件'创建失败=====");
            }
        }
    }

    /**
     * 将实体转为json字符串
     *
     * @param object 实体
     * @param <T>    实体类型
     * @return 字符串
     */
    private <T> String encodeJson(T object) {
        return JSON.toJSONString(object);
    }
}
