package by.epam.esm.service;

import by.epam.esm.dto.entity.EntityDto;

import java.io.Serializable;

/**
 * interface BaseService
 * interface contains base methods for work with entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface BaseService<E extends EntityDto,ID extends Serializable> {
    /**
     * method findById
     * method for find entity by id
     * @param id - search identifier
     * @return found entity
     */
    E findById(ID id);

    /**
     * method add
     * method for save new entity
     * @param dto - entity dto for save
     * @return new entity
     */
    E add(E dto);

    /**
     * method update
     * method for update entity
     * @param e - entity dto for update
     * @return updated entity
     */
    E update(E e);

    /**
     * method delete
     * method for delete entity by identifier
     * @param id
     */
    void delete(ID id);
}
