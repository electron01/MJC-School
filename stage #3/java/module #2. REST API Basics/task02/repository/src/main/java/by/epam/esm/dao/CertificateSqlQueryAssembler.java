package by.epam.esm.dao;

import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.GiftCertificateRequestParam;

/**
 * interface CertificateSqlQueryAssembler
 * interface contains methods for assemble sql query got gift certificate table
 * @author Aliaksei Tkachuk
 * @version 1.0
 */

public interface CertificateSqlQueryAssembler {
    CertificateSqlQueryAssemblerImpl.SqlQuery createPartUpdateRequest (GiftCertificate certificate);
    CertificateSqlQueryAssemblerImpl.SqlQuery createQueryForFindAllCertificate (GiftCertificateRequestParam requestParam, Pagination pagination);

}
