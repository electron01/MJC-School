package by.epam.esm.config;

import by.epam.esm.constant.RepoConstant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("development")
@Configuration
@ComponentScan("by.epam.esm")
public class RepoConfig {

    @Value(RepoConstant.DB_DEVELOPMENT_PROPERTIES_FILE)
    private String dbPropertiesFile;

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig(dbPropertiesFile);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
