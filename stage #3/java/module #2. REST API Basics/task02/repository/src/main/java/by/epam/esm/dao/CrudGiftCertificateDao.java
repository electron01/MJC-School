package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;

import java.util.Optional;
/**
 * interface CrudGiftCertificateDao
 * interface extends BaseDao and contains methods for work with gift certificate
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface CrudGiftCertificateDao extends BaseDao<GiftCertificate, Long> {
    /**
     * method findByName
     * method for find certificate by name
     * @param name - gift certificate name
     * @return Optional.of(certificate) or Optional.empty if certificate was not found
     */
    Optional<GiftCertificate> findByName(String name);
}
