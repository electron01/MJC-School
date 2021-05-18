package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.CrudGiftCertificateDao;
import by.epam.esm.enity.GiftCertificate;
import by.epam.esm.enity.Pagination;
import by.epam.esm.query.GiftCertificateQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements CrudGiftCertificateDao {
    private GiftCertificateQueryBuilder giftCertificateQueryBuilder;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateQueryBuilder giftCertificateQueryBuilder) {
        this.giftCertificateQueryBuilder = giftCertificateQueryBuilder;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findAll(Map<String, String[]> params, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = giftCertificateQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getStartPosition());
        query.setMaxResults(pagination.getLimit());
       return query.getResultList()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> from = query.from(GiftCertificate.class);
        query.where(criteriaBuilder.equal(from.get(RepoConstant.ENTITY_NAME), name));
        TypedQuery<GiftCertificate> tagTypedQuery = entityManager.createQuery(query);
        return tagTypedQuery.getResultList()
                .stream()
                .findAny();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        GiftCertificate reference = entityManager.getReference(GiftCertificate.class, entity.getId());
        entity.setCreateDate(reference.getCreateDate());
        entity.setUpdateDate(LocalDateTime.now());
        GiftCertificate giftCertificate = entityManager.merge(entity);
        giftCertificate.setTags(entity.getTags());
        entityManager.flush();
        entityManager.detach(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        TypedQuery<GiftCertificate> tagTypedQuery = entityManager.createQuery(RepoConstant.GET_CERTIFICATE_BY_ID, GiftCertificate.class)
                .setParameter(RepoConstant.ENTITY_ID, id);
        Optional<GiftCertificate> certificate = tagTypedQuery.getResultStream().findAny();
//        certificate.ifPresent(entityManager::detach);//todo : ??? 2 select without join
        return certificate;
    }

    @Override
    public GiftCertificate add(GiftCertificate entity) {
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.detach(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        GiftCertificate giftCertificate = entityManager.getReference(GiftCertificate.class, id);
        Query query = entityManager.createNativeQuery(RepoConstant.DELETE_CERTIFICATE_REQUEST)
                .setParameter(RepoConstant.ENTITY_ID, giftCertificate.getId());
        return query.executeUpdate() == 1;
    }

    @Override
    public Integer getCountOfElements(Map<String, String[]> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = giftCertificateQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .collect(Collectors.toList())
                .size();
    }

}
