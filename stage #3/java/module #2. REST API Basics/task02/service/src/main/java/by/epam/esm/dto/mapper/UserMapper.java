package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * interface UserMapper
 * interface contains a method to convert a dto tag to a tag and vice versa
 * @author Aliaksei Tkachuk
 * @version 1.0
 * @see User
 * @see UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * method toDto
     * method for convert User to User dto
     * @param entity - user for convert
     * @return user dto
     */
    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "login", source = "entity.login"),
//            @Mapping(target = "password", source = "entity.password"),
            @Mapping(target = "email", source = "entity.email"),
    })
    UserDto toDto(User entity);

    /**
     * method toEntity
     * method for convert UserDto to User
     * @param dto - User dto for convert
     * @return user
     */
    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "login", source = "dto.login"),
            @Mapping(target = "password", source = "dto.password"),
            @Mapping(target = "email", source = "dto.email"),
    })
    User toEntity(UserDto dto);
}
