package by.epam.esm.dao;

import by.epam.esm.enity.Entity;

import java.io.Serializable;
import java.util.Optional;

public interface BaseDao<T extends Entity, ID extends Serializable> {
    Optional<T> findById(ID id);

    T save(T entity);
    T update(T entity);

    boolean deleteById(ID id);

}
