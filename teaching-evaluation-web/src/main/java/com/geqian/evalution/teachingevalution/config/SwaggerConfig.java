package com.geqian.evalution.teachingevalution.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("搁浅")
                //enable swagger是否启动
                .enable(true)
                .select()
                //1.RequestHandlerSelectors 配置扫描方式 2.basePackage() 指定扫描的包 3.any() 扫描全部 4.none() 不扫描 5. withClassAnnotation()
                .apis(RequestHandlerSelectors.basePackage("com.geqian.evalution.teachingevalution.controller"))
                //过滤路径
                //.paths(PathSelectors.ant("/api/**"))
                .build();
                //全局参数
                //.globalOperationParameters(globalParameters());


    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 作者信息
                .contact(new Contact("geqian", "", "2254324470@qq.com"))
                .title("高校评教系统 API 文档")
                .description("高校评教系统项目")
                .version("v1.0")
                .build();
    }
    
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("token", "token", "header"));
        return apiKeyList;
    }

    private List<Parameter> globalParameters(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameterList = new ArrayList<>();
        //第一个token为传参的key，第二个token为swagger页面显示的值
        parameterBuilder.name("token")
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameterList.add(parameterBuilder.build());
        return parameterList;
    }
}