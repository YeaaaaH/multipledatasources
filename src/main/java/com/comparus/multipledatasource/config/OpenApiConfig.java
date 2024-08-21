package com.comparus.multipledatasource.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import java.util.Map;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setUrl("https://github.com/YeaaaaH");

        Info info = new Info()
                .title("Multiple data source api example")
                .version("2.0")
                .contact(contact)
                .description("Declarative configuration for specification of data sources with spring jdbc");

        return new OpenAPI()
                .info(info)
                .components(
                        new Components()
                                .addSchemas(
                                        "queryRequestMap", new Schema<Map<String, String>>()
                                                .addProperty("datasource", new StringSchema().example("postgres"))
                                                .addProperty("id", new StringSchema().example("string_id_example"))
                                                .addProperty("username", new StringSchema().example("username_example"))
                                                .addProperty("name", new StringSchema().example("name_example"))
                                                .addProperty("surname", new StringSchema().example("surname_example"))
                                )
                );
    }
}
