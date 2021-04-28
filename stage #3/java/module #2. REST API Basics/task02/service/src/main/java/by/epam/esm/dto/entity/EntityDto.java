package by.epam.esm.dto.entity;

/**
 * class EntityDto
 * base abstract entity data transfer object class
 * @see by.epam.esm.enity.Entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class EntityDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
