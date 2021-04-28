package by.epam.esm.dao;

import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * interface CrdTagDao
 * interface extends BaseDao and contains methods for work with tag
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface CrdTagDao extends BaseDao<Tag, Long> {
    /**
     * Method findAll
     * method for find tags list in database
     * @param pagination - offset and limit for find
     * @return Tags list
     */
    List<Tag> findAll(Pagination pagination);
    /**
     * method findByName
     * method for find tag ny name
     * @param tagName - tag name
     * @return Optional.of(tag) or Optional.empty if tag was not found
     */
    Optional<Tag> findByName(String tagName);
    /**
     * method findByCertificateId
     * method for find tags list related for certificate
     * @param id certificate identifier
     * @return Tags list
     */
    List<Tag> findByCertificateId(Long id);
}
