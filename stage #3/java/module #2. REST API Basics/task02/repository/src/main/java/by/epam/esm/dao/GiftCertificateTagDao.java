package by.epam.esm.dao;

import java.util.List;

/**
 * interface GiftCertificateTagDao
 * interface contains methods for work with table tag_gift_certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface GiftCertificateTagDao {
    /**
     * deleteCertificateTags
     * remove all certificate tags
     * @param certificateId - certificate id for delete tag
     */
    void deleteCertificateTags(Long certificateId);

    /**
     * addCertificateTags
     * add new tag for certificate
     * @param certificateId - certificate id for add
     * @param tagsId - tags id for add
     */
    void addCertificateTags(Long certificateId, List<Long> tagsId);
}
