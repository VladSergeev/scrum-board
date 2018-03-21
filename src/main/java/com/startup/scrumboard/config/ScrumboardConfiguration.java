
package com.startup.scrumboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.startup.scrumboard.Application.API;
import static springfox.documentation.builders.PathSelectors.ant;


@EnableSwagger2
@Configuration
public class ScrumboardConfiguration {


//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod(HttpMethod.POST);
//        config.addAllowedMethod(HttpMethod.DELETE);
//        config.addAllowedMethod(HttpMethod.PUT);
//        config.addAllowedMethod(HttpMethod.GET);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("all")
                .apiInfo(apiInfo())
                .select()
                .paths(ant(API + "/**"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Scrumboard")
                .version("1.0")
                .build();
    }


}