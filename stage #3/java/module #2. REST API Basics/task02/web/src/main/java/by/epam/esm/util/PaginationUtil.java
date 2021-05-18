package by.epam.esm.util;

import by.epam.esm.dto.entity.PaginationDto;
import org.springframework.hateoas.PagedModel;

/**
 * class PaginationUtil
 * class contains methods for work with pages
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class PaginationUtil {
    /**
     * getPaginationDto
     * method creates PaginationDto with request parameters
     * @param pageNumber - number of page
     * @param limit - limit
     * @return new paginationDto
     */
    public static PaginationDto getPaginationDto(int pageNumber, int limit){
        PaginationDto paginationDto =new PaginationDto();
        int offset = (pageNumber -1) * limit;
        paginationDto.setStartPosition(offset);
        paginationDto.setLimit(limit);
        return paginationDto;
    }

    /**
     * getPageMetaData
     * method creates MetaData for PagedModel
     * @param paginationDto - Pagination Dto from request parameters
     * @param countOfElements - total number of elements
     * @see by.epam.esm.dto.entity.PaginationDto
     * @see org.springframework.hateoas.PagedModel.PageMetadata
     * @return Page.Metadata with limit,number of page and total number of elements
     */
    public static PagedModel.PageMetadata getPageMetaData(PaginationDto paginationDto, int countOfElements){
        int page = paginationDto.getStartPosition() / paginationDto.getLimit() + 1;
        return new  PagedModel.PageMetadata(paginationDto.getLimit(),page,countOfElements);
    }
}
