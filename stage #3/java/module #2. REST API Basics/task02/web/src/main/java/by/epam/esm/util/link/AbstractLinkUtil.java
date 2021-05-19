package by.epam.esm.util.link;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.PaginationController;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.util.UrlBuilder;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * class AbstractLinkUtil
 * class contains methods for create link
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public abstract class AbstractLinkUtil<T> {

    /**
     * addPageLinks
     * method creates link to pages
     * @param pagedModel - page meta data
     * @param controllerClass - rest controller
     * @param webRequest - request parameters
     * @param paginationDto - pagination dto
     * @see by.epam.esm.dto.entity.PaginationDto
     * @param page - number of page
     */
    public void addPageLinks(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        addFirstPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addNextPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addPreviousPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
        addLastPageLink(pagedModel, controllerClass, webRequest, paginationDto, page);
    }

    private void addNextPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        long nextPage = pagedModel.getMetadata().getNumber() + 1;
        if (nextPage <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, page + 1, paginationDto.getLimit())).slash(UrlBuilder.buildParams(webRequest.getParameterMap())).withRel(WebConstant.NEXT_PAGE));
        }
    }

    private void addPreviousPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        long nextPage = pagedModel.getMetadata().getNumber() - 1;
        if (nextPage > 0 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, page - 1, paginationDto.getLimit())).slash(UrlBuilder.buildParams(webRequest.getParameterMap())).withRel(WebConstant.PREVIOUS_PAGE));
        }
    }

    private void addFirstPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        if (page != 1 && page <= pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, 1, paginationDto.getLimit())).slash(UrlBuilder.buildParams(webRequest.getParameterMap())).withRel(WebConstant.FIRST_PAGE));
        }
    }

    private void addLastPageLink(PagedModel pagedModel, Class<? extends PaginationController> controllerClass, WebRequest webRequest, PaginationDto paginationDto, int page) {
        if (page < pagedModel.getMetadata().getTotalPages()) {
            pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerClass).findAll(webRequest, (int) pagedModel.getMetadata().getTotalPages(), paginationDto.getLimit())).slash(UrlBuilder.buildParams(webRequest.getParameterMap())).withRel(WebConstant.LAST_PAGE));
        }
    }

    /**
     * addLinks
     * method for add links to dto
     * @param list - dtos list
     */
    public abstract void addLinks(List<T> list);
}
