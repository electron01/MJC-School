package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GiftCertificateRequestMapper {

    @Mappings({
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "description", source = "dto.description"),
            @Mapping(target = "tagName", source = "dto.tagName"),
            @Mapping(target = "sort", source = "dto.sort")
    })
    GiftCertificateRequestParam dtoGiftCertificateRequestToGiftCertificate(DtoGiftCertificateRequestParam dto);
}
