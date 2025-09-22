package dev.hwiveloper.orbitlink.common.config;

import dev.hwiveloper.orbitlink.common.security.TokenService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
        info = @Info(title = "Orbit Link API",
        description = "Orbit Link API 명세서",
        version = "1.0.0",
        contact = @Contact(
            name = "STUDIO HWI",
            email = "studio.hwi.app@gmail.com"
        ))
)
@Configuration
@Profile("dev")
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("JWT")
            .addList(TokenService.REFRESH_TOKEN_NAME);

        String BEARER_TOKEN_PREFIX = "Bearer";

        SecurityScheme securityScheme = new SecurityScheme()
            .name("JWT")
            .type(Type.HTTP)
            .scheme(BEARER_TOKEN_PREFIX)
            .bearerFormat("JWT");

        SecurityScheme refreshSecurityScheme = new SecurityScheme()
            .name(TokenService.REFRESH_TOKEN_NAME)
            .description("Orbit Link Refresh Token")
            .in(In.HEADER)
            .type(Type.APIKEY)
            ;

        Components components = new Components()
            .addSecuritySchemes("JWT", securityScheme)
            .addSecuritySchemes(TokenService.REFRESH_TOKEN_NAME, refreshSecurityScheme);

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
