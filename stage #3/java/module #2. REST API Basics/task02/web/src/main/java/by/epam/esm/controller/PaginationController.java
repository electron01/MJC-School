package by.epam.esm.controller;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
/**
 * interface PaginationController
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface PaginationController<T> {
    /**
     * Method call find all in class controller
     * @param webRequest - web parameters
     * @param page - number of page
     * @param limit - limit
     * @return List with pagination
     */
    ResponseEntity<PagedModel<T>> findAll(WebRequest webRequest, Integer page, Integer limit);
}
