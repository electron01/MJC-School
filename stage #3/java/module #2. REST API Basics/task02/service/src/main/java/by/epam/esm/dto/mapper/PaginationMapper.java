package by.epam.esm.dto.mapper;

import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.enity.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * interface PaginationMapper
 * interface contains a method to convert a dto pagination to a pagination
 * @see Pagination
 * @see PaginationDto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface PaginationMapper {
    /**
     * method paginationDtoToPagination
     * method for convert pagination dto to pagination
     * @param dto - pagination dto for convert
     * @return pagination
     */
    @Mappings({
            @Mapping(target = "startPosition", source = "dto.startPosition"),
            @Mapping(target = "limit", source = "dto.limit"),
    })
    Pagination paginationDtoToPagination(PaginationDto dto);
}
