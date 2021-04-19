package by.epam.esm.dao;

import by.epam.esm.enity.Entity;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.request.EntityRequestParam;

/**
 * interface BaseSqlQueryAssembler
 * base interface for assemble sql query
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface BaseSqlQueryAssembler<E extends Entity,T extends EntityRequestParam> {
    CertificateSqlQueryAssemblerImpl.SqlQuery createPartUpdateRequest(E e);

    CertificateSqlQueryAssemblerImpl.SqlQuery createQueryForFindAllCertificate(T requestParam, Pagination pagination);
}
