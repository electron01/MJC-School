package by.epam.esm.controller;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * class GiftCertificateController
 * this class is a rest controller with request mapping "/app/gift-certificate"
 * this rest controller contains GET,POST,PUT,PATCH,DELETE Mapping
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@RestController
@RequestMapping("/app/gift-certificate")
public class GiftCertificateController {
    private static final String DEFAULT_VALUE_START_POSITION = "0";
    private static final String DEFAULT_VALUE_LIMIT = "6";
    private static final String START_POSITION = "startPosition";
    private static final String LIMIT = "limit";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TAG = "tag";
    private static final String SORT = "sort";

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * method getAllGiftCertificate
     * get mapping
     * method for find all gift certificate
     * @param model -  request model
     * @return Certificates list
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAllGiftCertificate(@RequestParam Map<String, Object> model) {
        PaginationDto paginationDto = createPaginationDto(model);
        DtoGiftCertificateRequestParam dtoGiftCertificateRequestParam = getGiftCertificateRequest(model);
        List<GiftCertificateDto> giftCertificates = giftCertificateService.findAll(dtoGiftCertificateRequestParam, paginationDto);
        return ResponseEntity.ok(giftCertificates);
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
        return ResponseEntity.ok(certificate);
    }

    /**
     * method createNewGiftCertificate
     * post mapping
     * method for create a new  certificate
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * method createPaginationDto
     * method for create new Pagination dto with startPosition and limit
     * @param model - request model
     * @return new Pagination dto
     */
    private PaginationDto createPaginationDto(Map<String, Object> model) {
        String startPosition = (String) Optional.ofNullable(model.get(START_POSITION))
                .orElse(DEFAULT_VALUE_START_POSITION);

        String limit = (String) Optional.ofNullable(model.get(LIMIT))
                .orElse(DEFAULT_VALUE_LIMIT);

        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setStartPosition(Integer.valueOf(startPosition));
        paginationDto.setLimit(Integer.valueOf(limit));
        return paginationDto;
    }

    /**
     * method getGiftCertificateRequest
     * method for create dto gift certificate request params
     * @param model - request model
     * @return new dtoGiftCertificateRequestParam
     */
    private DtoGiftCertificateRequestParam getGiftCertificateRequest(Map<String, Object> model) {
        DtoGiftCertificateRequestParam dtoGiftCertificateRequestParam = new DtoGiftCertificateRequestParam();
        dtoGiftCertificateRequestParam.setName((String) model.get(NAME));
        dtoGiftCertificateRequestParam.setDescription((String) model.get(DESCRIPTION));
        dtoGiftCertificateRequestParam.setTagName((String) model.get(TAG));
        dtoGiftCertificateRequestParam.setSort(((String) model.get(SORT)));
        return dtoGiftCertificateRequestParam;
    }
}
