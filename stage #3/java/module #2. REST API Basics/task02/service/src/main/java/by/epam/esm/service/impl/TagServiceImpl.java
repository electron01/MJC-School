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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
    public List<TagDto> findByGiftCertificateId(Long certificateId) {
        return tagDao.findByCertificateId(certificateId)
                .stream()
                .map(tagMapper::toDto)
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
                .map(tagMapper::toDto)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID,
                        ErrorCode.NOT_FIND_TAG_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("name", name, ErrorCode.NOT_FIND_TAG_BY_ID.getMessage())
                        )));
    }

    @Override
    public Integer getCountCountOfElements(Map<String, String[]> params) {
        return tagDao.getCountOfElements(params);
    }

    @Override
    public List<TagDto> findAll(Map<String, String[]> params, PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        return tagDao
                .findAll(params,paginationMapper.toEntity(paginationDto))
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Long id) {
        return tagDao.findById(id)
                .stream()
                .findAny()
                .map(tagMapper::toDto)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID,
                        ErrorCode.NOT_FIND_TAG_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", String.valueOf(id), ErrorCode.NOT_FIND_TAG_BY_ID.getMessage())
                        )));
    }

    @Override
    @Transactional
    public TagDto add(TagDto dto) {
        baseValidator.dtoValidator(dto);
        if (tagDao.findByName(dto.getName()).isPresent()) {
            throw new ServiceException(ErrorCode.NAME_TAG_IS_ALREADY_EXIST,
                    ErrorCode.NAME_TAG_IS_ALREADY_EXIST.getMessage(),
                    Set.of(new ErrorMessage("name", dto.getName(), ErrorCode.NAME_TAG_IS_ALREADY_EXIST.getMessage())
                    ));
        }
        Tag tag = tagMapper.toEntity(dto);
        tag.setName(tag.getName().trim());
        Tag newTag = tagDao.add(tag);
        return tagMapper.toDto(newTag);
    }

    @Override
    public TagDto update(TagDto tagDto) {
        tagDao.update(tagMapper.toEntity(tagDto));
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return tagDao.deleteById(id);
    }
}
