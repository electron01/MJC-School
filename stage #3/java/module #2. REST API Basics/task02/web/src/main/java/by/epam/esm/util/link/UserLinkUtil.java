package by.epam.esm.util.link;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.UserController;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.UserDto;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class UserLinkUtil
 * class extends AbstractLinkUtil and contains methods for create links for user dto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class UserLinkUtil extends AbstractLinkUtil<UserDto> {
    @Override
    public void addLinks(List<UserDto> list) {
        for (UserDto userDto : list) {
            getUserLinks(userDto);
        }
    }

    private void getUserLinks(UserDto userDto) {
        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .findById(userDto.getId()))
                .withSelfRel());

        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .deleteById(userDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));

        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getOrders(userDto.getId(), Integer.valueOf(WebConstant.PAGE_DEFAULT_VALUE), Integer.valueOf(WebConstant.LIMIT_DEFAULT_VALUE)))
                .withRel(WebConstant.ORDERS));
    }

    public void addUserOrdersPagination(PagedModel pagedModel, Class<UserController> controllerClass, PaginationDto paginationDto, int page, Long userId) {
        long nextPage = pagedModel.getMetadata().getNumber() + 1;
        if (nextPage <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).getOrders(userId, page + 1, paginationDto.getLimit())).withRel(WebConstant.NEXT_PAGE));
        }

        long previousPage = pagedModel.getMetadata().getNumber() - 1;
        if (previousPage > 0 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).getOrders(userId, page - 1, paginationDto.getLimit())).withRel(WebConstant.PREVIOUS_PAGE));
        }

        if (page != 1 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).getOrders(userId, 1, paginationDto.getLimit())).withRel(WebConstant.FIRST_PAGE));
        }

        if (page < pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).getOrders(userId, (int) pagedModel.getMetadata().getTotalPages(), paginationDto.getLimit())).withRel(WebConstant.LAST_PAGE));

        }
    }
}
