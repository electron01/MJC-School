package by.epam.esm.dao;

import by.epam.esm.enity.Entity;

import java.io.Serializable;
import java.util.Optional;
/**
 * Interface BaseDao
 * interface contains basic methods for data access object
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface BaseDao<T extends Entity, ID extends Serializable> {
    /**
     * Method findById
     * method for find something entity by id
     * @param id - search identifier
     * @return Optional.of(Entity) or Optional.empty if entity was not found
     */
    Optional<T> findById(ID id);

    /**
     * Method save
     * method saves new entity in database
     * @param entity - entity for save
     * @return the same entity
     */
    T save(T entity);

    /**
     * Method update
     * method updates entity in database
     * @param entity - entity for update
     * @return updated entity
     */
    T update(T entity);

    /**
     * Method deleteById
     * method deletes entity by id
     * @param id - identifier to delete
     * @return true if entity was correct deleted
     */
    boolean deleteById(ID id);
}
