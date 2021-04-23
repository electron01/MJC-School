package by.epam.esm;


import by.epam.esm.dao.CertificateSqlQueryBuilder;
import by.epam.esm.dao.CertificateSqlQueryBuilderImpl;
import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dao.impl.GiftCertificateDaoImpl;
import by.epam.esm.dao.impl.TagDaoImpl;
import by.epam.esm.dao.mapper.GiftCertificateRowMapper;
import by.epam.esm.dao.mapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {
    private static final String DB_SCRIPT = "classpath:DBScript.sql";
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TagRowMapper tagRowMapper;
    @Autowired
    private GiftCertificateRowMapper giftCertificateRowMapper;
    @Autowired
    private CertificateSqlQueryBuilderImpl sqlQuery;
    @Autowired
    private CrdTagDao tagDao;
    @Autowired
    private CrudGiftCertificateDao giftCertificateDao;
    @Autowired
    private CertificateSqlQueryBuilder sqlQueryAssembler;

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

    @Bean
    public TagRowMapper getTagRowMapper() {
        return new TagRowMapper();
    }

    @Bean
    public GiftCertificateRowMapper giftCertificateRowMapper() {
        return new GiftCertificateRowMapper();
    }

    @Bean
    public CertificateSqlQueryBuilderImpl getSqlQuery() {
        return new CertificateSqlQueryBuilderImpl();
    }

    @Bean
    public CrdTagDao getTagDao() {
        return new TagDaoImpl(jdbcTemplate, tagRowMapper);
    }

    @Bean
    public CrudGiftCertificateDao giftCertificateDao() {
        return new GiftCertificateDaoImpl(giftCertificateRowMapper(), jdbcTemplate,sqlQueryAssembler);
    }
}
