package by.epam.esm.dao;

import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest(classes = RepositoryConfig.class)
@ActiveProfiles("test")
@Transactional
public class TestGiftCertificateDao {

    @Autowired
    private CrudGiftCertificateDao giftCertificateDao;

    private static Pagination pagination;


    @BeforeAll
    public static void initBeforeTest() {
        pagination = new Pagination();
        pagination.setStartPosition(0);
        pagination.setLimit(6);
    }

    @Test
    public void addCertificateTest() {
        //given
        GiftCertificate giftCertificate = createNewCertificate();
        //when
        GiftCertificate newCertificate = giftCertificateDao.add(giftCertificate);
        //then
        Assertions.assertNotNull(newCertificate.getId());
    }

    @Test
    public void updateTag() {
        //given
        GiftCertificate certificate = createNewCertificate();
        String newName = "newCertificateName";
        GiftCertificate newCertificate = giftCertificateDao.add(certificate);
        //when
        certificate.setName(newName);
        GiftCertificate updatedCertificate = giftCertificateDao.update(newCertificate);
        //then
        Assertions.assertEquals(newName, updatedCertificate.getName());
    }

    @Test
    public void findByCorrectId() {
        //given
        GiftCertificate giftCertificate = createNewCertificate();
        GiftCertificate newCertificate = giftCertificateDao.add(giftCertificate);
        //when
        Optional<GiftCertificate> foundTag = giftCertificateDao.findById(newCertificate.getId());
        //then
        Assertions.assertEquals(Optional.of(newCertificate), foundTag);
    }

    @Test
    public void findByUnCorrectId() {
        //given
        Long unCorrectId = -1l;
        //when
        Optional<GiftCertificate> foundTag = giftCertificateDao.findById(unCorrectId);
        //then
        Assertions.assertEquals(Optional.empty(), foundTag);
    }

    @Test
    public void findAllCertificateWithStandardPagination(){
        //given
        createCertificateList();
        //when
        List<GiftCertificate> certificates = giftCertificateDao.findAll(new HashMap<>(), pagination);
        //then
        Assertions.assertTrue(certificates.size()==5);
    }

    @Test
    public void findAllCertificateWithPagination(){
        //given
        createCertificateList();
        Pagination pagination = new Pagination();
        pagination.setStartPosition(2);
        pagination.setLimit(1);
        //when
        List<GiftCertificate> certificates = giftCertificateDao.findAll(new HashMap<>(), pagination);
        //then
        Assertions.assertTrue(certificates.size()==1
                && certificates.get(0).getName().equals("Airsoft"));
    }
    @Test
    public void findAllWithNameLike(){
        //given
        createCertificateList();
        Map<String, String[]> params = new HashMap<>();
        params.put("name",new String[]{"Quad"});
        //when
        List<GiftCertificate> certificates = giftCertificateDao.findAll(params, pagination);
        //then
        Assertions.assertTrue(certificates.size()==1 && certificates.get(0).getName().equals("Quad biking"));
    }

    @Test
    public void findAllWithDescriptionLike(){
        //given
        createCertificateList();
        Map<String, String[]> params = new HashMap<>();
        params.put("description",new String[]{"jumps"});
        //when
        List<GiftCertificate> certificates = giftCertificateDao.findAll(params, pagination);
        //then
        Assertions.assertTrue(certificates.size()==1 && certificates.get(0).getDescription().equals("Three jumps from the tower (35m)"));
    }

    @Test
    public void findAllWithPriceLike(){
        //given
        createCertificateList();
        Map<String, String[]> params = new HashMap<>();
        params.put("price",new String[]{"90.00"});
        //when
        List<GiftCertificate> certificates = giftCertificateDao.findAll(params, pagination);
        //then
        Assertions.assertTrue(certificates.size()==1
                && certificates.get(0).getPrice().compareTo(new BigDecimal(90))==0);
    }

    @Test
    public void findAllWithSort(){
        //given
        List<GiftCertificate> expectedList = createCertificateList();
        Map<String, String[]> params = new HashMap<>();
        params.put("sort",new String[]{"asc[duration]","desc[name]"});
        expectedList.sort(Comparator.comparingInt(GiftCertificate::getDuration));
        //when
        List<GiftCertificate> certificateList = giftCertificateDao.findAll(params, pagination);
        //then
        Assertions.assertEquals(expectedList,certificateList);
    }

    @Test
    public void findAllWithDescSort(){
        //given
        List<GiftCertificate> expectedList = createCertificateList();
        Map<String, String[]> params = new HashMap<>();
        params.put("sort",new String[]{"desc[price]"});
        expectedList.sort(Comparator.comparing(GiftCertificate::getPrice));
        Collections.reverse(expectedList);
        //when
        List<GiftCertificate> certificateList = giftCertificateDao.findAll(params, pagination);
        //then
        Assertions.assertEquals(expectedList,certificateList);
    }

    @Test
    public void deleteByCorrectId(){
        //given
        GiftCertificate certificate = createNewCertificate();
        GiftCertificate newCertificate  = giftCertificateDao.add(certificate);
        //when
        giftCertificateDao.deleteById(newCertificate.getId());
        //then
        Assertions.assertEquals(Optional.empty(),giftCertificateDao.findById(newCertificate.getId()));
    }

    @Test
    public void deleteByUnCorrectId(){
        //given
        Long unCorrectId = -1l;
        //when
        boolean wasDeleted = giftCertificateDao.deleteById(unCorrectId);
        //then
        Assertions.assertFalse(wasDeleted);
    }

    private GiftCertificate createNewCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("New Certificate");
        giftCertificate.setDescription("New Description");
        giftCertificate.setDuration(123);
        giftCertificate.setPrice(BigDecimal.valueOf(123.00).setScale(2));
        giftCertificate.setCreateDate(LocalDateTime.parse("2021-01-01T10:21:34"));
        giftCertificate.setUpdateDate(LocalDateTime.parse("2021-02-20T19:25:16"));
        return giftCertificate;
    }


    private List<GiftCertificate> createCertificateList() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Martial art training");
        giftCertificate.setDescription("Ten martial arts training sessions with the best trainers");
        giftCertificate.setDuration(100);
        giftCertificate.setPrice(BigDecimal.valueOf(70.00).setScale(2));
        giftCertificate.setCreateDate(LocalDateTime.parse("2021-01-01T10:21:34"));
        giftCertificate.setUpdateDate(LocalDateTime.parse("2021-02-20T19:25:16"));

        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setName("Bungee jump");
        giftCertificate2.setDescription("Three jumps from the tower (35m)");
        giftCertificate2.setDuration(50);
        giftCertificate2.setPrice(BigDecimal.valueOf(100.00).setScale(2));
        giftCertificate2.setCreateDate(LocalDateTime.parse("2021-01-07T17:29:12"));
        giftCertificate2.setUpdateDate(LocalDateTime.parse("2021-02-27T21:37:23"));

        GiftCertificate giftCertificate3 = new GiftCertificate();
        giftCertificate3.setName("Airsoft");
        giftCertificate3.setDescription("Airsoft games in a large area for 5 hours");
        giftCertificate3.setDuration(150);
        giftCertificate3.setPrice(BigDecimal.valueOf(90.00).setScale(2));
        giftCertificate3.setCreateDate(LocalDateTime.parse("2021-01-12T07:56:14"));
        giftCertificate3.setUpdateDate(LocalDateTime.parse("2021-03-01T09:52:31"));

        GiftCertificate giftCertificate4 = new GiftCertificate();
        giftCertificate4.setName("Scuba diving");
        giftCertificate4.setDescription("Scuba diving in the black sea for 2 hours (video recording is available)");
        giftCertificate4.setDuration(70);
        giftCertificate4.setPrice(BigDecimal.valueOf(250.00).setScale(2));
        giftCertificate4.setCreateDate(LocalDateTime.parse("2021-01-19T22:13:21"));
        giftCertificate4.setUpdateDate(LocalDateTime.parse("2021-02-01T15:39:46"));

        GiftCertificate giftCertificate5 = new GiftCertificate();
        giftCertificate5.setName("Quad biking");
        giftCertificate5.setDescription("Quad biking for 3 people outside the city");
        giftCertificate5.setDuration(70);
        giftCertificate5.setPrice(BigDecimal.valueOf(80.00).setScale(2));
        giftCertificate5.setCreateDate(LocalDateTime.parse("2021-01-29T17:12:59"));
        giftCertificate5.setUpdateDate(LocalDateTime.parse("2021-03-05T23:21:28"));

        giftCertificateDao.add(giftCertificate);
        giftCertificateDao.add(giftCertificate2);
        giftCertificateDao.add(giftCertificate3);
        giftCertificateDao.add(giftCertificate4);
        giftCertificateDao.add(giftCertificate5);

        List<GiftCertificate> giftCertificatesList = new ArrayList<>();
        giftCertificatesList.add(giftCertificate);
        giftCertificatesList.add(giftCertificate2);
        giftCertificatesList.add(giftCertificate3);
        giftCertificatesList.add(giftCertificate4);
        giftCertificatesList.add(giftCertificate5);
        return giftCertificatesList;
    }
}
