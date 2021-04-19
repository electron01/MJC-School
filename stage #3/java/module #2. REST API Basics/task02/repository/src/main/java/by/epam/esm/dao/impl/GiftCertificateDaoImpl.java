package by.epam.esm.dao.impl;

import by.epam.esm.dao.BaseSqlQueryAssembler;
import by.epam.esm.dao.CertificateSqlQueryAssemblerImpl;
import by.epam.esm.dao.CrudGiftCertificateDao;
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
    private static final String SELECT_ALL_COLUMNS =
            "SELECT gift_certificate_id,name," +
                    "description, price, duration, create_date, last_update_date ";
    private static final String FROM_GIFT_CERTIFICATE = "FROM gift_certificate ";
    private static final String UPDATE_TAG_GIFT_CERTIFICATE =
            "INSERT INTO tag_gift_certificate " +
                    "(gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_TAG_GIFT_CERTIFICATE = "DELETE FROM tag_gift_certificate WHERE gift_certificate_id = ?";
    private static final String SAVE_CERTIFICATE_REQUEST =
            "INSERT INTO  gift_certificate" +
                    " (name,description,price,duration) VALUES (?,?,?,?)";
    private static final String UPDATE_CERTIFICATE_REQUEST =
            "UPDATE gift_certificate SET name = ?, description = ?, price = ?, duration = ?, last_update_date = now() " +
                    "WHERE gift_certificate_id = ?";

    private static final String GET_CERTIFICATE_BY_ID = SELECT_ALL_COLUMNS +
            FROM_GIFT_CERTIFICATE +
            "WHERE gift_certificate_id = ? AND remove = FALSE ";

    private static final String GET_CERTIFICATE_BY_NAME = SELECT_ALL_COLUMNS +
            FROM_GIFT_CERTIFICATE +
            "WHERE name = ? AND remove = FALSE ";

    private static final String DELETE_CERTIFICATE_REQUEST =
            "UPDATE gift_certificate set remove = TRUE " +
                    "WHERE gift_certificate_id = ?";


    private GiftCertificateRowMapper giftCertificateRowMapper;
    private JdbcTemplate jdbcTemplate;
    private BaseSqlQueryAssembler certificateSqlQueryAssembler;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateRowMapper giftCertificateRowMapper, JdbcTemplate jdbcTemplate,BaseSqlQueryAssembler certificateSqlQueryAssembler ) {
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.certificateSqlQueryAssembler=certificateSqlQueryAssembler;
    }

    @Override
    public List<GiftCertificate> findAll(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination) {
        CertificateSqlQueryAssemblerImpl.SqlQuery sqlQuery = certificateSqlQueryAssembler.createQueryForFindAllCertificate(giftCertificateRequestParam, pagination);
        return jdbcTemplate.query(sqlQuery.getSqlQuery(),
                sqlQuery.getParams(),
                giftCertificateRowMapper);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(GET_CERTIFICATE_BY_NAME,
                new Object[]{name},
                giftCertificateRowMapper).stream()
                .findAny();
    }

    @Override
    public GiftCertificate partUpdate(GiftCertificate entity) {
        CertificateSqlQueryAssemblerImpl.SqlQuery sqlUpdateRequest = certificateSqlQueryAssembler.createPartUpdateRequest(entity);
        jdbcTemplate.update(sqlUpdateRequest.getSqlQuery(), sqlUpdateRequest.getParams());
        if (entity.getTags() != null) {
            updateTags(entity);
        }
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        jdbcTemplate.update(con -> getUpdateStatement(con, entity));
        updateTags(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }


    @Override
    public Optional<GiftCertificate> findById(Integer id) {
        return jdbcTemplate.query(GET_CERTIFICATE_BY_ID,
                new Object[]{id},
                giftCertificateRowMapper).stream()
                .findAny();
    }

    @Override
    public GiftCertificate save(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> getSaveStatement(con, entity), keyHolder);
        entity.setId(getGeneratedId(keyHolder));
        addToTagGiftCertificate(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public boolean deleteById(Integer id) {
        return jdbcTemplate.update(DELETE_CERTIFICATE_REQUEST, new Object[]{id}) == 1;
    }

    private void addToTagGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate.getTags() != null) {
            giftCertificate.getTags().stream()
                    .forEach(tag -> jdbcTemplate.update(UPDATE_TAG_GIFT_CERTIFICATE, giftCertificate.getId(), tag.getId()));
        }

    }

    private void deleteFromGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(DELETE_TAG_GIFT_CERTIFICATE, new Object[]{giftCertificate.getId()});
    }

    private PreparedStatement getSaveStatement(Connection con, GiftCertificate entity) throws SQLException {
        int index = 1;
        PreparedStatement preparedStatement = con.prepareStatement(SAVE_CERTIFICATE_REQUEST, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(index++, entity.getName());
        preparedStatement.setString(index++, entity.getDescription());
        preparedStatement.setBigDecimal(index++, entity.getPrice());
        preparedStatement.setInt(index, entity.getDuration());
        return preparedStatement;
    }

    private PreparedStatement getUpdateStatement(Connection con, GiftCertificate entity) throws SQLException {
        int index = 1;
        PreparedStatement preparedStatement = con.prepareStatement(UPDATE_CERTIFICATE_REQUEST);
        preparedStatement.setString(index++, entity.getName());
        preparedStatement.setString(index++, entity.getDescription());
        preparedStatement.setBigDecimal(index++, entity.getPrice());
        preparedStatement.setInt(index++, entity.getDuration());
        preparedStatement.setInt(index, entity.getId());
        return preparedStatement;
    }

    private Integer getGeneratedId(KeyHolder keyHolder) {
        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::intValue)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    private void updateTags(GiftCertificate giftCertificate) {
        deleteFromGiftCertificate(giftCertificate);
        addToTagGiftCertificate(giftCertificate);
    }
}
