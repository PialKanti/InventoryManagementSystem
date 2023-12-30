package com.codecrafters.hub.inventorymanagementsystem.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pial Kanti Samadder",
                        email = "pialkanti2012@gmail.com"
                ),
                description = "OpenApi documentation for Inventory Management System",
                title = "Inventory Management System",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/license/mit/"
                )
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
