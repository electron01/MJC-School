package by.epam.esm.service;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.OrderInfoDto;

import java.util.List;

public interface OrderService {
    OrderDto add(List<OrderInfoDto> orderInfoList,Long userId);

    OrderDto findById(Long id);

    List<OrderDto> findByUserId(Long userId);

    boolean delete(Long id);
}
