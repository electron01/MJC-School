package by.epam.esm.service;

import by.epam.esm.ServiceConfig;
import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
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
public class GiftCertificateTest {
    private static final String CERTIFICATE_NAME = "name";
    private static final Integer CORRECT_ID = 1;
    private static final Integer UN_CORRECT_ID = -1;
    private static GiftCertificate correctGiftCertificate = new GiftCertificate();
    private static List<GiftCertificate> certificateList;
    @Autowired
    private GiftCertificateService giftCertificateService;
    @Autowired
    private CrudGiftCertificateDao giftCertificateDao;
    @Autowired
    private BaseValidator baseValidator;

    @BeforeAll
    public static void init() {
        correctGiftCertificate.setId(CORRECT_ID);
        correctGiftCertificate.setName(CERTIFICATE_NAME);
        certificateList = List.of(correctGiftCertificate, new GiftCertificate(), new GiftCertificate(), new GiftCertificate());
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(giftCertificateDao, baseValidator);
    }

    @Test
    public void getAllCertificateWithCorrectDto() {
        Mockito.when(giftCertificateDao.findAll(Mockito.any(GiftCertificateRequestParam.class),
                Mockito.any(Pagination.class))).thenReturn(certificateList);
        List<GiftCertificateDto> foundList = giftCertificateService.getAll(new DtoGiftCertificateRequestParam(), new PaginationDto());
        Assertions.assertTrue(foundList.size() == certificateList.size());

    }

    @Test
    public void getAllCertificateWithUnCorrectPaginationDto() {
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(PaginationDto.class));
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.getAll(new DtoGiftCertificateRequestParam(), new PaginationDto()));
    }

    @Test
    public void getAllCertificateWithUnCorrectDto() {
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(GiftCertificateRequestParam.class));
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.getAll(new DtoGiftCertificateRequestParam(), new PaginationDto()));

    }

    @Test
    public void getCertificateByCorrectId() {
        Mockito.when(giftCertificateDao.findById(CORRECT_ID)).thenReturn(Optional.of(correctGiftCertificate));
        GiftCertificateDto foundById = giftCertificateService.getById(CORRECT_ID);
        Assertions.assertEquals(correctGiftCertificate.getId(), foundById.getId());
        Assertions.assertEquals(correctGiftCertificate.getName(), foundById.getName());
    }

    @Test
    public void getCertificateByUnCorrectId() {
        Mockito.when(giftCertificateDao.findById(UN_CORRECT_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.getById(UN_CORRECT_ID));
    }

    @Test
    public void addNewCorrectCertificate() {
        Mockito.when(giftCertificateDao.save(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        Mockito.when(giftCertificateDao.getGiftCertificateByName(CERTIFICATE_NAME)).thenReturn(Optional.empty());
        GiftCertificateDto newGiftCertificate = new GiftCertificateDto();
        newGiftCertificate.setName(CERTIFICATE_NAME);
        GiftCertificateDto createdCertificate = giftCertificateService.add(newGiftCertificate);
        Assertions.assertNotNull(createdCertificate.getId());
        Assertions.assertEquals(correctGiftCertificate.getName(), createdCertificate.getName());
    }

    @Test
    public void addNewUnCertificate() {
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(GiftCertificateDto.class));
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.add(new GiftCertificateDto()));
    }


    @Test
    public void addNewDuplicateCertificate() {
        Mockito.when(giftCertificateDao.save(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        Mockito.when(giftCertificateDao.getGiftCertificateByName(CERTIFICATE_NAME)).thenReturn(Optional.of(new GiftCertificate()));
        GiftCertificateDto newGiftCertificate = new GiftCertificateDto();
        newGiftCertificate.setName(CERTIFICATE_NAME);
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.add(newGiftCertificate));
    }

    @Test
    public void updateCorrectCertificate() {
        Mockito.when(giftCertificateDao.update(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        Mockito.when(giftCertificateDao.getGiftCertificateByName(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.findById(Mockito.anyInt())).thenReturn(Optional.of(correctGiftCertificate));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        GiftCertificateDto updateCertificate = giftCertificateService.update(giftCertificateDto);
        Assertions.assertEquals(correctGiftCertificate.getName(), updateCertificate.getName());

    }


    @Test
    public void updateUnCorrectCertificate() {
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(GiftCertificateDto.class));
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.update(new GiftCertificateDto()));

    }

    @Test
    public void updateDuplicateCertificate() {
        Mockito.when(giftCertificateDao.getGiftCertificateByName(Mockito.anyString())).thenReturn(Optional.of(new GiftCertificate()));
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.update(new GiftCertificateDto()));
    }

    @Test
    public void deleteCertificateByCorrectId() {
        Mockito.when(giftCertificateDao.deleteById(CORRECT_ID)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> giftCertificateService.delete(CORRECT_ID));

    }

    @Test
    public void deleteCertificateByUnCorrectId() {
        Mockito.when(giftCertificateDao.deleteById(UN_CORRECT_ID)).thenReturn(false);
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.delete(UN_CORRECT_ID));

    }

    @Test
    public void partUpdateCorrectDtoTest() {
        Mockito.when(giftCertificateDao.partUpdate(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        GiftCertificateDto partUpdateDto = giftCertificateService.partUpdate(new GiftCertificateDto());
        Assertions.assertEquals(correctGiftCertificate.getName(), partUpdateDto.getName());
    }


}
