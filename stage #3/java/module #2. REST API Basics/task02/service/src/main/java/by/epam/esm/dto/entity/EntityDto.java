package by.epam.esm.dto.entity;

import java.io.Serializable;

public abstract class EntityDto implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
