package by.epam.esm.service;

import by.epam.esm.dao.impl.GiftCertificateDaoImpl;
import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.dto.mapper.GiftCertificateMapperImpl;
import by.epam.esm.dto.mapper.GiftCertificateRequestMapperImpl;
import by.epam.esm.dto.mapper.PaginationMapperImpl;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.impl.GiftCertificateServiceImpl;
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
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateTest {
    private static final String CERTIFICATE_NAME = "name";
    private static final Long CORRECT_ID = 1l;
    private static final Long UN_CORRECT_ID = -1l;
    private static GiftCertificate correctGiftCertificate = new GiftCertificate();
    private static List<GiftCertificate> certificateList;
    @Mock
    private BaseValidator baseValidator;
    @Spy
    private PaginationMapperImpl paginationMapper;
    @Mock
    private TagServiceImpl tagService;
    @Spy
    private GiftCertificateMapperImpl giftCertificateMapper;
    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;
    @Spy
    private GiftCertificateRequestMapperImpl giftCertificateRequestMapper;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;


    @BeforeAll
    public static void init() {

        correctGiftCertificate.setId(CORRECT_ID);
        correctGiftCertificate.setName(CERTIFICATE_NAME);
        certificateList = List.of(correctGiftCertificate, new GiftCertificate(), new GiftCertificate(), new GiftCertificate());
    }


    @Test
    public void findAllCertificateWithCorrectDto() {
        // Given Request for find all gift certificate
        Mockito.when(giftCertificateDao.findAll(Mockito.any(GiftCertificateRequestParam.class),
                Mockito.any(Pagination.class))).thenReturn(certificateList);
        // When method findAll will start executing with default pagination params startPosition = 0 and Limit = 6
        List<GiftCertificateDto> foundList = giftCertificateService.findAll(new DtoGiftCertificateRequestParam(), new PaginationDto());
        //Then a complete  certificate list should be received with startPosition = 0 and Limit = 6
        Assertions.assertTrue(foundList.size() == certificateList.size());

    }

    @Test
    public void findAllCertificateWithUnCorrectPaginationDto() {
        // Given Request for find all gift certificate
        // When method findAll will start executing with uncCorrect pagination params
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(PaginationDto.class));
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.findAll(new DtoGiftCertificateRequestParam(), new PaginationDto()));
    }

    @Test
    public void findAllCertificateWithUnCorrectDto() {
        // Given Request for find all gift certificate
        // When method findAll will start executing with uncCorrect dto params
        Mockito.doNothing().when(baseValidator).dtoValidator(Mockito.any(PaginationDto.class));
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(DtoGiftCertificateRequestParam.class));
        // Then get an exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.findAll(new DtoGiftCertificateRequestParam(), new PaginationDto()));

    }

    @Test
    public void findCertificateByCorrectId() {
        // Given Request for findById gift certificate by id
        Mockito.when(giftCertificateDao.findById(CORRECT_ID)).thenReturn(Optional.of(correctGiftCertificate));
        // When method findById will start executing with correct id
        GiftCertificateDto foundById = giftCertificateService.findById(CORRECT_ID);
        //Then returned giftCertificate
        Assertions.assertEquals(correctGiftCertificate.getId(), foundById.getId());
        Assertions.assertEquals(correctGiftCertificate.getName(), foundById.getName());
    }

    @Test
    public void findCertificateByUnCorrectId() {
        // Given Request for findById gift certificate by id
        // When method findById will start executing with unCorrect id
        Mockito.when(giftCertificateDao.findById(UN_CORRECT_ID)).thenReturn(Optional.empty());
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.findById(UN_CORRECT_ID));
    }

    @Test
    public void addNewCorrectCertificate() {
        // Given Request for create giftCertificate
        Mockito.when(giftCertificateDao.save(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        Mockito.when(giftCertificateDao.findByName(CERTIFICATE_NAME)).thenReturn(Optional.empty());
        GiftCertificateDto newGiftCertificate = new GiftCertificateDto();
        newGiftCertificate.setName(CERTIFICATE_NAME);
        // When method add will start executing with correct certificate
        GiftCertificateDto createdCertificate = giftCertificateService.add(newGiftCertificate);
        //Then returned new giftCertificate with autoIncrement id
        Assertions.assertNotNull(createdCertificate.getId());
        Assertions.assertEquals(correctGiftCertificate.getName(), createdCertificate.getName());
    }

    @Test
    public void addNewUnCertificate() {
        // Given Request for create giftCertificate
        // When method add will start executing with unCorrect certificate
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(GiftCertificateDto.class));
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.add(new GiftCertificateDto()));
    }


    @Test
    public void addNewDuplicateCertificate() {
        // Given Request for create giftCertificate
        Mockito.when(giftCertificateDao.findByName(CERTIFICATE_NAME)).thenReturn(Optional.of(new GiftCertificate()));
        // When method add will start executing with duplicate certificate name
        GiftCertificateDto newGiftCertificate = new GiftCertificateDto();
        newGiftCertificate.setName(CERTIFICATE_NAME);
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.add(newGiftCertificate));
    }

    @Test
    public void updateCorrectCertificate() {
        // Given Request for update giftCertificate
        Mockito.when(giftCertificateDao.findByName(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.findById(Mockito.anyLong())).thenReturn(Optional.of(correctGiftCertificate));
        Mockito.when(giftCertificateDao.update(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(correctGiftCertificate.getId());
        // When method update will start executing with correct params
        GiftCertificateDto updateCertificate = giftCertificateService.update(giftCertificateDto);
        //Then we get updated certificate
        Assertions.assertEquals(correctGiftCertificate.getName(), updateCertificate.getName());

    }


    @Test
    public void updateUnCorrectCertificate() {
        // Given Request for update giftCertificate
        // When method update will start executing with unCorrect params
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(Mockito.any(GiftCertificateDto.class));
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.update(new GiftCertificateDto()));

    }

    @Test
    public void updateDuplicateCertificate() {
        // Given Request for update giftCertificate
        // When method update will start executing with duplicate certificate name
        Mockito.when(giftCertificateDao.findByName(Mockito.any())).thenReturn(Optional.of(new GiftCertificate()));
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.update(new GiftCertificateDto()));
    }

    @Test
    public void deleteCertificateByCorrectId() {
        // Given Request for delete giftCertificate by id
        // When method deleteById will start executing with correct id
        Mockito.when(giftCertificateDao.deleteById(CORRECT_ID)).thenReturn(true);
        // Then we not have does exception
        Assertions.assertDoesNotThrow(() -> giftCertificateService.delete(CORRECT_ID));

    }

    @Test
    public void deleteCertificateByUnCorrectId() {
        // Given Request for delete giftCertificate by id
        // When method deleteById will start executing with unCorrect id
        Mockito.when(giftCertificateDao.deleteById(UN_CORRECT_ID)).thenReturn(false);
        //Then returned exception (ServiceException)
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.delete(UN_CORRECT_ID));

    }

    @Test
    public void partUpdateCorrectDtoTest() {
        // Given Request for update giftCertificate
        Mockito.when(giftCertificateDao.update(Mockito.any(GiftCertificate.class))).thenReturn(correctGiftCertificate);
        // When method deleteById will start executing with correct certificate dto
        GiftCertificateDto partUpdateDto = giftCertificateService.partUpdate(new GiftCertificateDto());
        //Then we get updated certificate
        Assertions.assertEquals(correctGiftCertificate.getName(), partUpdateDto.getName());
    }
}
