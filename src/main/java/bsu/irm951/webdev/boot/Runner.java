package bsu.irm951.webdev.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan("bsu.irm951.webdev*")
@EnableJpaRepositories("bsu.irm951.webdev.repositories")
@ComponentScan(basePackages = {"bsu.irm951.webdev.controllers"})
@ComponentScan(basePackages = {"bsu.irm951.webdev.models"})
@ComponentScan(basePackages = {"bsu.irm951.webdev.services"})
@ComponentScan(basePackages = {"bsu.irm951.webdev.validators"})
public class Runner {
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
