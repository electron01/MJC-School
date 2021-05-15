package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "login", source = "entity.login"),
//            @Mapping(target = "password", source = "entity.password"),
            @Mapping(target = "email", source = "entity.email"),
    })
    UserDto toDto(User entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "login", source = "dto.login"),
            @Mapping(target = "password", source = "dto.password"),
            @Mapping(target = "email", source = "dto.email"),
    })
    User toEntity(UserDto dto);
}
