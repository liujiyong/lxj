package com.lxj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置文件
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo());
    }

    @Bean
    public Docket api2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("v2").select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v2/**")).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("来新居工程管理APP API手册").contact(new Contact("lxj", "www.lxj.com", "lxj@lxj.com")).version("1.0").description("API 描述").build();
        return apiInfo;
    }

}
