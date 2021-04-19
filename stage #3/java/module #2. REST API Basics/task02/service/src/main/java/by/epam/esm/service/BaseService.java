package by.epam.esm.service;

import by.epam.esm.dto.entity.EntityDto;

import java.io.Serializable;

public interface BaseService<E extends EntityDto,ID extends Serializable> {
    E findById(ID id);

    E add(E dto);

    E update(E e);

    void delete(ID id);
}
