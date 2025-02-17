package by.epam.esm.service;

import by.epam.esm.dao.impl.TagDaoImpl;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.dto.mapper.PaginationMapperImpl;
import by.epam.esm.dto.mapper.TagMapperImpl;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.impl.TagServiceImpl;
import by.epam.esm.validator.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private BaseValidator baseValidator;
    @Spy
    private TagMapperImpl tagMapper;
    @Spy
    private PaginationMapperImpl paginationMapper;
    @InjectMocks
    private TagServiceImpl tagService;


    private static Tag correctTag = new Tag();
    private static final Long CORRECT_ID = 1l;
    private static final Long UN_CORRECT_ID = -1l;
    private static final String TAG_NAME = "tagName";
    private static List<Tag> tagList;


    @BeforeAll
    public static void init() {
        correctTag.setId(CORRECT_ID);
        correctTag.setName(TAG_NAME);
        tagList = List.of(new Tag(), new Tag(), new Tag());
    }

    @Test
    public void testUnCorrectPaginationDtoTest() {
        // Given Request for find all tags
        PaginationDto unCorrectPaginationDto = new PaginationDto();
        // When method findAll will start executing with unCorrect pagination params
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(unCorrectPaginationDto);
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> tagService.findAll(new HashMap<>(),unCorrectPaginationDto));
    }

    @Test
    public void findTagByCorrectIdTest() {
        // Given Request for find tags by id
        Tag tag = new Tag();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        // When method findById will start executing with correct id
        Mockito.when(tagDao.findById(CORRECT_ID)).thenReturn(Optional.of(tag));
        TagDto findById = tagService.findById(CORRECT_ID);
        //Then returned tag
        Assertions.assertEquals(tag.getName(), findById.getName());
    }

    @Test
    public void findTagByUnCorrectIdTest() {
        // Given Request for find tags by id
        // When method findById will start executing with unCorrect id
        Mockito.when(tagDao.findById(UN_CORRECT_ID)).thenReturn(Optional.empty());
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> tagService.findById(UN_CORRECT_ID));
    }

    @Test
    public void addCorrectTagTest() {
        // Given Request for create new tag
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        Mockito.when(tagDao.findByName(TAG_NAME)).thenReturn(Optional.empty());
        Mockito.when(tagDao.add(Mockito.any(Tag.class))).thenReturn(correctTag);
        // When method add will start executing with correct params
        TagDto newTag = tagService.add(tag);
        // Then returned new tag
        Assertions.assertEquals(correctTag.getName(), newTag.getName());
        Assertions.assertEquals(correctTag.getId(), newTag.getId());
    }

    @Test
    public void addUnCorrectTagTest() {
        // Given Request for create new tag
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        // When method add will start executing with unCorrect params
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(tag);
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> tagService.add(tag));
    }

    @Test
    public void addDuplicateTagTest() {
        // Given Request for create new tag
        TagDto tag = new TagDto();
        tag.setId(CORRECT_ID);
        tag.setName(TAG_NAME);
        // When method add will start executing with duplicate tag name
        Mockito.when(tagDao.findByName(TAG_NAME)).thenReturn(Optional.of(new Tag()));
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> tagService.add(tag));
    }

    @Test
    public void isTagByNameExistTest() {
        // Given Request to check if there is already such a tag name
        TagDto tag = new TagDto();
        tag.setName(TAG_NAME);
        // When method findByName will start executing new name
        Mockito.when(tagDao.findByName(TAG_NAME)).thenReturn(Optional.empty());
        // Then returned false
        Assertions.assertFalse(tagService.isTagByNameExist(tag.getName()));
    }

    @Test
    public void deleteByCorrectIdTest() {
        // Given Request for delete teg by id
        // When method deleteById will start executing with correct id
        Mockito.when(tagDao.deleteById(CORRECT_ID)).thenReturn(true);
        // Then we not have does exception
        Assertions.assertDoesNotThrow(() -> tagService.delete(CORRECT_ID));
    }

    @Test
    public void deleteByUnCorrectIdTest() {
        // Given Request for delete teg by id
        // When method deleteById will start executing with unCorrect id
        Mockito.when(tagDao.deleteById(UN_CORRECT_ID)).thenReturn(false);
        // Then get an exception (ServiceException)
        Assertions.assertFalse(tagService.delete(UN_CORRECT_ID));
    }

    @Test
    public void findByCorrectGiftCertificateIdTest() {
        // Given Request for find tags be certificate id
        // When method findByCertificateId will start executing with correct id
        Mockito.when(tagDao.findByCertificateId(CORRECT_ID)).thenReturn(tagList);
        //Then returned list should be contains tags related for certificate
        Assertions.assertTrue(tagList.size() == tagService.findByGiftCertificateId(CORRECT_ID).size());
    }

    @Test
    public void findByUnCorrectGiftCertificateIdTest() {
        // Given Request for find tags be certificate id
        // When method findByCertificateId will start executing with unCorrect id
        Mockito.when(tagDao.findByCertificateId(UN_CORRECT_ID)).thenReturn(List.of());
        //Then returned list should be empty
        Assertions.assertTrue(tagService.findByGiftCertificateId(UN_CORRECT_ID).isEmpty());
    }

    @Test
    public void findAllTest() {
        // Given Request for find all tags
        PaginationDto paginationDto = new PaginationDto();
        // When method findAll will start executing with default pagination params startPosition = 0 and Limit = 6
        Mockito.when(tagDao.findAll(Mockito.anyMap(),Mockito.any(Pagination.class))).thenReturn(tagList);
        //Then a complete  tag list should be received with startPosition = 0 and Limit = 6
        Assertions.assertTrue(tagList.size() == tagService.findAll(new HashMap<>(),paginationDto).size());
    }
}
