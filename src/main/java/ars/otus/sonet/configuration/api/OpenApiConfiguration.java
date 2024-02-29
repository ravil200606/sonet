package ars.otus.sonet.configuration.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API учебного проекта социальной сети SoNet.")
                        .description("Вызовы REST доступные в проекте.")
                        .version("1.0"));
    }
}
