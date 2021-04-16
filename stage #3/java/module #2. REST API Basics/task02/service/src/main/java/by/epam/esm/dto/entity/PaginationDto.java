package by.epam.esm.dto.entity;

import javax.validation.constraints.PositiveOrZero;

public class PaginationDto {
    @PositiveOrZero
    private Integer startPosition;
    @PositiveOrZero
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
