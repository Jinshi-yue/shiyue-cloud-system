package com.shiyue.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    /**
     * 子系统
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本号
     */
    private String version;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * api包路径
     */
    private String basePackage;
}
