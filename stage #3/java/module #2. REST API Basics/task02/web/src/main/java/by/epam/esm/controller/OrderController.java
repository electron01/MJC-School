package by.epam.esm.controller;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.service.OrderService;
import by.epam.esm.util.LinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto order = orderService.findById(id);
        LinkUtil.addOrderLinks(List.of(order));
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean wasDeleted = orderService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

}
