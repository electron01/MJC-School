package by.epam.esm.query;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.enity.User;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Component
public class UserQueryBuilder extends BaseQueryBuilder<User> {
    @Override
    public CriteriaQuery<User> createCriteriaQuery(Map<String, String[]> params, CriteriaBuilder criteriaBuilder) {
        getPredicateList().clear();
        setCriteriaBuilder(criteriaBuilder);
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        addParamRemoveFalseToQuery(root);
        addParams(params, root, criteriaQuery);
        criteriaQuery.select(root).where(getPredicateList().toArray(Predicate[]::new));
        return criteriaQuery;
    }

    @Override
    public List<String> getTableColumns() {
        return List.of(RepoConstant.ENTITY_ID, RepoConstant.USER_LOGIN, RepoConstant.USER_EMAIL);
    }

    private void addParams(Map<String, String[]> params, Root root, CriteriaQuery criteriaQuery) {
        for (String paramKey : params.keySet()) {
            if (paramKey.equals(RepoConstant.PAGE)
                    || paramKey.equals(RepoConstant.START_POSITION)
                    || paramKey.equals(RepoConstant.LIMIT_PARAM)) {
                continue;
            } else if (paramKey.equals(RepoConstant.SORT)) {
                addSortToQuery(criteriaQuery, root, params.get(paramKey));
            } else {
                addParamToQuery(params.get(paramKey)[0], root.get(paramKey), paramKey);
            }

        }
    }
}
