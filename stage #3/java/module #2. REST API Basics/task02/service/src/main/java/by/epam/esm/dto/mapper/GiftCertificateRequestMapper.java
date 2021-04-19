package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * interface GiftCertificateRequestMapper
 * interface contains a method to convert a dto gift certificate request params to a gift certificate request param
 * @see GiftCertificateRequestParam
 * @see DtoGiftCertificateRequestParam
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateRequestMapper {
    /**
     * method dtoGiftCertificateRequestToGiftCertificate
     * method for convert dto gift certificate request params to gift certificate request param
     * @param dto - dto gift certificate request params for convert
     * @return gift certificate request param
     */
    @Mappings({
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "description", source = "dto.description"),
            @Mapping(target = "tagName", source = "dto.tagName"),
            @Mapping(target = "sort", source = "dto.sort")
    })
    GiftCertificateRequestParam dtoGiftCertificateRequestToGiftCertificate(DtoGiftCertificateRequestParam dto);
}
