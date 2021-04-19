package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.enity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * interface TagMapper
 * interface contains a method to convert a dto tag to a tag and vice versa
 * @author Aliaksei Tkachuk
 * @version 1.0
 * @see Tag
 * @see TagDto
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    /**
     * method tagToTagDTO
     * method for convert tag to tag dto
     * @param entity - tag for convert
     * @return tag dto
     */
    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "name", source = "entity.name")
    })
    TagDto tagToTagDTO(Tag entity);

    /**
     * method tagToTagDTO
     * method for convert tag dto to tag
     * @param dto - tag dto for convert
     * @return tag
     */

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name")
    })
    Tag tagDtoToTag(TagDto dto);
}
