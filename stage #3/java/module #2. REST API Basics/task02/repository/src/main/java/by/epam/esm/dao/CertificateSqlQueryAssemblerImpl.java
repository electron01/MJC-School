package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * class CertificateSqlQueryAssemblerImpl
 * implements CertificateSqlQueryAssembler
 * class contains methods for assemble sql query got gift certificate table
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class CertificateSqlQueryAssemblerImpl implements CertificateSqlQueryAssembler {
    private static final String FULL_SELECT = "SELECT DISTINCT gift_certificate.gift_certificate_id, gift_certificate.name," +
            "gift_certificate.description, gift_certificate.price, gift_certificate.duration," +
            "gift_certificate.create_date, gift_certificate.last_update_date " +
            "FROM gift_certificate " +
            "LEFT JOIN tag_gift_certificate " +
            "ON gift_certificate.gift_certificate_id = tag_gift_certificate.gift_certificate_id " +
            "LEFT JOIN tag ON tag_gift_certificate.tag_id = tag.tag_id " +
            "WHERE gift_certificate.remove = FALSE AND tag.remove = FALSE ";

    private static final String WHERE = "WHERE ";
    private static final String LIKE = "LIKE ?";
    private static final String NAME = "gift_certificate.name ";
    private static final String DESCRIPTION = "gift_certificate.description ";
    private static final String TAG_NAME = "tag.name ";
    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String LIMIT = " LIMIT ?,?";
    private static final String ORDER_SEPARATOR = ",";
    private static final String REGEX = "%";
    private static final String PRICE = "gift_certificate.price";
    private static final String DURATION = "gift_certificate.duration";
    private static final String ID = "gift_certificate_id";
    private static final String EXCEPTED_VALUE = " ?";
    private static final String COMMA = ", ";
    private static final String EQUALLY = " =";


    private static final String START_PART_UPDATE_REQUEST = "UPDATE gift_certificate SET last_update_date = now(), ";


    private StringBuilder sqlQuery = new StringBuilder();
    private List<Object> params = new ArrayList<>();

    private void initializationSqlQuery() {
        params.clear();
        sqlQuery.delete(0, sqlQuery.length());
    }

    @Override
    public SqlQuery createPartUpdateRequest(GiftCertificate giftCertificate) {
        initializationSqlQuery();
        sqlQuery.append(START_PART_UPDATE_REQUEST);
        addParamIfNeed(giftCertificate.getName(), NAME);
        addParamIfNeed(giftCertificate.getDescription(), DESCRIPTION);
        addParamIfNeed(giftCertificate.getPrice(), PRICE);
        addParamIfNeed(giftCertificate.getDuration(), DURATION);
        deleteLastComma();
        addWhereIdEqual(giftCertificate.getId());
        System.out.println(sqlQuery);
        return new SqlQuery(sqlQuery.toString(), params.toArray());
    }

    private void addParamIfNeed(Object param, String paramName) {
        if (param != null) {
            sqlQuery.append(paramName + EQUALLY + EXCEPTED_VALUE + COMMA);
            params.add(param);
        }
    }

    private void deleteLastComma() {
        sqlQuery.deleteCharAt(sqlQuery.lastIndexOf(COMMA));
    }

    private void addWhereIdEqual(Integer id) {
        sqlQuery.append(WHERE + ID + EQUALLY + EXCEPTED_VALUE);
        params.add(id);
    }

    @Override
    public SqlQuery createQueryForFindAllCertificate(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination) {
        initializationSqlQuery();
        sqlQuery.append(FULL_SELECT);
        addRequestParam(giftCertificateRequestParam.getName(), NAME);
        addRequestParam(giftCertificateRequestParam.getDescription(), DESCRIPTION);
        addRequestParam(giftCertificateRequestParam.getTagName(), TAG_NAME);
        addSort(giftCertificateRequestParam.getSort());
        addPagination(pagination);
        return new SqlQuery(sqlQuery.toString(), params.toArray());
    }

    private void addRequestParam(String requestParam, String column) {
        if (checkingForEmptyString(requestParam)) {
            String searchParam = REGEX + requestParam + REGEX;
            params.add(searchParam);
            sqlQuery.append(AND + column + LIKE);
        }
    }

    private void addPagination(Pagination pagination) {
        sqlQuery.append(LIMIT);
        params.add(pagination.getStartPosition());
        params.add(pagination.getLimit());
    }


    private void addSort(String sortRequest) {
        if (checkingForEmptyString(sortRequest)) {
            String[] sortArray = sortRequest.split(ORDER_SEPARATOR);
            sqlQuery.append(ORDER_BY);
            for (String sort : sortArray) {
                String sortType = sort.substring(0, sort.indexOf('('));
                String columnForSort = sort.substring(sort.indexOf('(') + 1, sort.length() - 1);
                sqlQuery.append(columnForSort + " " + sortType);
                sqlQuery.append(ORDER_SEPARATOR);
            }
            deleteLastOrderSeparator();
        }
    }

    private void deleteLastOrderSeparator() {
        sqlQuery.deleteCharAt(sqlQuery.lastIndexOf(ORDER_SEPARATOR));
    }

    private boolean checkingForEmptyString(String str) {
        if (str != null && (!(str.isBlank()))) {
            return true;
        }
        return false;
    }

    public static class SqlQuery {
        private String sqlQuery;
        private Object[] params;

        public SqlQuery(String sqlQuery, Object[] params) {
            this.sqlQuery = sqlQuery;
            this.params = params;
        }

        public String getSqlQuery() {
            return sqlQuery;
        }

        public Object[] getParams() {
            return params;
        }
    }
}
