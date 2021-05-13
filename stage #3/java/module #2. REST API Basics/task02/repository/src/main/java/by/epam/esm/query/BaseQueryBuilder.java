package by.epam.esm.query;

import by.epam.esm.constant.RepoConstant;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseQueryBuilder<T> {
    private List<Predicate> predicateList = new ArrayList<>();
    private CriteriaBuilder criteriaBuilder;

    public void addParamRemoveFalseToQuery(Root root) {
        predicateList.add(criteriaBuilder.equal(root.get(RepoConstant.REMOVED), 0));
    }

    public void addParamToQuery(String param, Expression<String> attributeName, String column) {
        if (param != null && !param.isBlank()) {
            checkCorrectColumnName(column);
            predicateList.add(criteriaBuilder.like(attributeName,
                    RepoConstant.REGEX + param + RepoConstant.REGEX));
        }
    }

    public void addEqualParamToQuery(String param, Expression<String> attributeName, String column) {
        if (param != null && !param.isBlank()) {
            checkCorrectColumnName(column);
            predicateList.add(criteriaBuilder.equal(attributeName,
                    param));
        }
    }

    public void addJoinParamToQuery(String param, Root<T> root, String joinEntity, String column) {
        if (param != null && !param.isBlank()) {
            predicateList.add(criteriaBuilder.like(root.join(joinEntity).get(column),
                    RepoConstant.REGEX + param + RepoConstant.REGEX));
        }

    }

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

    public abstract CriteriaQuery<T> createCriteriaQuery(Map<String, String[]> params, CriteriaBuilder criteriaBuilder);

    public abstract List<String> getTableColumns();


    public List<Predicate> getPredicateList() {
        return predicateList;
    }


    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }
}
