package by.epam.esm.dto.entity;

/**
 * class OrderInfoDto
 * base abstract entity data
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class OrderInfoDto {
    private long giftCertificateId;

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    @Override
    public String toString() {
        return "OrderInfoDto{" +
                "giftCertificateId=" + giftCertificateId +
                '}';
    }
}
