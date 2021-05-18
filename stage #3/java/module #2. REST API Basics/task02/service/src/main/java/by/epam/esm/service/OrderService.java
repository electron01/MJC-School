package by.epam.esm.service;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.OrderInfoDto;

import java.util.List;

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
     * @return found Order
     */
    List<OrderDto> findByUserId(Long userId);

    /**
     * method delete
     * method for delete order by identifier
     * @param id
     */
    boolean delete(Long id);
}
