package by.epam.esm.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * class GiftCertificateDto
 * @see by.epam.esm.enity.GiftCertificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class GiftCertificateDto extends EntityDto {
    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){3,})(?=(^[\\s\\S]{0,25}$)).*",
            message = "GiftCertificateService Name must be longer than 3 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    @NotNull(message = "Name can't be null")
    private String name;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000000.00")
    @NotNull(message = "Price can't be null")
    private BigDecimal price;

    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){5,})(?=(^[\\s\\S]{0,255}$)).*",
            message = "GiftCertificateService description must be longer than 5 characters and shorter than 255 characters" +
                    " and consist only of letters, numbers and an underscore ")
    @NotNull(message = "Description can't be null")
    private String description;

    @Min(value = 1)
    @NotNull(message = "Duration can't be null")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateDate;

    private List<TagDto> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
