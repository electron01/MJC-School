package by.epam.esm.dao;

import by.epam.esm.enity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> findById(Long id);
    Order add(Order entity);
    boolean deleteById(Long id);
    List<Order> findByUserId(Long userId);
}
