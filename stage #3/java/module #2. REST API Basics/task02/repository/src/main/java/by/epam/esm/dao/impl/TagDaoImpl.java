package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.dao.mapper.TagRowMapper;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
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
public class TagDaoImpl implements CrdTagDao {
    private JdbcTemplate jdbcTemplate;
    private TagRowMapper tagRowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        return jdbcTemplate.query(RepoConstant.GET_TAG_NY_NAME, new Object[]{tagName}, tagRowMapper)
                .stream()
                .findAny();
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        return jdbcTemplate.query(RepoConstant.GET_LIST_TAG_BY_CERTIFICATE_ID,
                new Object[]{id},
                tagRowMapper);
    }

    @Override
    public List<Tag> findAll(Pagination pagination) {
        return jdbcTemplate.query(RepoConstant.FIND_ALL_TAG_REQUEST,
                new Object[]{pagination.getStartPosition(), pagination.getLimit()},
                tagRowMapper);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(RepoConstant.GET_TAG_BY_ID_REQUEST, new Object[]{id}, tagRowMapper)
                .stream()
                .findAny();
    }

    @Override
    public Tag add(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> getAddNewTagStatement(con, entity), keyHolder);
        entity.setId(getGeneratedId(keyHolder));
        return findById(entity.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update");
    }


    private PreparedStatement getAddNewTagStatement(Connection con, Tag entity) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(RepoConstant.SAVE_TAG_REQUEST, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getName());
        return preparedStatement;

    }

    private Long getGeneratedId(KeyHolder keyHolder) {
        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(RepoConstant.DELETE_TAG_BY_ID_REQUEST,
                new Object[]{id}) == 1;
    }
}

