package com.demo.ibk.business.customer.infrastructure.configuration;

import com.demo.ibk.business.customer.infrastructure.constant.SwaggerConstant;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("springshop-public")
        .packagesToScan("com.demo.ibk.business.customer") //scan if there are any rest service on packages that match the pattern
        .build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("API Business Customer V1")
            .description(SwaggerConstant.API_DESCRIPTION)
            .version("1.0.0"))
        .addTagsItem(new Tag().name("customers").description("Operations related to customer domain"))
        .externalDocs(new ExternalDocumentation()
            .description("Documentation of API Business Customer V1")
            .url("https://ibk-confluence/documentation/api/business-customer-v1.com"));
  }
}