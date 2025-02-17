package by.epam.esm.enity;

import javax.persistence.*;

/**
 * class BaseEntity
 * base abstract entity class
 *
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
