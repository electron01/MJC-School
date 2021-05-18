package by.epam.esm.enity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Audited
public class Order extends BaseEntity {
    @Column(name = "all_cost",nullable = false)
    private BigDecimal allCost;

    @CreationTimestamp
    @Column(name = "creation_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "users_orders",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @NotAudited
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "certificates_orders",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"))
    @NotAudited
    private List<GiftCertificate> giftCertificateList;

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    public BigDecimal getAllCost() {
        return allCost;
    }

    public void setAllCost(BigDecimal allCost) {
        this.allCost = allCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
