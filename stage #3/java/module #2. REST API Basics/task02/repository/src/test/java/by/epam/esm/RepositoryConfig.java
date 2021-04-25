package by.epam.esm;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("by.epam.esm")
@Profile("test")
public class RepositoryConfig {
    private static final String DB_SCRIPT = "classpath:DBScript.sql";
    @Bean
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL)
                .addScript(DB_SCRIPT);
        return embeddedDatabaseBuilder.build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }


}
