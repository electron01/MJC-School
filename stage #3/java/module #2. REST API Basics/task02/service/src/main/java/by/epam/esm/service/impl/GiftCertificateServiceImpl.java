package by.epam.esm.service.impl;

import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.dto.mapper.GiftCertificateMapper;
import by.epam.esm.dto.mapper.GiftCertificateRequestMapper;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.GiftCertificateService;
import by.epam.esm.service.TagService;
import by.epam.esm.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateMapper giftCertificateMapper;
    private CrudGiftCertificateDao giftCertificateDao;
    private BaseValidator baseValidator;
    private PaginationMapper paginationMapper;
    private GiftCertificateRequestMapper giftCertificateRequestMapper;
    private TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateMapper giftCertificateMapper,
                                      CrudGiftCertificateDao giftCertificateDao,
                                      BaseValidator baseValidator,
                                      PaginationMapper paginationMapper,
                                      GiftCertificateRequestMapper giftCertificateRequestMapper,
                                      TagService tagService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateDao = giftCertificateDao;
        this.baseValidator = baseValidator;
        this.paginationMapper = paginationMapper;
        this.giftCertificateRequestMapper = giftCertificateRequestMapper;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificateDto getById(Integer id) {
        GiftCertificateDto giftCertificateDto = giftCertificateDao.findById(id).stream()
                .findAny()
                .map(giftCertificateMapper::giftCertificateToGiftCertificateDto)
                .orElseThrow(() -> new ServiceException(
                        ErrorCode.NOT_FIND_CERTIFICATE_BY_ID,
                        ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", Integer.toString(id), ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage()))
                ));
        return setTagsForDto(giftCertificateDto);
    }

    private GiftCertificateDto setTagsForDto(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setTags(tagService.getByGiftCertificateId(giftCertificateDto.getId()));
        return giftCertificateDto;

    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto dto) {
        baseValidator.dtoValidator(dto);
        checkIsGiftCertificateNameExists(dto);
        checkAndUpdateTags(dto.getTags());
        GiftCertificate newGiftCertificate = giftCertificateMapper.giftCertificateDtoToGiftCertificate(dto);
        GiftCertificateDto saveDto = giftCertificateMapper
                .giftCertificateToGiftCertificateDto(giftCertificateDao.save(newGiftCertificate));
        return setTagsForDto(saveDto);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        baseValidator.dtoValidator(giftCertificateDto);
        checkIsGiftCertificateNameExists(giftCertificateDto);
        checkAndUpdateTags(giftCertificateDto.getTags());//update tags

        if (isGiftCertificateIdIsExists(giftCertificateDto.getId())) {
            GiftCertificate updatedCertificate = giftCertificateDao
                    .update(giftCertificateMapper.giftCertificateDtoToGiftCertificate(giftCertificateDto));
            return setTagsForDto(giftCertificateMapper.giftCertificateToGiftCertificateDto(updatedCertificate));
        } else {
            GiftCertificate newGiftCertificate = giftCertificateMapper.giftCertificateDtoToGiftCertificate(giftCertificateDto);
            return setTagsForDto(giftCertificateMapper.giftCertificateToGiftCertificateDto(giftCertificateDao.save(newGiftCertificate)));

        }

    }

    @Override
    public void delete(Integer id) {
        if (!(giftCertificateDao.deleteById(id))) {
            throw new ServiceException(ErrorCode.NOT_FIND_CERTIFICATE_BY_ID,
                    ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage(),
                    Set.of(new ErrorMessage("id", String.valueOf(id), ErrorCode.NOT_FIND_CERTIFICATE_BY_ID.getMessage()))
            );

        }

    }


    @Override
    public List<GiftCertificateDto> getAll(DtoGiftCertificateRequestParam CertificateRequestDto,
                                           PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        baseValidator.dtoValidator(CertificateRequestDto);
        GiftCertificateRequestParam giftCertificateRequestParam = giftCertificateRequestMapper.dtoGiftCertificateRequestToGiftCertificate(CertificateRequestDto);
        Pagination pagination = paginationMapper.paginationDtoToPagination(paginationDto);
        List<GiftCertificateDto> dtoList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination)
                .stream()
                .map(giftCertificateMapper::giftCertificateToGiftCertificateDto)
                .collect(Collectors.toList());
        dtoList.forEach(this::setTagsForDto);
        return dtoList;

    }

    @Override
    public GiftCertificateDto partUpdate(GiftCertificateDto giftCertificateDto) {
        baseValidator.dtoValidatorForPartUpdate(giftCertificateDto);
        if (giftCertificateDto.getName() != null) {
            checkIsGiftCertificateNameExists(giftCertificateDto);
        }
        checkAndUpdateTags(giftCertificateDto.getTags());
        GiftCertificate certificateForUpdate = giftCertificateMapper.giftCertificateDtoToGiftCertificate(giftCertificateDto);
        GiftCertificateDto updatedCertificate = giftCertificateMapper.
                giftCertificateToGiftCertificateDto(giftCertificateDao.partUpdate(certificateForUpdate));
        return setTagsForDto(updatedCertificate);
    }

    //if user send tag with id
    private void checkAndUpdateTags(List<TagDto> listDto) {
        if (listDto != null) {
            listDto.stream()
                    .filter(tagDto -> tagDto.getName() != null)
                    .filter(tagDto -> tagDto.getId() != null)
                    .forEach(tagDto ->
                    {
                        TagDto tag = tagService.getById(tagDto.getId());
                        if (!(tag.getName().equals(tagDto.getName()))) {
                            throw new ServiceException(ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME,
                                    ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME.getMessage(),
                                    Set.of(new ErrorMessage("id",
                                            Integer.toString(tagDto.getId()),
                                            ErrorCode.NOT_FIND_TAG_BY_ID_WITH_THIS_NAME.getMessage())));
                        }

                    });

        }
        checkTagsByNameAndSetId(listDto);
    }

    //if user send only name
    //if name is exist set id
    //else create new tag
    private void checkTagsByNameAndSetId(List<TagDto> listDto) {
        if (listDto != null) {
            listDto.stream()//todo check name?
                    .filter(tagDto -> tagDto.getId() == null)
                    .forEach(tagDto ->
                    {
                        if (tagService.isTagByNameExist(tagDto.getName())) {
                            TagDto byName = tagService.getByName(tagDto.getName());
                            tagDto.setId(byName.getId());
                        } else {
                            createNewTag(tagDto);
                        }
                    });
        }

    }

    private void createNewTag(TagDto newTagDto) {
        TagDto createdTag = tagService.add(newTagDto);
        newTagDto.setId(createdTag.getId());

    }

    private boolean isGiftCertificateIdIsExists(Integer id) {
        return giftCertificateDao.findById(id).isPresent();
    }

    private boolean isGiftCertificateNameIsExists(String name) {
        return giftCertificateDao.getGiftCertificateByName(name).isPresent();

    }

    private void checkIsGiftCertificateNameExists(GiftCertificateDto dto) {
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


}


