package by.epam.esm.dto.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class TagDto extends EntityDto {
    @Pattern(regexp = "^[A-Za-z0-9\\s]{3,25}$",
            message = "GiftCertificateService Name must be longer than 3 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    //@NotBlank(message = "Tag Name can't be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
