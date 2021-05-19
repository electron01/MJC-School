package by.epam.esm.controller;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.service.OrderService;
import by.epam.esm.util.link.OrderLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * class OrderController
 * this class is a rest controller with request mapping "/app/orders"
 * this rest controller contains GET,POST Mapping
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@RestController
@RequestMapping("/app/orders")
public class OrderController {
    private OrderService orderService;
    private OrderLinkUtil orderLinkUtil;

    @Autowired
    public OrderController(OrderService orderService,OrderLinkUtil orderLinkUtil) {
        this.orderService = orderService;
        this.orderLinkUtil=orderLinkUtil;
    }

    /**
     * findById
     * get mapping
     * method for find order by id
     * @param id - id for find
     * @return OrderDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto order = orderService.findById(id);
        orderLinkUtil.addLinks(List.of(order));
        return ResponseEntity.ok(order);
    }

    /**
     * deleteById
     * delete mapping
     * method for delete order
     * @param id - id for delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean wasDeleted = orderService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }
}
