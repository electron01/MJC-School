package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * class CertificateSqlQueryAssemblerImpl
 * implements CertificateSqlQueryBuilder
 * class contains impl methods for assemble sql query got gift certificate table
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class CertificateSqlQueryBuilderImpl implements CertificateSqlQueryBuilder {
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
    private static final String CREATE_DATE = "gift_certificate.create_date ";
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


    private StringBuilder query = new StringBuilder();
    private List<Object> params = new ArrayList<>();
    private SqlQuery sqlQuery;

    @Autowired
    public CertificateSqlQueryBuilderImpl(SqlQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }


    private void initializationSqlQuery() {
        params.clear();
        query.delete(0, query.length());
    }

    @Override
    public SqlQuery createUpdateRequest(GiftCertificate giftCertificate) {
        initializationSqlQuery();
        query.append(START_PART_UPDATE_REQUEST);
        addParamIfNeed(giftCertificate.getName(), NAME);
        addParamIfNeed(giftCertificate.getDescription(), DESCRIPTION);
        addParamIfNeed(giftCertificate.getPrice(), PRICE);
        addParamIfNeed(giftCertificate.getDuration(), DURATION);
        addParamIfNeed(giftCertificate.getCreateDate(), CREATE_DATE);
        deleteLastComma();
        addWhereIdEqual(giftCertificate.getId());
        sqlQuery.setSqlQuery(query.toString());
        sqlQuery.setParams(params.toArray());
        return sqlQuery;
    }

    private void addParamIfNeed(Object param, String paramName) {
        if (param != null) {
            query.append(paramName + EQUALLY + EXCEPTED_VALUE + COMMA);
            params.add(param);
        }
    }

    private void deleteLastComma() {
        query.deleteCharAt(query.lastIndexOf(COMMA));
    }

    private void addWhereIdEqual(Integer id) {
        query.append(WHERE + ID + EQUALLY + EXCEPTED_VALUE);
        params.add(id);
    }

    @Override
    public SqlQuery createQueryForFindAllCertificate(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination) {
        initializationSqlQuery();
        query.append(FULL_SELECT);
        addRequestParam(giftCertificateRequestParam.getName(), NAME);
        addRequestParam(giftCertificateRequestParam.getDescription(), DESCRIPTION);
        addRequestParam(giftCertificateRequestParam.getTagName(), TAG_NAME);
        addSort(giftCertificateRequestParam.getSort());
        addPagination(pagination);
        sqlQuery.setSqlQuery(query.toString());
        sqlQuery.setParams(params.toArray());
        return sqlQuery;
    }

    private void addRequestParam(String requestParam, String column) {
        if (checkForEmptyString(requestParam)) {
            String searchParam = REGEX + requestParam + REGEX;
            params.add(searchParam);
            query.append(AND + column + LIKE);
        }
    }

    private void addPagination(Pagination pagination) {
        query.append(LIMIT);
        params.add(pagination.getStartPosition());
        params.add(pagination.getLimit());
    }


    private void addSort(String sortRequest) {
        if (checkForEmptyString(sortRequest)) {
            String[] sortArray = sortRequest.split(ORDER_SEPARATOR);
            query.append(ORDER_BY);
            for (String sort : sortArray) {
                String sortType = sort.substring(0, sort.indexOf('('));
                String columnForSort = sort.substring(sort.indexOf('(') + 1, sort.length() - 1);
                query.append(columnForSort + " " + sortType);
                query.append(ORDER_SEPARATOR);
            }
            deleteLastOrderSeparator();
        }
    }

    private void deleteLastOrderSeparator() {
        query.deleteCharAt(query.lastIndexOf(ORDER_SEPARATOR));
    }

    private boolean checkForEmptyString(String str) {
        if (str != null && (!(str.isBlank()))) {
            return true;
        }
        return false;
    }

}
