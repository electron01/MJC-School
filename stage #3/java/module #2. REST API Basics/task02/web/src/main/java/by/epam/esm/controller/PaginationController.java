package by.epam.esm.controller;

import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface PaginationController<T> {
    ResponseEntity<PagedModel<T>> findAll(WebRequest webRequest, Integer page, Integer limit);
}
