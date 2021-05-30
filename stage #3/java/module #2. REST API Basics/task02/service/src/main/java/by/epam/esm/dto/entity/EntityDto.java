package by.epam.esm.dto.entity;

import by.epam.esm.enity.BaseEntity;
import org.springframework.hateoas.RepresentationModel;

/**
 * class EntityDto
 * base abstract entity data transfer object class
 * @see BaseEntity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class EntityDto extends RepresentationModel<EntityDto> {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
