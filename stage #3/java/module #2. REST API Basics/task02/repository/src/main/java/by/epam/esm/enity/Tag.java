package by.epam.esm.enity;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * class Tag
 * extends base entity class
 *
 * @author Aliaksei Tkachuk
 * @version 1.0
 */

@Entity
@Table(name = "tag")
@Audited
public class Tag extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "remove")
    private Boolean remove=false;

    public Boolean getRemove() {
        return remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getId(), tag.getId()) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name);
    }
}
