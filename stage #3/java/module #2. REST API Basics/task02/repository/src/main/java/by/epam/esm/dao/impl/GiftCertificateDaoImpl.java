package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.CertificateSqlQueryBuilder;
import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dao.SqlQuery;
import by.epam.esm.dao.mapper.GiftCertificateRowMapper;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements CrudGiftCertificateDao {
    private GiftCertificateRowMapper giftCertificateRowMapper;
    private JdbcTemplate jdbcTemplate;
    private CertificateSqlQueryBuilder certificateSqlQueryBuilder;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateRowMapper giftCertificateRowMapper, JdbcTemplate jdbcTemplate, CertificateSqlQueryBuilder certificateSqlQueryAssembler) {
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.certificateSqlQueryBuilder = certificateSqlQueryAssembler;
    }

    @Override
    public List<GiftCertificate> findAll(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination) {
        SqlQuery sqlQuery = certificateSqlQueryBuilder.createQueryForFindAllCertificate(giftCertificateRequestParam, pagination);
        return jdbcTemplate.query(sqlQuery.getSqlQuery(),
                sqlQuery.getParams(),
                giftCertificateRowMapper);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(RepoConstant.GET_CERTIFICATE_BY_NAME,
                new Object[]{name},
                giftCertificateRowMapper).stream()
                .findAny();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        SqlQuery sqlQuery = certificateSqlQueryBuilder.createUpdateRequest(entity);
        jdbcTemplate.update(sqlQuery.getSqlQuery(), sqlQuery.getParams());
        if (entity.getTags() != null) {
            updateTags(entity);
        }
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(RepoConstant.GET_CERTIFICATE_BY_ID,
                new Object[]{id},
                giftCertificateRowMapper).stream()
                .findAny();
    }

    @Override
    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> getAddNewCertificateStatement(con, entity), keyHolder);
        entity.setId(getGeneratedId(keyHolder));
        addTagToGiftCertificate(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(RepoConstant.DELETE_CERTIFICATE_REQUEST, new Object[]{id}) == 1;
    }

    private void addTagToGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate.getTags() != null) {
            giftCertificate.getTags().stream()
                    .forEach(tag -> jdbcTemplate.update(RepoConstant.UPDATE_TAG_GIFT_CERTIFICATE, giftCertificate.getId(), tag.getId()));
        }

    }

    private void deleteFromGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(RepoConstant.DELETE_TAG_GIFT_CERTIFICATE, new Object[]{giftCertificate.getId()});
    }

    private PreparedStatement getAddNewCertificateStatement(Connection con, GiftCertificate entity) throws SQLException {
        int index = 1;
        PreparedStatement preparedStatement = con.prepareStatement(RepoConstant.SAVE_CERTIFICATE_REQUEST, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(index++, entity.getName());
        preparedStatement.setString(index++, entity.getDescription());
        preparedStatement.setBigDecimal(index++, entity.getPrice());
        preparedStatement.setInt(index, entity.getDuration());
        return preparedStatement;
    }

    private Long getGeneratedId(KeyHolder keyHolder) {
        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    private void updateTags(GiftCertificate giftCertificate) {
        deleteFromGiftCertificate(giftCertificate);
        addTagToGiftCertificate(giftCertificate);
    }
}
