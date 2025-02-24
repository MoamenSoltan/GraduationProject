package org.example.backend;

import org.example.backend.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class
BackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner(RoleService roleService)
    {
        return args -> {
            // Initialize roles
            roleService.getAllRoles().forEach(System.out::println);
        };
    }

}
