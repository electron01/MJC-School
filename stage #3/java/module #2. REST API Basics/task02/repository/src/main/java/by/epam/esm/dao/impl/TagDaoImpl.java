package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.CrdTagDao;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import by.epam.esm.query.TagQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl implements CrdTagDao {
    private TagQueryBuilder tagQueryBuilder;

    @Autowired
    public TagDaoImpl(TagQueryBuilder tagQueryBuilder) {
        this.tagQueryBuilder = tagQueryBuilder;
    }

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Tag> findByName(String tagName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        query.where(criteriaBuilder.equal(from.get(RepoConstant.ENTITY_NAME), tagName));
        TypedQuery<Tag> tagTypedQuery = entityManager.createQuery(query);
        return tagTypedQuery.getResultList()
                .stream()
                .findAny();
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        Query nativeQuery = entityManager.createNativeQuery(RepoConstant.GET_LIST_TAG_BY_CERTIFICATE_ID, Tag.class);
        nativeQuery.setParameter("gift_certificate_id", id);
        List resultList = nativeQuery.getResultList();
        return resultList;

    }

    @Override
    public List<Tag> findAll(Map<String, String[]> params, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = tagQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getStartPosition());
        query.setMaxResults(pagination.getLimit());
        return query.getResultList()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        TypedQuery<Tag> tagTypedQuery = entityManager.createQuery(RepoConstant.GET_TAG_BY_ID_REQUEST, Tag.class)
                .setParameter(RepoConstant.ENTITY_ID, id);
        Optional<Tag> tag = tagTypedQuery.getResultStream().findAny();
        tag.ifPresent(entityManager::detach);
        return tag;
    }

    @Override
    public Tag add(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update");
    }


    @Override
    public boolean deleteById(Long id) {
        Tag tag = entityManager.getReference(Tag.class, id);
        Query query = entityManager.createNativeQuery(RepoConstant.DELETE_TAG_BY_ID_REQUEST)
                .setParameter(RepoConstant.ENTITY_ID, tag.getId());
        return query.executeUpdate() == 1;
    }

    @Override
    public Integer getCountOfElements(Map<String, String[]> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = tagQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<Tag> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .size();
    }
}

