package by.epam.esm.dao;

import by.epam.esm.constant.RepoConstant;
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
        query.append(RepoConstant.START_PART_UPDATE_CERTIFICATE_REQUEST);
        addParamIfNeed(giftCertificate.getName(), RepoConstant.CERTIFICATE_NAME);
        addParamIfNeed(giftCertificate.getDescription(), RepoConstant.CERTIFICATE_DESCRIPTION);
        addParamIfNeed(giftCertificate.getPrice(), RepoConstant.CERTIFICATE_PRICE);
        addParamIfNeed(giftCertificate.getDuration(),RepoConstant.CERTIFICATE_DURATION);
        addParamIfNeed(giftCertificate.getCreateDate(), RepoConstant.CERTIFICATE_CREATE_DATE);
        deleteLastComma();
        addWhereIdEqual(giftCertificate.getId());
        sqlQuery.setSqlQuery(query.toString());
        sqlQuery.setParams(params.toArray());
        return sqlQuery;
    }

    private void addParamIfNeed(Object param, String paramName) {
        if (param != null) {
            query.append(paramName + RepoConstant.EQUALLY + RepoConstant.EXCEPTED_VALUE + RepoConstant.COMMA);
            params.add(param);
        }
    }

    private void deleteLastComma() {
        query.deleteCharAt(query.lastIndexOf(RepoConstant.COMMA));
    }

    private void addWhereIdEqual(Long id) {
        query.append(RepoConstant.WHERE + RepoConstant.CERTIFICATE_ID + RepoConstant.EQUALLY + RepoConstant.EXCEPTED_VALUE);
        params.add(id);
    }

    @Override
    public SqlQuery createQueryForFindAllCertificate(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination) {
        initializationSqlQuery();
        query.append(RepoConstant.FULL_SELECT);
        addRequestParam(giftCertificateRequestParam.getName(), RepoConstant.CERTIFICATE_NAME);
        addRequestParam(giftCertificateRequestParam.getDescription(), RepoConstant.CERTIFICATE_DESCRIPTION);
        addRequestParam(giftCertificateRequestParam.getTagName(), RepoConstant.TAG_NAME);
        addSort(giftCertificateRequestParam.getSort());
        addPagination(pagination);
        sqlQuery.setSqlQuery(query.toString());
        sqlQuery.setParams(params.toArray());
        return sqlQuery;
    }

    private void addRequestParam(String requestParam, String column) {
        if (checkForEmptyString(requestParam)) {
            String searchParam = RepoConstant.REGEX + requestParam + RepoConstant.REGEX;
            params.add(searchParam);
            query.append(RepoConstant.AND + column + RepoConstant.LIKE);
        }
    }

    private void addPagination(Pagination pagination) {
        query.append(RepoConstant.LIMIT);
        params.add(pagination.getStartPosition());
        params.add(pagination.getLimit());
    }


    private void addSort(String sortRequest) {
        if (checkForEmptyString(sortRequest)) {
            String[] sortArray = sortRequest.split(RepoConstant.ORDER_SEPARATOR);
            query.append(RepoConstant.ORDER_BY);
            for (String sort : sortArray) {
                String sortType = sort.substring(0, sort.indexOf('('));
                String columnForSort = sort.substring(sort.indexOf('(') + 1, sort.length() - 1);
                query.append(columnForSort + " " + sortType);
                query.append(RepoConstant.ORDER_SEPARATOR);
            }
            deleteLastOrderSeparator();
        }
    }

    private void deleteLastOrderSeparator() {
        query.deleteCharAt(query.lastIndexOf(RepoConstant.ORDER_SEPARATOR));
    }

    private boolean checkForEmptyString(String str) {
        if (str != null && (!(str.isBlank()))) {
            return true;
        }
        return false;
    }

}
