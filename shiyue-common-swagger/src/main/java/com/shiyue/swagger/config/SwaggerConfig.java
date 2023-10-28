package com.shiyue.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: shuoxue
 * @CreateTime: 2023-10-19  00:24
 * @Description: TODO
 * @Version: 1.0
 */

/**
 * 作用: 自动生成API文档和在线接口调试工具
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})
@ConditionalOnProperty(prefix = "swagger", value = "enable", matchIfMissing = true)
public class SwaggerConfig {

    /**
     * 创建一个Docket的对象，相当于是swagger的一个实例 ： 配置开发和测试环境下开启Swagger，生产发布时关闭
     * <p>
     * RequestHandlerSelectors,配置要扫描接口的方式
     * basePackage：指定扫描的包路径
     * any：扫描全部
     * none：全部不扫描
     * withClassAnnotation:扫描类上的注解，如RestController
     * withMethodAnnotation:扫描方法上的注解，如GetMapping
     *
     * @return
     */
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi(Environment environment) {
        //设置显示的swagger环境信息,判断是否处在自己设定的环境当中,为了安全生产环境不开放Swagger
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(profiles);
        //创建一个Docket的对象，相当于是swagger的一个实例
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(swaggerProperties.getGroupName())
                .apiInfo(apiInfo())
                //只有当springboot配置文件为dev或test环境时，才开启swaggerAPI文档功能
                .enable(flag)
                .select()
                // 这里指定Controller扫描包路径:设置要扫描的接口类，一般是Controller类
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
//                .apis(RequestHandlerSelectors.basePackage("com.shiyue.common.controller"))  //这里采用包扫描的方式来确定要显示的接口
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //这里采用包含注解的方式来确定要显示的接口
                // 配置过滤哪些，设置对应的路径才获取
                .paths(PathSelectors.any())
                .build();
    }

    ///配置相关的api信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .title(swaggerProperties.getTitle())
                //服务Url
                .termsOfServiceUrl("")
                .build();
    }


}
