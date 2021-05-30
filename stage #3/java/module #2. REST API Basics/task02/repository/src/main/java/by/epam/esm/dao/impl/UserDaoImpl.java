package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.UserDao;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.User;
import by.epam.esm.query.UserQueryBuilder;
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
public class UserDaoImpl implements UserDao {
    private UserQueryBuilder userQueryBuilder;

    @Autowired
    public UserDaoImpl(UserQueryBuilder userQueryBuilder){
        this.userQueryBuilder=userQueryBuilder;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(Map<String, String[]> params, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = userQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pagination.getStartPosition());
        query.setMaxResults(pagination.getLimit());
        return query.getResultList()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        TypedQuery<User> userTypedQuery = entityManager.createQuery(RepoConstant.GET_USER_BY_ID_REQUEST, User.class)
                .setParameter(RepoConstant.ENTITY_ID, id);
        Optional<User> user = userTypedQuery.getResultStream().findAny();
        user.ifPresent(entityManager::detach);
        return user;
    }

    @Override
    public User add(User user) {
        entityManager.persist(user);
        entityManager.flush();
        entityManager.detach(user);
        return user;
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException(RepoConstant.USER_UPDATE_EXCEPTION);
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = entityManager.createNativeQuery(RepoConstant.DELETE_USER_REQUEST)
                .setParameter(RepoConstant.ENTITY_ID, id);
        return query.executeUpdate()==1;
    }

    @Override
    public Integer getCountOfElements(Map<String, String[]> params) {
         CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = userQueryBuilder.createCriteriaQuery(params, criteriaBuilder);
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .collect(Collectors.toList())
                .size();
    }

    @Override
    public Optional<User> isUserLoginExist(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(criteriaBuilder.equal(from.get(RepoConstant.USER_LOGIN), login));
        TypedQuery<User> tagTypedQuery = entityManager.createQuery(query);
        return tagTypedQuery.getResultList()
                .stream()
                .findAny();
    }
}
