package by.epam.esm.enity;

/**
 * class Entity
 * base abstract entity class
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class Entity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
