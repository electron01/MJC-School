package by.epam.esm.dao;

import org.springframework.stereotype.Component;
/**
 * class SqlQuery
 * class contains sql request and params for request
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class SqlQuery {
    private String sqlQuery;
    private Object[] params;

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public Object[] getParams() {
        return params;
    }
}