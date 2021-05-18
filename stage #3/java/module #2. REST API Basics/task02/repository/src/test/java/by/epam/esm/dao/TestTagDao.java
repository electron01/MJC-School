package by.epam.esm.dao;


import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(classes = RepositoryConfig.class)
@ActiveProfiles("test")
@Transactional
public class TestTagDao {

    @Autowired
    private CrdTagDao tagDao;
    private static Pagination pagination;

    @BeforeAll
    static void init() {
        pagination = new Pagination();
        pagination.setStartPosition(0);
        pagination.setLimit(6);
    }

    @Test
    public void addNewTagTest() {
        //given
        Tag tag = new Tag();
        tag.setName("tag1");
        //when
        Tag newTag = tagDao.add(tag);
        //then
        Assertions.assertNotNull(newTag.getId());
    }

    @Test
    public void findTagByCorrectIdTest() {
        //given
        Tag tag = new Tag();
        tag.setName("tag1");
        Tag newTag = tagDao.add(tag);
        //when
        Optional<Tag> foundTag = tagDao.findById(newTag.getId());
        //then
        Assertions.assertEquals(Optional.of(newTag),foundTag);
    }

    @Test
    public void findTagByUnCorrectIdTest() {
        //given
        Long unCorrectId = -1l;
        //when
        Optional<Tag> foundTag = tagDao.findById(unCorrectId);
        //then
        Assertions.assertEquals(Optional.empty(),foundTag);
    }

    @Test
    public void updateTag(){
        //given
        Tag tag = new Tag();
        tag.setName("name1");
        Tag newTag = tagDao.add(tag);
        //when
        newTag.setName("newName");
        //then
        Assertions.assertThrows(UnsupportedOperationException.class,()->tagDao.update(newTag));
    }

    @Test
    public void findAllTagsWithPaginationTest(){
        //given
        createNewTag();
        Pagination pagination = new Pagination();
        pagination.setStartPosition(2);
        pagination.setLimit(1);
        //when
        List<Tag> foundTags = tagDao.findAll(new HashMap<>(), pagination);
        //then
        Assertions.assertTrue(foundTags.size()==1
                && foundTags.get(0).getName().equals("newTag3"));
    }

    @Test
    public void findAllTagsWithNameTest(){
        //given
        createNewTag();
        Map<String,String[]> params = new HashMap<>();
        params.put("name",new String[]{"newTag2"});
        //when
        List<Tag> foundTags = tagDao.findAll(params, pagination);
        //then
        Assertions.assertTrue(foundTags.size()==1
                && foundTags.get(0).getName().equals("newTag2"));
    }

    @Test
    public void findAllTagsWithParamsTest(){
        //given
        createNewTag();
        Map<String,String[]> params = new HashMap<>();
        params.put("name",new String[]{"newTag"});
        //when
        List<Tag> foundTags = tagDao.findAll(params, pagination);
        //then
        Assertions.assertTrue(foundTags.size()==5);
    }

    @Test
    public void findTagByCorrectName(){
        //given
        String tagName = "testTag";
        Tag tag = new Tag();
        tag.setName(tagName);
        tagDao.add(tag);
        //when
        Optional<Tag> foundTag = tagDao.findByName(tagName);
        //then
        Assertions.assertEquals(Optional.of(tag),foundTag);
    }

    @Test
    public void findTagByUnCorrectName(){
        //given
        String unCorrectTagName = "unCorrectTagName";
        //when
        Optional<Tag> foundTag = tagDao.findByName(unCorrectTagName);
        //then
        Assertions.assertEquals(Optional.empty(),foundTag);
    }


    @Test
    public void deleteTagByCorrectId(){
        //given
        Tag tag = new Tag();
        tag.setName("tag");
        Tag newTag = tagDao.add(tag);
        //when
        tagDao.deleteById(newTag.getId());
        //then
        Assertions.assertEquals(Optional.empty(),tagDao.findById(newTag.getId()));
    }

    @Test
    public void deleteTagByUnCorrectId(){
        //given
        Long unCorrectId = -1l;
        //when
        boolean wasDeleted = tagDao.deleteById(unCorrectId);
        //then
        Assertions.assertFalse(wasDeleted);
    }

    @Test
    public void findAllTagWithSort(){
        //given
        createNewTag();
        Map<String, String[]> params = new HashMap<>();
        params.put("sort",new String[]{"desc[name]"});
        //when
        List<Tag> tags = tagDao.findAll(params, pagination);
        //then
        Assertions.assertEquals("newTag5",tags.get(0).getName());
    }

    private void createNewTag(){
        Tag tag1 = new Tag();
        tag1.setName("newTag1");

        Tag tag2 = new Tag();
        tag2.setName("newTag2");

        Tag tag3 = new Tag();
        tag3.setName("newTag3");

        Tag tag4 = new Tag();
        tag4.setName("newTag4");

        Tag tag5 = new Tag();
        tag5.setName("newTag5");
        tagDao.add(tag1);
        tagDao.add(tag2);
        tagDao.add(tag3);
        tagDao.add(tag4);
        tagDao.add(tag5);
    }

}
