package by.epam.esm.dao;

import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;

import java.util.List;
import java.util.Optional;

public interface CrdTagDao extends BaseDao<Tag, Integer> {
    List<Tag> findAll(Pagination pagination);
    Optional<Tag> findByName(String tagName);
    List<Tag> findByCertificateId(Integer id);
}
