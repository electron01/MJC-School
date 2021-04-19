package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.enity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mappings({
            @Mapping(target = "id",source = "entity.id"),
            @Mapping(target = "name",source = "entity.name")
    })
    TagDto tagToTagDTO(Tag entity);

    @Mappings({
            @Mapping(target = "id",source = "dto.id"),
            @Mapping(target = "name",source = "dto.name")
    })
    Tag tagDtoToTag(TagDto dto);
}
