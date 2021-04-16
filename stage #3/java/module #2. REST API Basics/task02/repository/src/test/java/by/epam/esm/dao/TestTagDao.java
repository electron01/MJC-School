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
    public void getAllTagTest() {
        List<Tag> tagList = tagDao.findAll(pagination);
        Assertions.assertEquals(tags, tagList);
    }

    @Test
    public void getAllTagsWithPagination() {
        Pagination pagination = new Pagination();
        pagination.setStartPosition(2);
        pagination.setLimit(2);
        List<Tag> expectedTags = tags.subList(pagination.getStartPosition(), pagination.getLimit() + pagination.getStartPosition());
        Assertions.assertEquals(expectedTags, tagDao.findAll(pagination));

    }

    @Test
    public void getTagByCorrectId() {
        Integer id = 1;
        Optional<Tag> tag = tagDao.findById(id);
        Assertions.assertEquals(Optional.of(tags.get(id)), tag);

    }

    @Test
    public void getTagByUnCorrectId() {
        Integer id = -1;
        Optional<Tag> tag = tagDao.findById(id);
        Assertions.assertEquals(Optional.empty(), tag);

    }

    @Test
    public void getTagByCorrectCertificateId() {
        Integer id = 1;
        List<Tag> tagByCertificateId = tagDao.getTagByCertificateId(id);
        Assertions.assertEquals(certificateTags, tagByCertificateId);

    }

    @Test
    public void getTagByUnCorrectCertificateId() {
        Integer id = -1;
        List<Tag> tagByCertificateId = tagDao.getTagByCertificateId(id);
        Assertions.assertTrue((tagByCertificateId).isEmpty());

    }

    @Test
    public void addNewTag() {
        Tag tag = new Tag();
        tag.setId(10);
        tag.setName("newTag");
        Tag savedTag = tagDao.save(tag);
        Assertions.assertEquals(tag.getName(), savedTag.getName());
        Assertions.assertNotNull(savedTag.getId());
    }

    @Test
    public void updateTag() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> tagDao.update(new Tag()));
    }

    @Test
    void deleteTagByCorrectId() {
        Tag tag = new Tag();
        tag.setId(11);
        tag.setName("tagNew12)");
        Tag savedTag = tagDao.save(tag);
        Integer id = savedTag.getId();
        Assertions.assertTrue(tagDao.deleteById(id));
    }

    @Test
    void deleteTagByUnCorrectId() {
        Integer id = -1;
        Assertions.assertFalse(tagDao.deleteById(id));
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
