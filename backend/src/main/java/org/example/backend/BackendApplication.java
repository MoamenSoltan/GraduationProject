package org.example.backend;

import org.example.backend.entity.Department;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);


    }

  // @Bean
//   CommandLineRunner commandLineRunner(PasswordEncoder encoder)
//    {
//        return args -> {
//            System.out.println(encoder.encode("123"));
//        };
//    }

}
