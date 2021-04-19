package by.epam.esm.dto.entity;

import javax.validation.constraints.Pattern;

/**
 * class Tag
 * @see by.epam.esm.enity.Tag
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class TagDto extends EntityDto {
    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){3,})(?=(^[\\s\\S]{0,25}$)).*",
            message = "GiftCertificateService Name must be longer than 3 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
