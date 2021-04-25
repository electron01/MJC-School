package by.epam.esm.enity;

import java.util.Objects;

/**
 * class Tag
 * extends base entity class
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class Tag extends Entity {

    private String name;

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
