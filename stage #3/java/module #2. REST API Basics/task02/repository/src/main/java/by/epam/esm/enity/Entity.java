package by.epam.esm.enity;

import java.io.Serializable;

/**
 * class Entity
 * base abstract entity class
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class Entity implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
