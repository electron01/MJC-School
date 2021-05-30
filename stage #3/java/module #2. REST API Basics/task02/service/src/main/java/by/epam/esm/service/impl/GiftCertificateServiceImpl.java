package by.epam.esm.service.impl;

import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dao.GiftCertificateTagDao;
import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.dto.mapper.GiftCertificateMapper;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.dto.mapper.TagMapper;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.GiftCertificateService;
import by.epam.esm.service.TagService;
import by.epam.esm.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateMapper giftCertificateMapper;
    private CrudGiftCertificateDao giftCertificateDao;
    private BaseValidator baseValidator;
    private PaginationMapper paginationMapper;
    private TagService tagService;
    private GiftCertificateTagDao giftCertificateTagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateMapper giftCertificateMapper,
                                      CrudGiftCertificateDao giftCertificateDao,
                                      BaseValidator baseValidator,
                                      PaginationMapper paginationMapper,
                                      GiftCertificateTagDao giftCertificateTagDao,
                                      TagService tagService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.baseValidator = baseValidator;
        this.paginationMapper = paginationMapper;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return giftCertificateDao.findById(id).stream()
                .findAny()
                .map(giftCertificateMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.NOT_FIND_CERTIFICATE_BY_ID,
                        ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", Long.toString(id), ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage()))
                ));
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto dto) {
        baseValidator.dtoValidator(dto);
        checkIfGiftCertificateNameExists(dto);
        List<TagDto> tagsFromDb = new ArrayList<>();
        List<TagDto> newTags = checkAndUpdateTags(dto, tagsFromDb);
        dto.setTags(newTags);
        GiftCertificate entity = giftCertificateMapper.toEntity(dto);
        entity.setName(entity.getName().trim());
        GiftCertificateDto newCertificateDto = giftCertificateMapper.toDto(giftCertificateDao.add(entity));
        newCertificateDto.getTags().addAll(tagsFromDb);

        return newCertificateDto;
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        baseValidator.dtoValidator(giftCertificateDto);
        checkIfGiftCertificateNameExists(giftCertificateDto);
        if (isGiftCertificateIdIsExists(giftCertificateDto.getId())) {
            List<TagDto> tagsFromDb = new ArrayList<>();
            List<TagDto> newTags = checkAndUpdateTags(giftCertificateDto, tagsFromDb);
            giftCertificateTagDao.deleteCertificateTags(giftCertificateDto.getId());
            giftCertificateDto.setName(giftCertificateDto.getName().trim());
            giftCertificateDto.setTags(newTags);
            GiftCertificate updatedCertificate = giftCertificateDao
                    .update(giftCertificateMapper.toEntity(giftCertificateDto));

            giftCertificateTagDao.addCertificateTags(updatedCertificate.getId(),
                    tagsFromDb.stream().map(TagDto::getId).collect(Collectors.toList()));

            GiftCertificateDto updatedCertificateDto = giftCertificateMapper.toDto(updatedCertificate);
            updatedCertificateDto.getTags().addAll(tagsFromDb);
            return updatedCertificateDto;
        } else {
            throw new ServiceException(ErrorCode.NOT_FIND_CERTIFICATE_BY_ID,
                    ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage(),
                    Set.of(new ErrorMessage(
                            "id",
                            String.valueOf(giftCertificateDto.getId()),
                            ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage()
                    )));
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return giftCertificateDao.deleteById(id);

    }

    @Override
    public Integer getCountCountOfElements(Map<String, String[]> params) {
        return giftCertificateDao.getCountOfElements(params);
    }


    @Override
    public List<GiftCertificateDto> findAll(Map<String, String[]> params, PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        Pagination pagination = paginationMapper.toEntity(paginationDto);
        return giftCertificateDao.findAll(params, pagination)
                .stream()
                .map(giftCertificateMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto) {
        baseValidator.dtoValidatorForPartUpdate(giftCertificateDto);
        if (giftCertificateDto.getName() != null) {
            checkIfGiftCertificateNameExists(giftCertificateDto);
            giftCertificateDto.setName(giftCertificateDto.getName().trim());
        }
        GiftCertificateDto certificateFromDb = findById(giftCertificateDto.getId());
        List<TagDto> tagsFromDb = new ArrayList<>();
        List<TagDto> newTagList = checkAndUpdateTags(giftCertificateDto, tagsFromDb);
        if (giftCertificateDto.getTags() != null) {
            giftCertificateTagDao.deleteCertificateTags(giftCertificateDto.getId());
            giftCertificateDto.setTags(newTagList);
        } else {
            giftCertificateDto.setTags(certificateFromDb.getTags());
        }
        fillEmptyData(certificateFromDb, giftCertificateDto);
        GiftCertificate certificateForUpdate = giftCertificateMapper.toEntity(giftCertificateDto);
        GiftCertificateDto updatedCertificate = giftCertificateMapper.
                toDto(giftCertificateDao.update(certificateForUpdate));
        updatedCertificate.getTags().addAll(tagsFromDb);
        giftCertificateTagDao.addCertificateTags(giftCertificateDto.getId(),
                tagsFromDb.stream().map(TagDto::getId).collect(Collectors.toList()));
        return updatedCertificate;
    }


    //if user send tag with id
    private List<TagDto> checkAndUpdateTags(GiftCertificateDto giftCertificateDto, List<TagDto> tagsFromDb) {
        List<TagDto> listDto = giftCertificateDto.getTags();
        if (listDto != null) {
            for (TagDto tagDto : listDto) {
                if (tagDto.getId() != null) {
                    TagDto tag = tagService.findById(tagDto.getId());
                    if (tagDto.getName() != null && !(tag.getName().equals(tagDto.getName()))) {
                        throw new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME,
                                ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME.getMessage(),
                                Set.of(new ErrorMessage("id",
                                        Long.toString(tagDto.getId()),
                                        ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME.getMessage())));
                    }
                    tagDto.setName(tag.getName());
                    tagsFromDb.add(tagDto);
                }
            }
        }
        return checkTagsByNameAndSetId(giftCertificateDto, tagsFromDb);
    }

    private List<TagDto> checkTagsByNameAndSetId(GiftCertificateDto giftCertificateDto, List<TagDto> tagsFromDb) {
        List<TagDto> newTagForSave = new ArrayList<>();
        List<TagDto> listDto = giftCertificateDto.getTags();
        if (listDto != null) {
            for (TagDto tagDto : listDto) {
                if (tagDto.getId() == null) {
                    if (tagService.isTagByNameExist(tagDto.getName())) {
                        TagDto byName = tagService.findByName(tagDto.getName());
                        tagDto.setId(byName.getId());
                        tagsFromDb.add(tagDto);
                    } else {
                        TagDto tag = new TagDto();
                        tag.setName(tagDto.getName());
                        TagDto newTag = tagService.add(tag);
                        newTagForSave.add(newTag);
                    }
                }
            }
        }
        return newTagForSave;
    }


    private boolean isGiftCertificateIdIsExists(Long id) {
        return giftCertificateDao.findById(id).isPresent();
    }

    private boolean isGiftCertificateNameIsExists(String name) {
        return giftCertificateDao.findByName(name).isPresent();
    }

    private void checkIfGiftCertificateNameExists(GiftCertificateDto dto) {
        if (isGiftCertificateNameIsExists(dto.getName())) {
            throw new ServiceException(ErrorCode.CERTIFICATE_NAME_IS_ALREADY_EXISTS,
                    ErrorCode.CERTIFICATE_NAME_IS_ALREADY_EXISTS.getMessage(),
                    Set.of(new ErrorMessage(
                            "name",
                            dto.getName(),
                            ErrorCode.CERTIFICATE_NAME_IS_ALREADY_EXISTS.getErrorCode())
                    ));
        }
    }

    private void fillEmptyData(GiftCertificateDto giftCertificateFromDb, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null) {
            giftCertificateDto.setName(giftCertificateFromDb.getName());
        }
        if (giftCertificateDto.getDescription() == null) {
            giftCertificateDto.setDescription(giftCertificateFromDb.getDescription());
        }
        if (giftCertificateDto.getDuration() == null) {
            giftCertificateDto.setDuration(giftCertificateFromDb.getDuration());
        }
        if (giftCertificateDto.getPrice() == null) {
            giftCertificateDto.setPrice(giftCertificateFromDb.getPrice());
        }
    }
}



