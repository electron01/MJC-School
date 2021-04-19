package by.epam.esm.controller;

import by.epam.esm.dto.entity.GiftCertificateDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.request.DtoGiftCertificateRequestParam;
import by.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllGiftCertificate(@RequestParam Map<String, Object> model) {
        PaginationDto paginationDto = createPaginationDto(model);
        DtoGiftCertificateRequestParam dtoGiftCertificateRequestParam = getGiftCertificateRequest(model);
        return giftCertificateService.findAll(dtoGiftCertificateRequestParam, paginationDto);
    }

    /**
     * method findById
     * get mapping
     * method find certificate by id
     * @param id - id for found
     * @return Gift Certificate dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findById(@PathVariable Integer id) {
        return giftCertificateService.findById(id);
    }

    /**
     * method createNewGiftCertificate
     * post mapping
     * method for create a new  certificate
     * @param giftCertificateDto - dto for create certificate
     * @return - new Gift Certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto createNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    /**
     * method updateGiftCertificate
     * put mapping
     * @param giftCertificateDto - dto for update
     * @param id - id for found
     * @return updated Certificate dto
     */
    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto, @PathVariable Integer id) {
        giftCertificateDto.setId(id);
        return giftCertificateService.update(giftCertificateDto);
    }

    /**
     * method updatePartOfCertificate
     * patch mapping
     * @param giftCertificateDto - dto for part update
     * @param id - id for found
     * @return updated Certificate dto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updatePartOfCertificate(@PathVariable Integer id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return giftCertificateService.partUpdate(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        giftCertificateService.delete(id);
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
