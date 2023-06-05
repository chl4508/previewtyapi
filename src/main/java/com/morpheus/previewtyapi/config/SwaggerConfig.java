package com.morpheus.previewtyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Profile(value = {"!real"})
@Configuration
@EnableSwagger2
public class SwaggerConfig{

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Previewty Api Server")
                .description("Previewty API 명세서")
                .contact(new Contact("Previewty", "https://dev.morpheus3d.co.kr/","ch14508@morpheus3d.co.kr"))
                .version("1.0")
                .build();
    }

    private Set<String> getConsumContentTypes(){
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF_8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes(){
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    @Bean
    public Docket commonApi(ServletContext servletContext){
        return new Docket(DocumentationType.SWAGGER_2)
//                .host("api.previewty.co.kr")
//                .pathProvider(new RelativePathProvider(servletContext) {
//                    @Override
//                    public String getApplicationBasePath() {
//                        return "/api";
//                    }
//                })
                .groupName("v1")
                .consumes(getConsumContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.base.swagger.api.v1"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
//                .ignoredParameterTypes(UserVO.class)
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    @Bean
    public Docket commonApi2(ServletContext servletContext){
        return new Docket(DocumentationType.SWAGGER_2)
//                .host("api.previewty.co.kr")
//                .pathProvider(new RelativePathProvider(servletContext) {
//                    @Override
//                    public String getApplicationBasePath() {
//                        return "/api";
//                    }
//                })
                .groupName("v2")
                .consumes(getConsumContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.base.swagger.api.v1"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v2/api/**"))
                .build()
//                .ignoredParameterTypes(UserVO.class)
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }



    private ApiKey apiKey() {
        return new ApiKey("access_token", "access_token", "header");
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("access_token", authorizationScopes));
    }

}
