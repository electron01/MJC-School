package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.enity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    @Mappings({
            @Mapping(target = "id",source = "entity.id"),
            @Mapping(target = "name",source = "entity.name"),
            @Mapping(target = "price",source = "entity.price"),
            @Mapping(target = "duration",source = "entity.duration"),
            @Mapping(target = "createDate",source = "entity.createDate"),
            @Mapping(target = "updateDate",source = "entity.updateDate"),
            @Mapping(target = "tags",source = "entity.tags")
    })
    GiftCertificateDto giftCertificateToGiftCertificateDto(GiftCertificate entity);

    @Mappings({
            @Mapping(target = "id",source = "dto.id"),
            @Mapping(target = "name",source = "dto.name"),
            @Mapping(target = "price",source = "dto.price"),
            @Mapping(target = "duration",source = "dto.duration"),
            @Mapping(target = "createDate",source = "dto.createDate"),
            @Mapping(target = "updateDate",source = "dto.updateDate"),
            @Mapping(target = "tags",source = "dto.tags")
    })
    GiftCertificate giftCertificateDtoToGiftCertificate(GiftCertificateDto dto);
}
