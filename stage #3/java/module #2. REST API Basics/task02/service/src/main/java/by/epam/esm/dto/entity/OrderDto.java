package by.epam.esm.dto.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * class OrderDto
 * base abstract entity data transfer object class
 * @see EntityDto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class OrderDto extends EntityDto {
    private BigDecimal allCost;

    private LocalDateTime creationDate;
    private UserDto user;
    private List<GiftCertificateDto> giftCertificateList;

    public List<GiftCertificateDto> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificateDto> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    public BigDecimal getAllCost() {
        return allCost;
    }

    public void setAllCost(BigDecimal allCost) {
        this.allCost = allCost;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
