package by.epam.esm.dao;


import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(RepositoryConfig.class)
public class TestTagDao {

    @Autowired
    private CrdTagDao tagDao;
    private static Pagination pagination = new Pagination();

    private static List<Tag> tags;
    private static List<Tag> certificateTags;


    @BeforeAll
    public static void initBeforeTest() {
        pagination.setLimit(6);
        pagination.setStartPosition(0);
        initTagList();

    }

    @Test
    public void findAllTagTest() {
        // Given Request for find all tags
        // When method findAll will start executing with default pagination params
        List<Tag> tagList = tagDao.findAll(pagination);
        //Then a complete tag list should be received with startPosition = 0 and Limit = 6
        Assertions.assertEquals(tags, tagList);
    }

    @Test
    public void findAllTagsWithPagination() {
        // Given Request for find all tags
        Pagination pagination = new Pagination();
        pagination.setStartPosition(2);
        pagination.setLimit(2);
        // When method findAll will start executing with pagination params startPosition = 2 and Limit = 2
        List<Tag> expectedTags = tags.subList(pagination.getStartPosition(), pagination.getLimit() + pagination.getStartPosition());
        //Then a complete tag list should be received with startPosition = 2 and Limit = 2
        Assertions.assertEquals(expectedTags, tagDao.findAll(pagination));
    }

    @Test
    public void findTagByCorrectId() {
        // Given Request for find tag by id
        Integer id = 1;
        // When method find will start executing with correct id
        Optional<Tag> tag = tagDao.findById(id);
        //Then returned Optional should be contains tag
        Assertions.assertEquals(Optional.of(tags.get(id)), tag);
    }

    @Test
    public void findTagByUnCorrectId() {
        // Given Request for find tag by id
        Integer id = -1;
        // When method find will start executing with unCorrect id
        Optional<Tag> tag = tagDao.findById(id);
        //Then returned Optional should be empty
        Assertions.assertEquals(Optional.empty(), tag);
    }

    @Test
    public void findTagByCorrectCertificateId() {
        // Given Request for find tag by certificate id
        Integer id = 1;
        // When method findByCertificateId will start executing with correct id
        List<Tag> tagByCertificateId = tagDao.findByCertificateId(id);
        //Then returned list should be contains tags related for certificate
        Assertions.assertEquals(certificateTags, tagByCertificateId);
    }

    @Test
    public void findTagByUnCorrectCertificateId() {
        // Given Request for find tag by certificate id
        Integer id = -1;
        // When method findByCertificateId will start executing with unCorrect id
        List<Tag> tagByCertificateId = tagDao.findByCertificateId(id);
        //Then returned list should be empty
        Assertions.assertTrue((tagByCertificateId).isEmpty());
    }

    @Test
    public void addNewTag() {
        // Given Request for add new tag
        Tag tag = new Tag();
        tag.setId(10);
        tag.setName("newTag");
        // When method save will start executing with correct tag
        Tag savedTag = tagDao.save(tag);
        // Then new tag must be saved with an autoIncrement identifier
        Assertions.assertEquals(tag.getName(), savedTag.getName());
        Assertions.assertNotNull(savedTag.getId());
    }

    @Test
    public void updateTag() {
        // Given Request for update  tag
        // When method update will start executing
        // Then get an exception (UnsupportedOperationException)
        Assertions.assertThrows(UnsupportedOperationException.class, () -> tagDao.update(new Tag()));
    }

    @Test
    void deleteTagByCorrectId() {
        // Given Request for delete tag by id
        Tag tag = new Tag();
        tag.setId(11);
        tag.setName("tagNew12)");
        Tag savedTag = tagDao.save(tag);
        // When method deleteById will start executing with correct id
        boolean deleteById = tagDao.deleteById(savedTag.getId());
        //Then certificate must be removed and method delete must be return true
        Assertions.assertTrue(deleteById);
    }

    @Test
    void deleteTagByUnCorrectId() {
        // Given Request for delete tag by id
        Integer id = -1;
        // When method deleteById will start executing with unCorrect id
        boolean deleteById = tagDao.deleteById(id);
        //Then certificate must be return false
        Assertions.assertFalse(deleteById);
    }


    private static void initTagList() {
        tags = new ArrayList<>();
        certificateTags = new ArrayList<>();
        Integer id = 0;
        String[] names = {"Extreme", "For him and for her", "Training", "Flying", "In another country", "Ride and Walk"};
        for (String name : names) {
            Tag tag = new Tag();
            tag.setId(id++);
            tag.setName(name);
            tags.add(tag);
        }
        certificateTags.add(tags.get(0));
        certificateTags.add(tags.get(1));
        certificateTags.add(tags.get(3));
    }
}
