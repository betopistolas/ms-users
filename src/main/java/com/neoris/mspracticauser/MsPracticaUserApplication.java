package com.neoris.mspracticauser;

import com.neoris.mspracticauser.controller.UserController;
import com.neoris.mspracticauser.model.Users;
import com.neoris.mspracticauser.repositorio.UserRepositorio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@SpringBootApplication
public class MsPracticaUserApplication {

    private static final String ADMIN_NAME="admin";
    private static final Logger log = LogManager.getLogger(MsPracticaUserApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MsPracticaUserApplication.class, args);
    }

    @Autowired
    private UserRepositorio userRepositorio;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {


            Users userOptional = userRepositorio.findByEmail(ADMIN_NAME);
            if (userOptional == null)  {
                Users user = new Users();
                user.setName(ADMIN_NAME);
                user.setEmail(ADMIN_NAME);
                user.setStatus("activo");
                user.setRoles(null);
                user.setPassword(ADMIN_NAME);
                userRepositorio.save(user);
            } else {
                userOptional.setEmail(ADMIN_NAME);
                userOptional.setPassword(ADMIN_NAME);
                userRepositorio.save(userOptional);
            }
            log.debug("user "+ ADMIN_NAME);

        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

}
