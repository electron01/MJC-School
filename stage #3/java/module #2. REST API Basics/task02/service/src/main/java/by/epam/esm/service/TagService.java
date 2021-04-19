package by.epam.esm.service;

import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;

import java.util.List;
/**
 * class TagService
 * interface extends BaseService and contains methods for tag
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface TagService extends BaseService<TagDto, Integer> {
    /**
     * method findAll
     * method for find all tags
     * @param paginationDto - offset and limit for find
     * @return Tags dto list
     */
    List<TagDto> findAll(PaginationDto paginationDto);

    /**
     * method findByGiftCertificateId
     * method for find tags list related for certificate
     * @param certificateId - certificate id for find
     * @return Tags dto list
     */
    List<TagDto> findByGiftCertificateId(Integer certificateId);

    /**
     * method isTagByNameExist
     * method for check is already such a tag name
     * @param name - name for found
     * @return true if name already is exists
     */
    boolean isTagByNameExist(String name);

    /**
     * method got find tag by name
     * @param name - tag name for find
     * @return found tag dto
     */
    TagDto findByName(String name);
}
