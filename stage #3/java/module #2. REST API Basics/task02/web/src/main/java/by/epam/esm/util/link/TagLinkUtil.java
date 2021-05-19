package by.epam.esm.util.link;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.TagController;
import by.epam.esm.dto.entity.TagDto;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class TagLinkUtil
 * class extends AbstractLinkUtil and contains methods for create links for tag dto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class TagLinkUtil extends AbstractLinkUtil<TagDto> {

    @Override
    public void addLinks(List<TagDto> list) {
        for (TagDto tagDto : list) {
            getTagLinks(tagDto);
        }
    }
    private void getTagLinks(TagDto tagDto) {
        tagDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .findTagById(tagDto.getId()))
                .withSelfRel());

        tagDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .deleteTag(tagDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));
    }
}
