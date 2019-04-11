package com.example.demoloadtestingapp.configuration;

import com.example.demoloadtestingapp.security.SecurityConstants;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        List<Parameter> parameterList = Lists.newArrayList(
                new ParameterBuilder()
                        .name(SecurityConstants.HEADER)
                        .description("Please enter JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .globalOperationParameters(parameterList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demoloadtestingapp.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo Load Testing App 1")
                .contact(new Contact("Howard", "", "idontwannarock@gmail.com"))
                .version("v1")
                .build();
    }
}
