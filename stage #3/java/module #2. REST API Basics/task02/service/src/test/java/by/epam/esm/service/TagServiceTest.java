package by.epam.esm.service;

import by.epam.esm.ServiceConfig;
import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.validator.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(ServiceConfig.class)
public class TagServiceTest {
    @Autowired
    private CrdTagDao tagDao;
    @Autowired
    private BaseValidator baseValidator;
    @Autowired
    private TagService tagService;
    private static Tag correctTag = new Tag();
    private static final Integer CORRECT_ID = 1;
    private static final Integer UN_CORRECT_ID = -1;
    private static final String TAG_NAME = "tagName";
    private static List<Tag> tagList;


    @BeforeAll
    public static void init() {
        correctTag.setId(CORRECT_ID);
        correctTag.setName(TAG_NAME);
        tagList = List.of(new Tag(), new Tag(), new Tag());
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(tagDao, baseValidator);
    }


    @Test
    public void testUnCorrectPaginationDtoTest() {
        PaginationDto unCorrectPaginationDto = new PaginationDto();
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(unCorrectPaginationDto);
        Assertions.assertThrows(ServiceException.class, () -> tagService.getAll(unCorrectPaginationDto));
    }

    @Test
    public void getTagByCorrectIdTest() {
        Tag tag = new Tag();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        Mockito.when(tagDao.findById(CORRECT_ID)).thenReturn(Optional.of(tag));
        TagDto findById = tagService.getById(CORRECT_ID);
        Assertions.assertEquals(tag.getName(), findById.getName());

    }

    @Test
    public void getTagByUnCorrectIdTest() {
        Mockito.when(tagDao.findById(UN_CORRECT_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(ServiceException.class, () -> tagService.getById(UN_CORRECT_ID));

    }

    @Test
    public void addCorrectTagTest() {
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        Mockito.when(tagDao.getTagByName(TAG_NAME)).thenReturn(Optional.empty());
        Mockito.when(tagDao.save(Mockito.any(Tag.class))).thenReturn(correctTag);
        TagDto newTag = tagService.add(tag);
        Assertions.assertEquals(correctTag.getName(), newTag.getName());
        Assertions.assertEquals(correctTag.getId(), newTag.getId());

    }

    @Test
    public void addUnCorrectTagTest() {
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(tag);
        Assertions.assertThrows(ServiceException.class, () -> tagService.add(tag));
    }

    @Test
    public void addDuplicateTagTest() {
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        Mockito.when(tagDao.getTagByName(TAG_NAME)).thenReturn(Optional.of(new Tag()));
        Mockito.when(tagDao.save(Mockito.any(Tag.class))).thenReturn(correctTag);
        Assertions.assertThrows(ServiceException.class, () -> tagService.add(tag));
    }

    @Test
    public void isTagByNameExistTest() {
        TagDto tag = new TagDto();
        tag.setName(TAG_NAME);
        Mockito.when(tagDao.getTagByName(TAG_NAME)).thenReturn(Optional.empty());
        Assertions.assertFalse(tagService.isTagByNameExist(tag.getName()));

    }

    @Test
    public void deleteByCorrectIdTest() {
        Mockito.when(tagDao.deleteById(CORRECT_ID)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> tagService.delete(CORRECT_ID));
    }

    @Test
    public void deleteByUnCorrectIdTest() {
        Mockito.when(tagDao.deleteById(CORRECT_ID)).thenReturn(false);
        Assertions.assertThrows(ServiceException.class, () -> tagService.delete(CORRECT_ID));
    }

    @Test
    public void getByCorrectGiftCertificateIdTest() {
        Mockito.when(tagDao.getTagByCertificateId(CORRECT_ID)).thenReturn(tagList);
        Assertions.assertTrue(tagList.size() == tagService.getByGiftCertificateId(CORRECT_ID).size());
    }

    @Test
    public void getByUnCorrectGiftCertificateIdTest() {
        Mockito.when(tagDao.getTagByCertificateId(UN_CORRECT_ID)).thenReturn(List.of());
        Assertions.assertTrue(tagService.getByGiftCertificateId(UN_CORRECT_ID).isEmpty());
    }

    @Test
    public void getAllTest() {
        PaginationDto paginationDto = new PaginationDto();
        Mockito.when(tagDao.findAll(Mockito.any(Pagination.class))).thenReturn(tagList);
        Assertions.assertTrue(tagList.size() == tagService.getAll(paginationDto).size());

    }


}
