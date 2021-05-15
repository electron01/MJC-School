package by.epam.esm.dto.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto extends EntityDto {
    private BigDecimal allCost;
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
}
