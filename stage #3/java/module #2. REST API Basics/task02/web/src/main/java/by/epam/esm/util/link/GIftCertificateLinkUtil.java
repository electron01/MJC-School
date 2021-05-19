package by.epam.esm.util.link;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.controller.GiftCertificateController;
import by.epam.esm.dto.entity.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class GIftCertificateLinkUtil
 * class extends AbstractLinkUtil and contains methods for create links for certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class GIftCertificateLinkUtil extends AbstractLinkUtil<GiftCertificateDto> {
    private TagLinkUtil tagLinkUtil;

    @Autowired
    public GIftCertificateLinkUtil(TagLinkUtil tagLinkUtil) {
        this.tagLinkUtil = tagLinkUtil;
    }

    @Override
    public void addLinks(List<GiftCertificateDto> list) {
        for (GiftCertificateDto certificate : list) {
            getCertificateLinks(certificate);
        }
        list.stream()
                .forEach(certificate -> tagLinkUtil.addLinks(certificate.getTags()));
    }

    private void getCertificateLinks(GiftCertificateDto certificateDto) {
        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .findById(certificateDto.getId()))
                .withSelfRel());

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .deleteById(certificateDto.getId()))
                .withRel(WebConstant.DELETE)
                .withName(WebConstant.DELETE_METHOD));

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .updateGiftCertificate(certificateDto, certificateDto.getId()))
                .withRel(WebConstant.UPDATE)
                .withName(WebConstant.UPDATE_METHOD));

        certificateDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .updatePartOfCertificate(certificateDto.getId(), certificateDto))
                .withRel(WebConstant.PART_UPDATE)
                .withName(WebConstant.PART_UPDATE_METHOD));
    }
}
