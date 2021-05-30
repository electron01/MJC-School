package by.epam.esm.query;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.enity.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Component
public class TagQueryBuilder extends BaseQueryBuilder<Tag> {

    @Override
    public CriteriaQuery<Tag> createCriteriaQuery(Map<String, String[]> params, CriteriaBuilder criteriaBuilder) {
        getPredicateList().clear();
        setCriteriaBuilder(criteriaBuilder);
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        addParamRemoveFalseToQuery(root);
        addParams(params, root, criteriaQuery);
        criteriaQuery.select(root).where(getPredicateList().toArray(Predicate[]::new));
        criteriaQuery.distinct(true);
        return criteriaQuery;
    }

    @Override
    public List<String> getTableColumns() {
        return List.of(RepoConstant.ENTITY_ID, RepoConstant.ENTITY_NAME);
    }


    private void addParams(Map<String, String[]> params, Root root, CriteriaQuery criteriaQuery) {
        for (String paramKey : params.keySet()) {
            if (paramKey.equals(RepoConstant.LIMIT_PARAM)
                    || paramKey.equals(RepoConstant.START_POSITION)
                    || paramKey.equals(RepoConstant.PAGE)) {
                continue;
            } else if (paramKey.equals(RepoConstant.SORT)) {
                addSortToQuery(criteriaQuery, root, params.get(paramKey));
            } else {
                addParamToQuery(params.get(paramKey)[0], root.get(paramKey), paramKey);
            }
        }
    }

}
