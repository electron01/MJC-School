package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.enity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * interface GiftCertificateMapper
 * interface contains a method to convert a dto gift certificate to a gift certificate and vice versa
 * @see GiftCertificate
 * @see GiftCertificateDto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    /**
     * method toDto
     * method for convert gift certificate to gift certificate dto
     * @param entity - gift certificate for convert
     * @return gift certificate dto
     */
    @Mappings({
            @Mapping(target = "id",source = "entity.id"),
            @Mapping(target = "name",source = "entity.name"),
            @Mapping(target = "price",source = "entity.price"),
            @Mapping(target = "duration",source = "entity.duration"),
            @Mapping(target = "createDate",source = "entity.createDate"),
            @Mapping(target = "updateDate",source = "entity.updateDate"),
            @Mapping(target = "tags",source = "entity.tags")
    })
    GiftCertificateDto toDto(GiftCertificate entity);

    /**
     * method toEntity
     * method for convert gift certificate dto to gift certificate
     * @param dto - gift certificate dto for convert
     * @return gift certificate
     */
    @Mappings({
            @Mapping(target = "id",source = "dto.id"),
            @Mapping(target = "name",source = "dto.name"),
            @Mapping(target = "price",source = "dto.price"),
            @Mapping(target = "duration",source = "dto.duration"),
            @Mapping(target = "createDate",source = "dto.createDate"),
            @Mapping(target = "updateDate",source = "dto.updateDate"),
            @Mapping(target = "tags",source = "dto.tags")
    })
    GiftCertificate toEntity(GiftCertificateDto dto);
}
