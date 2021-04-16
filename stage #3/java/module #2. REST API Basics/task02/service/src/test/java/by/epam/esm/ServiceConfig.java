package by.epam.esm;

import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.dao.impl.GiftCertificateDaoImpl;
import by.epam.esm.dao.impl.TagDaoImpl;
import by.epam.esm.dto.mapper.*;
import by.epam.esm.service.GiftCertificateService;
import by.epam.esm.service.TagService;
import by.epam.esm.service.impl.GiftCertificateServiceImpl;
import by.epam.esm.service.impl.TagServiceImpl;
import by.epam.esm.validator.BaseValidator;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Autowired
    private CrdTagDao tagDao;
    @Autowired
    private BaseValidator validator;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private GiftCertificateMapper giftCertificateMapper;
    @Autowired
    private PaginationMapper paginationMapper;
    @Autowired
    private GiftCertificateRequestMapper giftCertificateRequestMapper;
    @Autowired
    private CrudGiftCertificateDao giftCertificateDao;
    @Autowired
    private TagService tagService;
    @Autowired
    private GiftCertificateService giftCertificateService;


    @Bean
    public CrdTagDao getTagDao(){
        return Mockito.mock(TagDaoImpl.class);
    }
    @Bean
    public TagMapper getTagMapper(){
        return new TagMapperImpl();
    }
    @Bean
    public BaseValidator getValidator(){
        return Mockito.mock(BaseValidator.class);
    }
    @Bean
    public PaginationMapper getPaginationMapper(){
        return new PaginationMapperImpl();
    }
    @Bean
    public TagService getTagService(){
        return new TagServiceImpl(tagDao,validator,tagMapper,paginationMapper);

    }
    @Bean
    public GiftCertificateService giftCertificateService(){
        return new GiftCertificateServiceImpl(giftCertificateMapper,giftCertificateDao,
                validator,paginationMapper,
                giftCertificateRequestMapper,tagService);
    }
    @Bean
    public CrudGiftCertificateDao giftCertificateDao(){
        return Mockito.mock(GiftCertificateDaoImpl.class);
    }

    @Bean
    public GiftCertificateRequestMapperImpl giftCertificateRequestMapper(){
        return new GiftCertificateRequestMapperImpl();
    }

    @Bean
    public GiftCertificateMapper giftCertificateMapper(){
        return new GiftCertificateMapperImpl();
    }




}
