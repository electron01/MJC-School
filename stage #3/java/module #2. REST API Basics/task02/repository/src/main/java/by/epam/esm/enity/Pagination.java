package by.epam.esm.enity;

/**
 * class Pagination
 * class contains two params startPosition and limit for list separation on page
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class Pagination {
    private Integer startPosition;
    private Integer limit;

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
