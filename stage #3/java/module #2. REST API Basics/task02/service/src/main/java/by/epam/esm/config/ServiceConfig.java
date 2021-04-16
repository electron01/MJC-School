package by.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
@ComponentScan("by.epam.esm")
@Import(RepoConfig.class)
public class ServiceConfig {
    @Bean
    public Validator createNewValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();

    }
}
