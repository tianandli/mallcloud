package com.lijie.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：
 *
 * @author: lijie
 * @date: 2021/5/20 17:19
 * @version: V1.0
 */
@RestController
@RefreshScope//用于刷新配置
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/getConfigInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}
