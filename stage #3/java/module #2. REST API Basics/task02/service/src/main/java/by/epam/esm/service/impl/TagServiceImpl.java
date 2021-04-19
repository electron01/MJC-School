package by.epam.esm.service.impl;

import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.dto.mapper.TagMapper;
import by.epam.esm.enity.Tag;
import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.TagService;
import by.epam.esm.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private CrdTagDao tagDao;
    private BaseValidator baseValidator;
    private TagMapper tagMapper;
    private PaginationMapper paginationMapper;


    @Autowired
    public TagServiceImpl(CrdTagDao tagDao,
                          BaseValidator baseValidator,
                          TagMapper tagMapper,
                          PaginationMapper paginationMapper) {
        this.tagDao = tagDao;
        this.baseValidator = baseValidator;
        this.tagMapper = tagMapper;
        this.paginationMapper = paginationMapper;
    }

    @Override
    public List<TagDto> findByGiftCertificateId(Integer certificateId) {
        return tagDao.findByCertificateId(certificateId)
                .stream()
                .map(tagMapper::tagToTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTagByNameExist(String name) {
        return tagDao.findByName(name).isPresent();
    }

    @Override
    public TagDto findByName(String name) {
        return tagDao.findByName(name)
                .stream()
                .findAny()
                .map(tagMapper::tagToTagDTO)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID,
                        ErrorCode.NOT_FIND_TAG_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("name", name, ErrorCode.NOT_FIND_TAG_BY_ID.getMessage())
                        )));
    }

    @Override
    public List<TagDto> findAll(PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        return tagDao
                .findAll(paginationMapper.paginationDtoToPagination(paginationDto))
                .stream()
                .map(tagMapper::tagToTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Integer id) {
        return tagDao.findById(id)
                .stream()
                .findAny()
                .map(tagMapper::tagToTagDTO)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID,
                        ErrorCode.NOT_FIND_TAG_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", String.valueOf(id), ErrorCode.NOT_FIND_TAG_BY_ID.getMessage())
                        )));
    }

    @Override
    public TagDto add(TagDto dto) {
        baseValidator.dtoValidator(dto);
        if (tagDao.findByName(dto.getName()).isPresent()) {
            throw new ServiceException(ErrorCode.NAME_TAG_IS_ALREADY_EXIST,
                    ErrorCode.NAME_TAG_IS_ALREADY_EXIST.getMessage(),
                    Set.of(new ErrorMessage("name", dto.getName(), ErrorCode.NAME_TAG_IS_ALREADY_EXIST.getMessage())
                    ));
        }
        Tag tag = tagMapper.tagDtoToTag(dto);
        Tag newTag = tagDao.save(tag);
        return tagMapper.tagToTagDTO(newTag);
    }

    @Override
    public TagDto update(TagDto tagDto) {
        tagDao.update(tagMapper.tagDtoToTag(tagDto));
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (!(tagDao.deleteById(id))) {
            throw new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID,
                    ErrorCode.NOT_FIND_TAG_BY_ID.getMessage(),
                    Set.of(new ErrorMessage("id", String.valueOf(id), ErrorCode.NOT_FIND_TAG_BY_ID.getMessage()))
            );
        }
    }
}
