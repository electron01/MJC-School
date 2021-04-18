package by.epam.esm.dao;


import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(RepositoryConfig.class)
public class TestGiftCertificateDao {
    @Autowired
    private CrudGiftCertificateDao giftCertificateDao;
    private static Pagination pagination = new Pagination();
    private static List<GiftCertificate> certificateList;

    @BeforeAll
    public static void initBeforeTest() {
        pagination.setStartPosition(0);
        pagination.setLimit(6);
        initCertificateList();

    }

    @Test
    public void findAllCertificateTest() {
        // Given Request for find all gift certificate
        GiftCertificateRequestParam giftCertificateRequestParam = new GiftCertificateRequestParam();
        // When method findAll will start executing with default pagination params startPosition = 0 and Limit = 6
        List<GiftCertificate> foundCertificateList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination);
        //Then a complete  certificate list should be received with startPosition = 0 and Limit = 6
        Assertions.assertEquals(certificateList, foundCertificateList);
    }

    @Test
    public void findAllCertificateByNameLikeTest() {
        // Given Request for find all gift certificate
        GiftCertificateRequestParam giftCertificateRequestParam = new GiftCertificateRequestParam();
        // When method findAll will start executing with params name like Scuba
        giftCertificateRequestParam.setName("Scuba");
        List<GiftCertificate> foundCertificateList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination);
        // Then a certificate list with a name like Scuba
        Assertions.assertEquals(List.of(certificateList.get(3)), foundCertificateList);
    }

    @Test
    public void findAllCertificateByDescriptionLikeTest() {
        // Given Request for find all gift certificate
        GiftCertificateRequestParam giftCertificateRequestParam = new GiftCertificateRequestParam();
        // When method findAll will start executing with params description like house
        giftCertificateRequestParam.setDescription("hours");
        List<GiftCertificate> foundCertificateList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination);
        // Then a certificate list with a name description house
        Assertions.assertEquals(List.of(certificateList.get(2), certificateList.get(3)), foundCertificateList);
    }

    @Test
    public void findAllCertificateSortByDescName() {
        // Given Request for find all gift certificate
        GiftCertificateRequestParam giftCertificateRequestParam = new GiftCertificateRequestParam();
        // When method findAll will start executing with sort by desc name
        giftCertificateRequestParam.setSort("desc(name)");
        List<GiftCertificate> foundCertificateList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination);
        // Then a certificate list with sort by desc name
        List<GiftCertificate> expectedList =  new ArrayList<>();
        expectedList.addAll(certificateList);
        expectedList.sort(Comparator.comparing(GiftCertificate::getName).reversed());
        Assertions.assertEquals(expectedList, foundCertificateList);
    }

    @Test
    public void findAllCertificateSortByDescDurationAndAscName() {
        // Given Request for find all gift certificate
        GiftCertificateRequestParam giftCertificateRequestParam = new GiftCertificateRequestParam();
        // When method findAll will start executing with sort by desc duration and asc name
        giftCertificateRequestParam.setSort("desc(duration),asc(name)");
        List<GiftCertificate> foundCertificateList = giftCertificateDao.findAll(giftCertificateRequestParam, pagination);
        // Then a certificate list with sort by desc duration and asc name
        List<GiftCertificate> expectedList = new ArrayList<>();
        expectedList.addAll(certificateList);
        expectedList.sort(Comparator.comparingInt(GiftCertificate::getDuration).reversed()
                .thenComparing(GiftCertificate::getName));
        Assertions.assertEquals(expectedList, foundCertificateList);
    }

    @Test
    public void addNewCertificateTest() {
        // Given Request for add new gift certificate
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("New Certificate");
        giftCertificate.setDescription("New Description");
        giftCertificate.setDuration(123);
        giftCertificate.setPrice(BigDecimal.valueOf(123.00).setScale(2));
        giftCertificate.setCreateDate(LocalDateTime.parse("2021-01-01T10:21:34"));
        giftCertificate.setUpdateDate(LocalDateTime.parse("2021-02-20T19:25:16"));
        // When method save will start executing with correct certificate
        GiftCertificate savedCertificate = giftCertificateDao.save(giftCertificate);
        // Then new certificate must be saved  with an autoIncrement identifier
        Assertions.assertEquals(savedCertificate.getName(), giftCertificate.getName());
        Assertions.assertNotNull(savedCertificate.getId());
    }

    @Test
    public void deleteCertificateByCorrectIdTest() {
        // Given Request for delete gift certificate by id
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("New Certificate");
        giftCertificate.setDescription("New Description");
        giftCertificate.setDuration(123);
        giftCertificate.setPrice(BigDecimal.valueOf(123.00).setScale(2));
        giftCertificate.setCreateDate(LocalDateTime.parse("2021-01-01T10:21:34"));
        giftCertificate.setUpdateDate(LocalDateTime.parse("2021-02-20T19:25:16"));
        GiftCertificate savedCertificate = giftCertificateDao.save(giftCertificate);
        // When method deleteById will start executing with correct id
        boolean deleteById = giftCertificateDao.deleteById(savedCertificate.getId());
        //Then certificate must be removed and method delete must be return true
        Assertions.assertTrue(deleteById);
    }

    @Test
    public void deleteCertificateByUnCorrectIdTest() {
        // Given Request for delete gift certificate by id
        // When method deleteById will start executing with unCorrect id
        boolean deleteById = giftCertificateDao.deleteById(-1);
        //Then certificate must be removed and method delete must be return false
        Assertions.assertFalse(deleteById);
    }

    @Test
    public void findCertificateByCorrectId() {
        // Given Request for find gift certificate by id
        Integer id = 1;
        // When method find will start executing with correct id
        Optional<GiftCertificate> findById = giftCertificateDao.findById(id);
        //Then returned Optional should be contains certificate
        Assertions.assertEquals(Optional.of(certificateList.get(id)), findById);

    }

    @Test
    public void findCertificateByUnCorrectId() {
        // Given Request for find gift certificate by id
        Integer id = -1;
        // When method find will start executing with unCorrect id
        Optional<GiftCertificate> findById = giftCertificateDao.findById(id);
        //Then returned Optional should be empty
        Assertions.assertEquals(Optional.empty(), findById);

    }

    @Test
   public void findAllWithPagination(){
        // Given Request for find all gift certificate
        Pagination pagination = new Pagination();
        pagination.setStartPosition(2);
        pagination.setLimit(2);
        // When method findAll will start executing with pagination params (startPosition = 2 and Limit = 2)
        List<GiftCertificate> foundList = giftCertificateDao.findAll(new GiftCertificateRequestParam(),pagination);
        // Then a certificate list with startPosition = 2 and Limit = 2
        Assertions.assertEquals(certificateList.subList(pagination.getStartPosition(),pagination.getStartPosition()+pagination.getLimit()),
                foundList);
    }


    private static void initCertificateList() {
        Integer id = 0;
        certificateList = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id++);
        giftCertificate.setName("Martial art training");
        giftCertificate.setDescription("Ten martial arts training sessions with the best trainers");
        giftCertificate.setDuration(100);
        giftCertificate.setPrice(BigDecimal.valueOf(70.00).setScale(2));
        giftCertificate.setCreateDate(LocalDateTime.parse("2021-01-01T10:21:34"));
        giftCertificate.setUpdateDate(LocalDateTime.parse("2021-02-20T19:25:16"));

        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(id++);
        giftCertificate2.setName("Bungee jump");
        giftCertificate2.setDescription("Three jumps from the tower (35m)");
        giftCertificate2.setDuration(50);
        giftCertificate2.setPrice(BigDecimal.valueOf(100.00).setScale(2));
        giftCertificate2.setCreateDate(LocalDateTime.parse("2021-01-07T17:29:12"));
        giftCertificate2.setUpdateDate(LocalDateTime.parse("2021-02-27T21:37:23"));

        GiftCertificate giftCertificate3 = new GiftCertificate();
        giftCertificate3.setId(id++);
        giftCertificate3.setName("Airsoft");
        giftCertificate3.setDescription("Airsoft games in a large area for 5 hours");
        giftCertificate3.setDuration(150);
        giftCertificate3.setPrice(BigDecimal.valueOf(90.00).setScale(2));
        giftCertificate3.setCreateDate(LocalDateTime.parse("2021-01-12T07:56:14"));
        giftCertificate3.setUpdateDate(LocalDateTime.parse("2021-03-01T09:52:31"));

        GiftCertificate giftCertificate4 = new GiftCertificate();
        giftCertificate4.setId(id++);
        giftCertificate4.setName("Scuba diving");
        giftCertificate4.setDescription("Scuba diving in the black sea for 2 hours (video recording is available)");
        giftCertificate4.setDuration(70);
        giftCertificate4.setPrice(BigDecimal.valueOf(250.00).setScale(2));
        giftCertificate4.setCreateDate(LocalDateTime.parse("2021-01-19T22:13:21"));
        giftCertificate4.setUpdateDate(LocalDateTime.parse("2021-02-01T15:39:46"));

        GiftCertificate giftCertificate5 = new GiftCertificate();
        giftCertificate5.setId(id++);
        giftCertificate5.setName("Quad biking");
        giftCertificate5.setDescription("Quad biking for 3 people outside the city");
        giftCertificate5.setDuration(70);
        giftCertificate5.setPrice(BigDecimal.valueOf(80.00).setScale(2));
        giftCertificate5.setCreateDate(LocalDateTime.parse("2021-01-29T17:12:59"));
        giftCertificate5.setUpdateDate(LocalDateTime.parse("2021-03-05T23:21:28"));

        certificateList.add(giftCertificate);
        certificateList.add(giftCertificate2);
        certificateList.add(giftCertificate3);
        certificateList.add(giftCertificate4);
        certificateList.add(giftCertificate5);

    }


}
