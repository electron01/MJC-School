package by.epam.esm.dao;

import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = RepositoryConfig.class)
@ActiveProfiles("test")
@Transactional
public class TestOrderDao {
    @Autowired
    private OrderDao orderDao;

    @Test
    public void findByCorrectIdTest(){
        //given
        Order order = createOrder();
        //when
        Optional<Order> foundOrder = orderDao.findById(order.getId());
        //then
        Assertions.assertNotEquals(Optional.empty(),foundOrder);
    }
    @Test
    public void findByUnCorrectIdTest(){
        //given
        Long id = -1l;
        //when
        Optional<Order> foundOrder = orderDao.findById(id);
        //then
        Assertions.assertEquals(Optional.empty(),foundOrder);
    }

    @Test
    public void addNewOrderTest(){
        //given
        Order order = new Order();
        order.setAllCost(BigDecimal.valueOf(80.00).setScale(2));
        //when
        Order newOrder = orderDao.add(order);
        //then
        Assertions.assertNotNull(newOrder.getId());
    }

//

    private Order createOrder(){
        Order order = new Order();
        order.setAllCost(BigDecimal.valueOf(80.00).setScale(2));
        return orderDao.add(order);
    }
}
