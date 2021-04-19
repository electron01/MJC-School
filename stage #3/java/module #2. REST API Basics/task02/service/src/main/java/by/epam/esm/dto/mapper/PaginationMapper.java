package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.enity.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaginationMapper {
    @Mappings({
            @Mapping(target = "startPosition", source = "dto.startPosition"),
            @Mapping(target = "limit", source = "dto.limit"),
    })
    Pagination paginationDtoToPagination(PaginationDto dto);
}
