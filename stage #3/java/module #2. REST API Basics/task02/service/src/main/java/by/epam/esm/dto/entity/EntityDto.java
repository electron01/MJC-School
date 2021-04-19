package by.epam.esm.dto.entity;

import java.io.Serializable;

/**
 * class EntityDto
 * base abstract entity data transfer object class
 * @see by.epam.esm.enity.Entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class EntityDto implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
