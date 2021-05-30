package by.epam.esm.controller;


import by.epam.esm.constant.WebConstant;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.service.TagService;
import by.epam.esm.util.PaginationUtil;
import by.epam.esm.util.link.TagLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

/**
 * class TagController
 * this class is a rest controller with request mapping "/app/tags"
 * this rest controller contains GET,POST,PUT,PATCH,DELETE Mapping
 *
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@RestController
@RequestMapping("/app/tags")
public class TagController implements PaginationController {
    private TagService tagService;
    private TagLinkUtil tagLinkUtil;

    @Autowired
    public TagController(TagService tagService, TagLinkUtil tagLinkUtil) {
        this.tagService = tagService;
        this.tagLinkUtil=tagLinkUtil;
    }

    /**
     * method findAllTags
     * get mapping
     * method for find Tags list
     * @param page  - number of page
     * @param limit - limit for one page
     * @return Tags list
     */
    @GetMapping
    public ResponseEntity<PagedModel<TagDto>> findAll(WebRequest webRequest,
                                                      @RequestParam(required = false, defaultValue = WebConstant.PAGE_DEFAULT_VALUE) Integer page,
                                                      @RequestParam(required = false, defaultValue = WebConstant.LIMIT_DEFAULT_VALUE) Integer limit) {
        PaginationDto paginationDto = PaginationUtil.getPaginationDto(page, limit);
        Map<String, String[]> webRequestParameter = webRequest.getParameterMap();
        List<TagDto> tags = tagService.findAll(webRequestParameter, paginationDto);
        tagLinkUtil.addLinks(tags);
        return ResponseEntity.ok(getPagedModel(tags, paginationDto, webRequest, page));
    }

    /**
     * method findByTagId
     * get mapping
     * method for found tag by id
     * @param id - od for found
     * @return found tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findTagById(@PathVariable Long id) {
        TagDto tag = tagService.findById(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * Get the most widely used tag of a user with the highest cost of all orders
     * @return most widely used tag
     */
    @GetMapping("/most-widely-tag")
    public ResponseEntity<TagDto> findTagById() {
        TagDto tagDto = tagService.mostWidelyUsedTag();
        return ResponseEntity.ok(tagDto);

    }

    /**
     * method addNewTag
     * post mapping
     * method for create new tag
     * @param tagDto - tag dto for create
     * @return created tag dto
     */
    @PostMapping
    public ResponseEntity<TagDto> addNewTag(@RequestBody TagDto tagDto) {
        TagDto newTag = tagService.add(tagDto);
        return ResponseEntity.ok(newTag);
    }

    /**
     * method updatePartOfTag
     * patch mapping
     * method for update part info of tag
     * @param id     - tag id for update
     * @param tagDto - tag dto for update
     * @return updated tag dto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TagDto> updatePartOfTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        TagDto updatedTag = tagService.update(tagDto);
        return ResponseEntity.ok(updatedTag);
    }

    /**
     * method updatePartOfTag
     * put mapping
     * method for update tag
     * @param id     - tag id for update
     * @param tagDto - tag dto for update
     * @return updated tag dto
     */
    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        TagDto updatedTag = tagService.update(tagDto);
        return ResponseEntity.ok(updatedTag);
    }

    /**
     * method deleteTag
     * delete mapping
     * method for delete tag by id
     * @param id - tag id for delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        boolean wasDeleted = tagService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * method getPagedModel
     * @param page number of page
     * @param paginationDto - pagination dto
     * @param tagList - list for pagination
     * @param webRequest - web parameters
     * method creates pagination
     * @see by.epam.esm.dto.entity.PaginationDto
     * @return new Pagination dto
     */
    private PagedModel<TagDto> getPagedModel(List<TagDto> tagList, PaginationDto paginationDto, WebRequest webRequest, int page) {
        Map<String, String[]> params = webRequest.getParameterMap();
        int countOfElements = tagService.getCountCountOfElements(params);
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetaData(paginationDto, countOfElements);
        PagedModel<TagDto> tagsPagedModel = PagedModel.of(tagList, pageMetadata);
        tagLinkUtil.addPageLinks(tagsPagedModel, TagController.class, webRequest, paginationDto, page);
        return tagsPagedModel;
    }
}
