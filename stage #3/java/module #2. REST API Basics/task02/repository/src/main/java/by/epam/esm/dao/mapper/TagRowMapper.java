package by.epam.esm.dao.mapper;

import by.epam.esm.enity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    private final static String TAG_ID = "tag_id";
    private final static String TAG_NAME = "name";
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt(TAG_ID));
        tag.setName(rs.getString(TAG_NAME));
        return tag;
    }
}
