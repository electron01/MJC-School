package by.epam.esm.service;

import by.epam.esm.dto.entity.GiftCertificateDto;

/**
 * class GiftCertificateService
 * interface extends BaseService and contains methods for gift certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto,Long> {
    /**xz
     * method partUpdate
     * method for partial update certificate
     * @param giftCertificateDto - certificate dto for update
     * @return updated certificate dto
     */
    GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto);
}
