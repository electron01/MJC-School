package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.OrderDao;
import by.epam.esm.enity.Order;
import by.epam.esm.enity.Pagination;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Order> findById(Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public Order add(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        entityManager.detach(order);
        return order;
    }


    @Override
    public boolean deleteById(Long id) {
        Query query = entityManager.createQuery(RepoConstant.DELETE_ORDER_BY_ID_REQUEST)
                .setParameter(RepoConstant.ENTITY_ID, id);
        return query.executeUpdate() == 1;
    }

    @Override
    public List<Order> findByUserId(Long userId, Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.where(criteriaBuilder.equal(root.join(RepoConstant.USER_ENTITY), userId));
        TypedQuery<Order> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pagination.getStartPosition());
        typedQuery.setMaxResults(pagination.getLimit());
        return typedQuery.getResultList();
    }

    @Override
    public Integer getCountCountOfElements(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.where(criteriaBuilder.equal(root.join(RepoConstant.USER_ENTITY), userId));
       return entityManager.createQuery(query)
               .getResultList()
               .size();
    }
}
