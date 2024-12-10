package com.codecrafters.hub.inventorymanagementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String JWT_SECURITY_SCHEME_NAME = "JWT Token";
    private static final String BEARER_SCHEME_TYPE = "bearer";
    private static final String JWT_BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(apiInfo());
        openAPI.addSecurityItem(new SecurityRequirement().addList(JWT_SECURITY_SCHEME_NAME));
        openAPI.components(securityComponents());

        return openAPI;
    }

    private Info apiInfo() {
        Info info = new Info();
        info.setTitle("Inventory Management System");
        info.setDescription("OpenApi documentation for Inventory Management System");
        info.setContact(apiContactDetails());
        info.setVersion("1.0");
        info.setLicense(apiLicense());

        return info;
    }

    private Contact apiContactDetails() {
        Contact contact = new Contact();
        contact.setName("Pial Kanti Samadder");
        contact.setEmail("pialkanti2012@gmail.com");

        return contact;
    }

    private License apiLicense() {
        License license = new License();
        license.setName("MIT License");
        license.setUrl("https://opensource.org/license/mit/");

        return license;
    }

    private Components securityComponents() {
        Components components = new Components();
        components.addSecuritySchemes(JWT_BEARER_FORMAT, jwtSecurityScheme());

        return components;
    }

    private SecurityScheme jwtSecurityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName(JWT_SECURITY_SCHEME_NAME);
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.scheme(BEARER_SCHEME_TYPE);
        securityScheme.bearerFormat(JWT_BEARER_FORMAT);

        return securityScheme;
    }
}
