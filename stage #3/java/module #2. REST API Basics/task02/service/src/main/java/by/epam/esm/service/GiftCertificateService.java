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
     * method findAll
     * method for find all certificates dto
     * @param giftCertificateRequestParam - request param for find
     * @param paginationDto - offset and limit for find
     * @return Certificates dto list
     */
    List<GiftCertificateDto> findAll(DtoGiftCertificateRequestParam giftCertificateRequestParam, PaginationDto paginationDto);

    /**
     * method partUpdate
     * method for partial update certificate
     * @param giftCertificateDto - certificate dto for update
     * @return updated certificate dto
     */
    GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto);
}
