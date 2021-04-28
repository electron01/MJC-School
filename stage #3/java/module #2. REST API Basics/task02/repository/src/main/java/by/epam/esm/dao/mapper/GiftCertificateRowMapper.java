package by.epam.esm.dao.mapper;

import by.epam.esm.enity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "description";
    private static final String GIFT_CERTIFICATE_PRICE = "price";
    private static final String GIFT_CERTIFICATE_DURATION = "duration";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = "create_date";
    private static final String GIFT_CERTIFICATE_LAST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(GIFT_CERTIFICATE_ID));
        giftCertificate.setName(rs.getString(GIFT_CERTIFICATE_NAME));
        giftCertificate.setPrice(rs.getBigDecimal(GIFT_CERTIFICATE_PRICE));
        giftCertificate.setDescription(rs.getString(GIFT_CERTIFICATE_DESCRIPTION));
        giftCertificate.setDuration(rs.getInt(GIFT_CERTIFICATE_DURATION));
        giftCertificate.setCreateDate(rs.getObject(GIFT_CERTIFICATE_CREATE_DATE, LocalDateTime.class));
        giftCertificate.setUpdateDate(rs.getObject(GIFT_CERTIFICATE_LAST_UPDATE_DATE, LocalDateTime.class));
        return giftCertificate;
    }
}
