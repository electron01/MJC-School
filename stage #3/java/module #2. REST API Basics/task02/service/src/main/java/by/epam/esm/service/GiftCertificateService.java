package by.epam.esm.service;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto,Integer> {
    List<GiftCertificateDto> getAll(DtoGiftCertificateRequestParam giftCertificateRequestParam, PaginationDto paginationDto);
    GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto);
}
