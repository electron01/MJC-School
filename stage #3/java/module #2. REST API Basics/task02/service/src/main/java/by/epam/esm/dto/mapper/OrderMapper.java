package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.enity.Order;
import by.epam.esm.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "allCost", source = "entity.allCost"),
            @Mapping(target = "user", source = "entity.user"),
            @Mapping(target = "giftCertificateList", source = "entity.giftCertificateList")
    })
    OrderDto toDto(Order entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "allCost", source = "dto.allCost"),
            @Mapping(target = "user", source = "dto.user"),
            @Mapping(target = "giftCertificateList", source = "dto.giftCertificateList")
    })
    Order toEntity(OrderDto dto);
}
