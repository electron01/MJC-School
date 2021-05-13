package by.epam.esm.dao;

import by.epam.esm.enity.BaseEntity;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface BaseDao
 * interface contains basic methods for data access object
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface BaseDao<T extends BaseEntity, ID extends Serializable> {
    /**
     * Method findAll
     * method for find tags list in database
     * @param pagination - offset and limit for find
     * @return Tags list
     */
    List<T> findAll(Map<String,String[]> params, Pagination pagination);

    /**
     * Method findById
     * method for find something entity by id
     * @param id - search identifier
     * @return Optional.of(BaseEntity) or Optional.empty if entity was not found
     */
    Optional<T> findById(ID id);

    /**
     * Method add
     * method saves new entity in database
     * @param entity - entity for add
     * @return the same entity
     */
    T add(T entity);

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

    Integer getCountOfElements(Map<String,String[]> params);
}
