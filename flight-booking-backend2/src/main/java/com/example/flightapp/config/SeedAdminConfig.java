//
//package com.example.flightapp.config;
//
//import com.example.flightapp.entity.User;
//import com.example.flightapp.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SeedAdminConfig {
//
//    @Bean
//    CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder) {
//        return args -> {
//            users.findByUsername("admin").orElseGet(() -> {
//                User admin = new User();
//                admin.setUsername("luffy");
//                admin.setEmail("luffy@gmail.com.com");
//                admin.setName("Administrator");
//                admin.setPhone("0000000000");
//                admin.setPassword(encoder.encode("kop001"));
//                admin.setRole(User.Role.ADMIN);
//                return users.save(admin);
//            });
//        };
//    }
//}
