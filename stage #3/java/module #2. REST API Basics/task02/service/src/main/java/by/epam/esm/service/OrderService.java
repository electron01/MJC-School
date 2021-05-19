package by.epam.esm.service;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.OrderInfoDto;
import by.epam.esm.dto.entity.PaginationDto;

import java.util.List;
import java.util.Map;

/**
 * class OrderService
 * interface contains methods for Order
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface OrderService {
    /**
     * method add
     * method for add new order
     * @param orderInfoList - order info for create
     * @param userId - user who buys
     * @return new order
     */
    OrderDto add(List<OrderInfoDto> orderInfoList,Long userId);

    /**
     * method findById
     * method for find Order by id
     * @param id - search identifier
     * @return found Order
     */
    OrderDto findById(Long id);

    /**
     * method findById
     * method for find Orders by userId
     * @param userId - search identifier
     * @param paginationDto - offset and limit for find
     * @return found Order
     */
    List<OrderDto> findByUserId(Long userId, PaginationDto paginationDto);

    /**
     * method delete
     * method for delete order by identifier
     * @param id
     */
    boolean delete(Long id);

    /**
     * getCountCountOfElements
     * method for find count of element
     * @param userId - params for find
     * @return count
     */
    Integer getCountCountOrderByUserId(Long userId);
}
