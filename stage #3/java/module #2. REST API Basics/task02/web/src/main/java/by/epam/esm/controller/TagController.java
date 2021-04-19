package by.epam.esm.controller;


import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * class TagController
 * this class is a rest controller with request mapping "/app/tags"
 * this rest controller contains GET,POST,PUT,PATCH,DELETE Mapping
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@RestController
@RequestMapping("/app/tags")
public class TagController {
    private static final String DEFAULT_VALUE_START_POSITION = "0";
    private static final String DEFAULT_VALUE_LIMIT = "6";
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * method findAllTags
     * get mapping
     * method for find Tags list
     * @param startPosition - offset for found
     * @param limit - limit for found
     * @return Tags list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findAllTags(@RequestParam(required = false, defaultValue = DEFAULT_VALUE_START_POSITION) Integer startPosition,
                                   @RequestParam(required = false, defaultValue = DEFAULT_VALUE_LIMIT) Integer limit) {
        PaginationDto paginationDto = createPaginationDto(startPosition, limit);
        return tagService.findAll(paginationDto);
    }

    /**
     * method findByTagId
     * get mapping
     * method for found tag by id
     * @param id - od for found
     * @return found tag
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagById(@PathVariable Integer id) {
        return tagService.findById(id);
    }

    /**
     * method addNewTag
     * post mapping
     * method for create new tag
     * @param tagDto - tag dto for create
     * @return created tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDto addNewTag(@RequestBody TagDto tagDto) {
        return tagService.add(tagDto);
    }

    /**
     * method updatePartOfTag
     * patch mapping
     * method for update part info of tag
     * @param id - tag id for update
     * @param tagDto - tag dto for update
     * @return updated tag dto
     */
    @PatchMapping("/{id}")
    public TagDto updatePartOfTag(@PathVariable Integer id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        return tagService.update(tagDto);
    }
    /**
     * method updatePartOfTag
     * put mapping
     * method for update tag
     * @param id - tag id for update
     * @param tagDto - tag dto for update
     * @return updated tag dto
     */
    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable Integer id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        return tagService.update(tagDto);
    }

    /**
     * method deleteTag
     * delete mapping
     * method for delete tag by id
     * @param id - tag id for delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Integer id) {
        tagService.delete(id);
    }

    /**
     * method createPaginationDto
     * method create new pagination dto with param
     * @param startPosition - offset for found
     * @param limit - limit for found
     * @return new Pagination dto
     */
    private PaginationDto createPaginationDto(Integer startPosition, Integer limit) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setStartPosition(startPosition);
        paginationDto.setLimit(limit);
        return paginationDto;
    }
}
