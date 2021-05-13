package by.epam.esm.service;

import by.epam.esm.dto.entity.EntityDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * interface BaseService
 * interface contains base methods for work with entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface BaseService<E extends EntityDto,ID extends Serializable> {
    /**
     * method findAll
     * method for find all tags
     * @param paginationDto - offset and limit for find
     * @param params - params for search
     * @return Tags dto list
     */
    List<E> findAll(Map<String,String[]> params, PaginationDto paginationDto);

    /**
     * method findById
     * method for find entity by id
     * @param id - search identifier
     * @return found entity
     */
    E findById(ID id);

    /**
     * method add
     * method for add new entity
     * @param dto - entity dto for add
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
    boolean delete(ID id);

    Integer getCountCountOfElements(Map<String,String[]> params);
}
