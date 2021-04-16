package by.epam.esm.service;

import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;

import java.util.List;

public interface TagService extends BaseService<TagDto, Integer> {
    List<TagDto> getAll(PaginationDto paginationDto);
    List<TagDto> getByGiftCertificateId(Integer certificateId);
    boolean isTagByNameExist(String name);
    TagDto getByName(String name);

}
