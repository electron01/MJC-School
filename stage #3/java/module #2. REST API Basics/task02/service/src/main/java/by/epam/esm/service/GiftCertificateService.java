package by.epam.esm.service;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;

import java.util.List;

/**
 * class GiftCertificateService
 * interface extends BaseService and contains methods for gift certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto,Long> {
    /**
     * method partUpdate
     * method for partial update certificate
     * @param giftCertificateDto - certificate dto for update
     * @return updated certificate dto
     */
    GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto);
}
