package by.epam.esm.util.link;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.OrderController;
import by.epam.esm.dto.entity.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class OrderLinkUtil
 * class extends AbstractLinkUtil and  contains methods for create links for order dto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class OrderLinkUtil extends AbstractLinkUtil<OrderDto> {
    private UserLinkUtil userLinkUtil;
    private GIftCertificateLinkUtil certificateLinkUtil;

    @Autowired
    public OrderLinkUtil(UserLinkUtil userLinkUtil, GIftCertificateLinkUtil certificateLinkUtil) {
        this.userLinkUtil = userLinkUtil;
        this.certificateLinkUtil = certificateLinkUtil;
    }

    @Override
    public void addLinks(List<OrderDto> list) {
        for (OrderDto order : list) {
            getOrderLinks(order);
        }
    }
    private void getOrderLinks(OrderDto orderDto) {
        orderDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .deleteById(orderDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));
        certificateLinkUtil.addLinks(orderDto.getGiftCertificateList());
        userLinkUtil.addLinks(List.of(orderDto.getUser()));
    }
}
