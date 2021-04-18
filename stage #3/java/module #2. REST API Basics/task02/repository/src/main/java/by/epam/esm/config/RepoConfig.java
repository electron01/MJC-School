package by.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("by.epam.esm")
@PropertySource("classpath:repository.properties")
public class RepoConfig {

    @Value("#{environment['spring.profiles.active'] == 'production' ? ${db.production} : ${db.development}}")
    private String dbPropertiesFile;

    @Bean(destroyMethod = "close")//optional
    public HikariDataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig(dbPropertiesFile);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
