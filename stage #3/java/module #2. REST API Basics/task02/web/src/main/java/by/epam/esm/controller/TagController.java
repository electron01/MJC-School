package by.epam.esm.controller;


import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.TagDto;
import by.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAllTags(@RequestParam(required = false, defaultValue = DEFAULT_VALUE_START_POSITION) Integer startPosition,
                                   @RequestParam(required = false, defaultValue = DEFAULT_VALUE_LIMIT) Integer limit) {
        PaginationDto paginationDto = createPaginationDto(startPosition, limit);
        return tagService.findAll(paginationDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable Integer id) {
        return tagService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addNewTag(@RequestBody TagDto tagDto) {
        return tagService.add(tagDto);
    }


    @PatchMapping("/{id}")
    public TagDto updatePartOfTag(@PathVariable Integer id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        return tagService.update(tagDto);
    }

    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable Integer id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        return tagService.update(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Integer id) {
        tagService.delete(id);
    }


    private PaginationDto createPaginationDto(Integer startPosition, Integer limit) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setStartPosition(startPosition);
        paginationDto.setLimit(limit);
        return paginationDto;
    }
}
