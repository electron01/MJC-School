package by.epam.esm.query;

import by.epam.esm.constant.RepoConstant;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class BaseQueryBuilder
 * base abstract query builder
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class BaseQueryBuilder<T> {
    private List<Predicate> predicateList = new ArrayList<>();
    private CriteriaBuilder criteriaBuilder;

    /**
     * addParamRemoveFalseToQuery
     * add param remove = false to query
     * @param root - A root type in the from clause.
     */
    public void addParamRemoveFalseToQuery(Root root) {
        predicateList.add(criteriaBuilder.equal(root.get(RepoConstant.REMOVED), 0));
    }

    /**
     * addParamToQuery
     * add param to query
     * @param param - parameter
     * @param attributeName - attribute name
     * @param column - name of column
     */
    public void addParamToQuery(String param, Expression<String> attributeName, String column) {
        if (param != null && !param.isBlank()) {
            checkCorrectColumnName(column);
            predicateList.add(criteriaBuilder.like(attributeName,
                    RepoConstant.REGEX + param + RepoConstant.REGEX));
        }
    }

    /**
     * addEqualParamToQuery
     * add equal param to query
     * @param param - parameter
     * @param attributeName - attribute name
     * @param column - name of column
     */
    public void addEqualParamToQuery(String param, Expression<String> attributeName, String column) {
        if (param != null && !param.isBlank()) {
            checkCorrectColumnName(column);
            predicateList.add(criteriaBuilder.equal(attributeName,
                    param));
        }
    }

    /**
     * addJoinParamToQuery
     * add join param to query
     * @param param - parameter
     * @param root - A root type in the from clause.
     * @param column - name of column
     */
    public void addJoinParamToQuery(String param, Root<T> root, String joinEntity, String column) {
        if (param != null && !param.isBlank()) {
            predicateList.add(criteriaBuilder.like(root.join(joinEntity).get(column),
                    RepoConstant.REGEX + param + RepoConstant.REGEX));
        }

    }
    /**
     * addSortToQuery
     * add sort param to query
     * @param criteriaQuery - query
     * @param root - A root type in the from clause
     * @param columnForSort - name column for sort
     */
    public void addSortToQuery(CriteriaQuery criteriaQuery, Root root, String[] columnForSort) {
        if (columnForSort == null || columnForSort.length == 0) {
            return;
        }
        List<Order> orderList = new ArrayList<>();
        for (String key : columnForSort) {
            String sortType = key.substring(0, key.indexOf("["));
            String column = key.substring(key.indexOf('[') + 1, key.indexOf("]"));
            checkCorrectColumnName(column);
            if (sortType.equals(RepoConstant.SORT_ASC)) {
                orderList.add(criteriaBuilder.asc(root.get(column)));
            } else {
                orderList.add(criteriaBuilder.desc(root.get(column)));
            }
        }
        criteriaQuery.orderBy(orderList);
    }

    private void checkCorrectColumnName(String columnName) {
        for (String column : getTableColumns()) {
            if (column.equals(columnName)) {
                return;
            }

        }
        throw new IllegalArgumentException();
    }

    /**
     * create criteria query
     * @param params - params for query
     * @param criteriaBuilder
     * @return new criteria query
     */
    public abstract CriteriaQuery<T> createCriteriaQuery(Map<String, String[]> params, CriteriaBuilder criteriaBuilder);

    public abstract List<String> getTableColumns();

    public List<Predicate> getPredicateList() {
        return predicateList;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }
}
