package com.ecommerce.ecommerce.config;

import org.springframework.context.annotation.Configuration;

// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
// @EnableSwagger2  // <-- Comentado para desactivar Swagger
public class SwaggerConfiguration {

    // @Bean
    // public Docket deofisApi() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .select()
    //             .apis(RequestHandlerSelectors.any())
    //             .paths(PathSelectors.any())
    //             .build()
    //             .apiInfo(getApiInfo());
    // }

    // private ApiInfo getApiInfo() {
    //     return new ApiInfoBuilder()
    //             .title("Deofis Tienda API")
    //             .version("1.0")
    //             .description("API para la tienda DEOFIS")
    //             .license("Apache License Version 2.0")
    //             .build();
    // }
}
