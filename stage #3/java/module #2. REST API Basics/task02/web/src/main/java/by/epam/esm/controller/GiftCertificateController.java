package by.epam.esm.controller;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.service.GiftCertificateService;
import by.epam.esm.util.PaginationUtil;
import by.epam.esm.util.link.GIftCertificateLinkUtil;
import by.epam.esm.util.link.TagLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

/**
 * class GiftCertificateController
 * this class is a rest controller with request mapping "/app/gift-certificate"
 * this rest controller contains GET,POST,PUT,PATCH,DELETE Mapping
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@RestController
@RequestMapping("/app/gift-certificates")
public class GiftCertificateController implements PaginationController {
    private GiftCertificateService giftCertificateService;
    private GIftCertificateLinkUtil certificateLinkUtil;
    private TagLinkUtil tagLinkUtil;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,GIftCertificateLinkUtil certificateLinkUtil,TagLinkUtil tagLinkUtil) {
        this.giftCertificateService = giftCertificateService;
        this.certificateLinkUtil=certificateLinkUtil;
        this.tagLinkUtil=tagLinkUtil;
    }

    /**
     * method getAllGiftCertificate
     * get mapping
     * method for find all gift certificate
     * @param webRequest -  request model
     * @return Certificates list
     */
    @GetMapping
    public ResponseEntity<PagedModel<GiftCertificateDto>> findAll(WebRequest webRequest,
                                                                  @RequestParam(required = false, defaultValue = WebConstant.PAGE_DEFAULT_VALUE) Integer page,
                                                                  @RequestParam(required = false, defaultValue = WebConstant.LIMIT_DEFAULT_VALUE) Integer limit
    ) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        PaginationDto paginationDto = PaginationUtil.getPaginationDto(page, limit);
        List<GiftCertificateDto> giftCertificates = giftCertificateService.findAll(parameterMap, paginationDto);
        certificateLinkUtil.addLinks(giftCertificates);
        return ResponseEntity.ok(getPagedModel(giftCertificates, paginationDto, webRequest, page));
    }

    /**
     * method findById
     * get mapping
     * method find certificate by id
     * @param id - id for found
     * @return Gift Certificate dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.findById(id);
        tagLinkUtil.addLinks(certificate.getTags());
        return ResponseEntity.ok(certificate);
    }

    /**
     * method createNewGiftCertificate
     * post mapping
     * method for create a new  certificate
     *
     * @param giftCertificateDto - dto for create certificate
     * @return - new Gift Certificate dto
     */
    @PostMapping
    public ResponseEntity<GiftCertificateDto> createNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto newCertificate = giftCertificateService.add(giftCertificateDto);
        return ResponseEntity.ok(newCertificate);
    }

    /**
     * method updateGiftCertificate
     * put mapping
     *
     * @param giftCertificateDto - dto for update
     * @param id                 - id for found
     * @return updated Certificate dto
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto, @PathVariable Long id) {
        giftCertificateDto.setId(id);
        GiftCertificateDto updatedCertificate = giftCertificateService.update(giftCertificateDto);
        return ResponseEntity.ok(updatedCertificate);
    }

    /**
     * method updatePartOfCertificate
     * patch mapping
     * @param giftCertificateDto - dto for part update
     * @param id                 - id for found
     * @return updated Certificate dto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updatePartOfCertificate(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto updatedCertificate = giftCertificateService.partUpdate(giftCertificateDto);
        return ResponseEntity.ok(updatedCertificate);
    }


    /**
     * method delete by id
     * delete mapping
     * @param id  for delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean wasDeleted = giftCertificateService.delete(id);
        if(wasDeleted){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * method getPagedModel
     * @param page number of page
     * @param paginationDto - pagination dto
     * @param certificateList - list for pagination
     * @param webRequest - web parameters
     * method creates pagination
     * @see by.epam.esm.dto.entity.PaginationDto
     * @return new Pagination dto
     */
    private PagedModel<GiftCertificateDto> getPagedModel(List<GiftCertificateDto> certificateList, PaginationDto paginationDto, WebRequest webRequest, int page) {
        Map<String, String[]> params = webRequest.getParameterMap();
        int countOfElements = giftCertificateService.getCountCountOfElements(params);
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetaData(paginationDto, countOfElements);
        PagedModel<GiftCertificateDto> certificates = PagedModel.of(certificateList, pageMetadata);
        certificateLinkUtil.addPageLinks(certificates, GiftCertificateController.class, webRequest, paginationDto, page);
        return certificates;
    }
}
