package by.epam.esm.service;

import by.epam.esm.dto.entity.TagDto;

import java.util.List;

/**
 * class TagService
 * interface extends BaseService and contains methods for tag
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface TagService extends BaseService<TagDto, Long> {

     TagDto mostWidelyUsedTag();
    /**
     * method findByGiftCertificateId
     * method for find tags list related for certificate
     * @param certificateId - certificate id for find
     * @return Tags dto list
     */
    List<TagDto> findByGiftCertificateId(Long certificateId);

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
