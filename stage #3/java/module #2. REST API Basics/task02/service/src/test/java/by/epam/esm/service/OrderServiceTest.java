package by.epam.esm.service;

import by.epam.esm.dao.OrderDao;
import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.mapper.GiftCertificateMapper;
import by.epam.esm.dto.mapper.OrderMapperImpl;
import by.epam.esm.dto.mapper.UserMapper;
import by.epam.esm.enity.Order;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private UserService userService;
    @Mock
    private GiftCertificateService giftCertificateService;
    @Spy
    private GiftCertificateMapper giftCertificateMapper;
    @Spy
    private UserMapper userMapper;
    @Spy
    private OrderMapperImpl orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    private static Long CORRECT_ID = 1L;
    private static Order correctOrder;
    private static List<Order> correctOrderList;
    @BeforeAll
    public static void init() {
        correctOrder = new Order();
        correctOrder.setId(CORRECT_ID);
        correctOrder.setAllCost(BigDecimal.ZERO);
        correctOrderList=List.of(new Order(),new Order(),new Order());
    }

    @Test
    public void findByCorrectIdTest(){
        //given
        Mockito.when(orderDao.findById(CORRECT_ID)).thenReturn(Optional.of(correctOrder));
        //when
        OrderDto orderDto = orderService.findById(CORRECT_ID);
        //then
        Assertions.assertEquals(correctOrder.getAllCost(),orderDto.getAllCost());
    }

    @Test
    public void findByUnCorrectIdTest(){
        //given
        Long unCorrectId = -1l;
        //when
        Mockito.when(orderDao.findById(unCorrectId)).thenReturn(Optional.empty());
        //then
        Assertions.assertThrows(ServiceException.class,()->orderService.findById(unCorrectId));
    }

    @Test
    public void findByCorrectUserIdTest(){
        //given
        Long userId = 1l;
        Mockito.when(orderDao.findByUserId(userId)).thenReturn(correctOrderList);
        //when
        List<OrderDto> orderList = orderService.findByUserId(userId);
        //then
        Assertions.assertTrue(orderList.size()==correctOrderList.size());
    }

    @Test
    public void findByUnCorrectUserIdTest(){
        //given
        Long unCorrectUserId = -1l;
        Mockito.when(orderDao.findByUserId(unCorrectUserId)).thenReturn(new ArrayList<>());
        //when
        List<OrderDto> orderList = orderService.findByUserId(unCorrectUserId);
        //then
        Assertions.assertTrue(orderList.isEmpty());
    }
}
