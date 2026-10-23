package com.mizu.inventory.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

  private static final String SCHEME = "bearerAuth";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(SCHEME))
        .components(new Components().addSecuritySchemes(SCHEME,
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }
}
