package by.epam.esm.util;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.*;
import by.epam.esm.dto.entity.*;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public class LinkUtil {

    public static void addTagLinks(List<TagDto> tagList) {
        for (TagDto tagDto : tagList) {
            getTagLinks(tagDto);
        }
    }

    public static void addOrderLinks(List<OrderDto> orderList) {
        for (OrderDto order : orderList) {
            getOrderLinks(order);
        }
    }


    public static void addUserLinks(List<UserDto> userList) {
        for (UserDto userDto : userList) {
            getUserLinks(userDto);
        }
    }

    private static void getUserLinks(UserDto userDto) {
        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .findById(userDto.getId()))
                .withSelfRel());

        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .deleteById(userDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));

        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getOrders(userDto.getId()))
                .withRel(WebConstant.ORDERS));
    }

    private static void getOrderLinks(OrderDto orderDto) {
        orderDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .deleteById(orderDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));
        addCertificateLinks(orderDto.getGiftCertificateList());
        getUserLinks(orderDto.getUser());

    }

    public static void addCertificateLinks(List<GiftCertificateDto> certificateList) {
        for (GiftCertificateDto certificate : certificateList) {
            getCertificateLinks(certificate);
        }
        certificateList.stream()
                .forEach(certificate -> addTagLinks(certificate.getTags()));
    }

    public static void addPageLinks(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        addNextPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addPreviousPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addFirstPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addLastPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
    }

    private static void getTagLinks(TagDto tagDto) {
        tagDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .findTagById(tagDto.getId()))
                .withSelfRel());

        tagDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .deleteTag(tagDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));
    }

    private static void getCertificateLinks(GiftCertificateDto certificateDto) {
        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .findById(certificateDto.getId()))
                .withSelfRel());

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .deleteById(certificateDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .updateGiftCertificate(certificateDto, certificateDto.getId()))
                .withRel(WebConstant.UPDATE)
                .withName(WebConstant.UPDATE_METHOD));

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .updatePartOfCertificate(certificateDto.getId(), certificateDto))
                .withRel(WebConstant.PART_UPDATE)
                .withName(WebConstant.PART_UPDATE_METHOD));
    }

    private static void addNextPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        long nextPage = pagedModel.getMetadata().getNumber() + 1;
        if (nextPage <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, page + 1, paginationDto.getLimit())).withRel(WebConstant.NEXT_PAGE));
        }
    }

    private static void addPreviousPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        long nextPage = pagedModel.getMetadata().getNumber() - 1;
        if (nextPage > 0 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, page - 1, paginationDto.getLimit())).withRel(WebConstant.PREVIOUS_PAGE));
        }
    }

    private static void addFirstPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        if (page != 1 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, 1, paginationDto.getLimit())).withRel(WebConstant.FIRST_PAGE));
        }
    }

    private static void addLastPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        if (page < pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, (int) pagedModel.getMetadata().getTotalPages(), paginationDto.getLimit())).withRel(WebConstant.LAST_PAGE));
        }
    }
}
