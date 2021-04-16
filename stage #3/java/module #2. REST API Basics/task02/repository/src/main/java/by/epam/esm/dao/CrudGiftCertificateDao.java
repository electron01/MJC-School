package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import by.epam.esm.enity.request.GiftCertificateRequestParam;

import java.util.List;
import java.util.Optional;

    public interface CrudGiftCertificateDao extends BaseDao<GiftCertificate, Integer> {
    List<GiftCertificate> findAll(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination);
    Optional<GiftCertificate> getGiftCertificateByName(String name);
    GiftCertificate partUpdate(GiftCertificate entity);


}
