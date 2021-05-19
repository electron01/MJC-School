package by.epam.esm.dao;

import by.epam.esm.enity.Order;
import by.epam.esm.enity.Pagination;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * interface OrderDao
 * interface contains base methods for work with order entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface OrderDao {
    /**
     * method findById
     * method for find Order by id
     * @param id - search identifier
     * @return found Order
     */
    Optional<Order> findById(Long id);

    /**
     * method add
     * method for add new order
     * @param entity - order for create
     * @return new order
     */
    Order add(Order entity);

    /**
     * method delete
     * method for delete order by identifier
     * @param id
     */
    boolean deleteById(Long id);

    /**
     * method findById
     * method for find Orders by userId
     * @param userId - search identifier
     * @param pagination - offset and limit for find
     * @return found Order
     */
    List<Order> findByUserId(Long userId, Pagination pagination);

    /**
     * getCountCountOfElements
     * method for find count of element
     * @param userId - params for find
     * @return count
     */
    Integer getCountCountOfElements(Long userId);
}
