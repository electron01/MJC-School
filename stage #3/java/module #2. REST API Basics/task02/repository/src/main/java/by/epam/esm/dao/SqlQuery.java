package by.epam.esm.dao;

import org.springframework.stereotype.Component;

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