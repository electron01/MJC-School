package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.enity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * interface OrderMapper
 * interface contains a method to convert a dto order to a order and vice versa
 * @see Order
 * @see OrderDto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    /**
     * method toDto
     * method for convert order to order dto
     * @param entity - order for convert
     * @return order dto
     */
    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "allCost", source = "entity.allCost"),
            @Mapping(target = "creationDate", source = "entity.creationDate"),
            @Mapping(target = "user", source = "entity.user"),
            @Mapping(target = "giftCertificateList", source = "entity.giftCertificateList")
    })
    OrderDto toDto(Order entity);

    /**
     * method toEntity
     * method for convert orderDto to order
     * @param dto - order dto for convert
     * @return order
     */
    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "allCost", source = "dto.allCost"),
            @Mapping(target = "creationDate", source = "dto.creationDate"),
            @Mapping(target = "user", source = "dto.user"),
            @Mapping(target = "giftCertificateList", source = "dto.giftCertificateList")
    })
    Order toEntity(OrderDto dto);
}
