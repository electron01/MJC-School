package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.request.GiftCertificateRequestParam;
/**
 * interface CertificateSqlQueryAssembler
 * interface extends BaseSqlQueryAssembler with params GiftCertificate and GiftCertificateRequestParam
 * for work with GiftCertificate sql query
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface CertificateSqlQueryAssembler extends BaseSqlQueryAssembler<GiftCertificate, GiftCertificateRequestParam> {
}
