package by.epam.esm;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("test")
@EnableTransactionManagement
@EnableAutoConfiguration
@Configuration
@ComponentScan("by.epam.esm")
@EntityScan(basePackages = {"by.epam.esm.enity"})
public class RepositoryConfig {
}
