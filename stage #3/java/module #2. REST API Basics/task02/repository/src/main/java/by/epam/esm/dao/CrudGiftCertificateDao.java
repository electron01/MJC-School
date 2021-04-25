package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;

import java.util.List;
import java.util.Optional;
/**
 * interface CrudGiftCertificateDao
 * interface extends BaseDao and contains methods for work with gift certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface CrudGiftCertificateDao extends BaseDao<GiftCertificate, Integer> {
    /**
     * method findAll
     * method for find certificates list in database
     * @param giftCertificateRequestParam - search parameters from user
     * @param pagination - offset and limit for find
     * @return Certificates list
     */
    List<GiftCertificate> findAll(GiftCertificateRequestParam giftCertificateRequestParam, Pagination pagination);

    /**
     * method findByName
     * method for find certificate by name
     * @param name - gift certificate name
     * @return Optional.of(certificate) or Optional.empty if certificate was not found
     */
    Optional<GiftCertificate> findByName(String name);
}
