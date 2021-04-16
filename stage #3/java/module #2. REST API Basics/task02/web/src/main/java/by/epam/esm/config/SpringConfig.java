package by.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("by.epam.esm")
@EnableWebMvc
@Import(ServiceConfig.class)
public class SpringConfig {
}
