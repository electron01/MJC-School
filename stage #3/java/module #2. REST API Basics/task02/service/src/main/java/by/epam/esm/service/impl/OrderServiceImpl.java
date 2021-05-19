package by.epam.esm.service.impl;

import by.epam.esm.dao.OrderDao;
import by.epam.esm.dto.entity.*;
import by.epam.esm.dto.mapper.GiftCertificateMapper;
import by.epam.esm.dto.mapper.OrderMapper;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.dto.mapper.UserMapper;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Order;
import by.epam.esm.enity.Pagination;
import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.GiftCertificateService;
import by.epam.esm.service.OrderService;
import by.epam.esm.service.UserService;
import by.epam.esm.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private OrderDao orderDao;
    private UserService userService;
    private GiftCertificateService giftCertificateService;
    private GiftCertificateMapper giftCertificateMapper;
    private UserMapper userMapper;
    private PaginationMapper paginationMapper;
    private BaseValidator baseValidator;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderDao orderDao,
                            UserService userService, GiftCertificateService giftCertificateService,
                            GiftCertificateMapper giftCertificateMapper,
                            UserMapper userMapper,PaginationMapper paginationMapper,
                            BaseValidator baseValidator) {
        this.orderMapper = orderMapper;
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateMapper = giftCertificateMapper;
        this.userMapper = userMapper;
        this.paginationMapper = paginationMapper;
        this.baseValidator = baseValidator;
    }


    @Override
    @Transactional
    public OrderDto add(List<OrderInfoDto> orderInfoList, Long userId) {
        UserDto userDto = userService.findById(userId);
        ArrayList<GiftCertificate> giftCertificates = new ArrayList<>();
        orderInfoList.stream()
                .forEach(orderInfo -> {
                    GiftCertificateDto certificateDto = giftCertificateService.findById(orderInfo.getGiftCertificateId());
                    giftCertificates.add(giftCertificateMapper.toEntity(certificateDto));
                });
        BigDecimal orderCost = BigDecimal.ZERO;
        for (GiftCertificate giftCertificate : giftCertificates) {
            orderCost = orderCost.add(giftCertificate.getPrice());
        }
        Order order = new Order();
        order.setUser(userMapper.toEntity(userDto));
        order.setGiftCertificateList(giftCertificates);
        order.setAllCost(orderCost);
        Order newOrder = orderDao.add(order);
        return orderMapper.toDto(newOrder);

    }

    @Override
    public OrderDto findById(Long id) {
        return orderDao.findById(id).stream()
                .findAny()
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.NOT_FIND_ORDER_BY_ID,
                        ErrorCode.NOT_FIND_ORDER_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", Long.toString(id), ErrorCode.NOT_FIND_ORDER_BY_ID.getMessage()))
                ));
    }

    @Override
    public List<OrderDto> findByUserId(Long userId, PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        Pagination pagination = paginationMapper.toEntity(paginationDto);
        List<Order> orderList = orderDao.findByUserId(userId,pagination);
        return orderList.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.deleteById(id);
    }

    @Override
    public Integer getCountCountOrderByUserId(Long userId) {
        return orderDao.getCountCountOfElements(userId);
    }
}
