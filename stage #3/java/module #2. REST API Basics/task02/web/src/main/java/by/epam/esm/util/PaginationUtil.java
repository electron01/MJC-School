package by.epam.esm.util;

import by.epam.esm.dto.entity.PaginationDto;
import org.springframework.hateoas.PagedModel;

public class PaginationUtil {

    public static PaginationDto getPaginationDto(int pageNumber, int limit){
        PaginationDto paginationDto =new PaginationDto();
        int offset = (pageNumber -1) * limit;
        paginationDto.setStartPosition(offset);
        paginationDto.setLimit(limit);
        return paginationDto;
    }

    public static PagedModel.PageMetadata getPageMetaData(PaginationDto paginationDto, int countOfElements){
        int page = paginationDto.getStartPosition() / paginationDto.getLimit() + 1;
        return new  PagedModel.PageMetadata(paginationDto.getLimit(),page,countOfElements);
    }


}
