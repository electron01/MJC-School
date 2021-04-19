package by.epam.esm.dto.entity.request;

import javax.validation.constraints.Pattern;

public class DtoGiftCertificateRequestParam implements DtoEntityRequestParam {
    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){1,})(?=(^[\\s\\S]{0,25}$)).*",
            message = "GiftCertificateService Name must be longer than 1 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    private String name;

    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){1,})(?=(^[\\s\\S]{0,255}$)).*",
            message = "GiftCertificateService description must be longer than 1 characters and shorter than 255 characters" +
                    " and consist only of letters, numbers and an underscore ")
    private String description;

    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){1,})(?=(^[\\s\\S]{0,25}$)).*",
            message = " Tag Name than 1 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    private String tagName;

    private String sort;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
