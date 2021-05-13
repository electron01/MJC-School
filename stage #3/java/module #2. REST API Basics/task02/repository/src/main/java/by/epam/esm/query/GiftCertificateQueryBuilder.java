package by.epam.esm.query;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.enity.GiftCertificate;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateQueryBuilder extends BaseQueryBuilder<GiftCertificate> {
    @Override
    public CriteriaQuery<GiftCertificate> createCriteriaQuery(Map<String, String[]> params, CriteriaBuilder criteriaBuilder) {
        setCriteriaBuilder(criteriaBuilder);
        getPredicateList().clear();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        addParamRemoveFalseToQuery(root);
        addParams(params, root, criteriaQuery);
        criteriaQuery.select(root).where(getPredicateList().toArray(Predicate[]::new));
        return criteriaQuery;
    }

    private void addParams(Map<String, String[]> params, Root<GiftCertificate> root, CriteriaQuery criteriaQuery) {
        for (String paramKey : params.keySet()) {//pagination
            if (paramKey.equals(RepoConstant.LIMIT_PARAM)
                    || paramKey.equals(RepoConstant.START_POSITION)
                    || paramKey.equals(RepoConstant.PAGE)) {
                continue;

            } else if (paramKey.equals(RepoConstant.SORT)) {//sort
                addSortToQuery(criteriaQuery, root, params.get(paramKey));

            } else if (paramKey.equals(RepoConstant.TAGS_NAME)) {
                addTagNames(root, params.get(paramKey));

            } else if (paramKey.equals(RepoConstant.CERTIFICATE_CREATE_DATE)//equal date type or integer
                    || paramKey.equals(RepoConstant.CERTIFICATE_CREATE_DATE)
                    || paramKey.equals(RepoConstant.CERTIFICATE_PRICE)
                    || paramKey.equals(RepoConstant.CERTIFICATE_DURATION)
            ) {
                addEqualParamToQuery(params.get(paramKey)[0], root.get(paramKey), paramKey);
            } else {
                addParamToQuery(params.get(paramKey)[0], root.get(paramKey), paramKey);  //like for string (name,tags,description)
            }

        }
    }

    private void addTagNames(Root<GiftCertificate> root, String[] tagNames) {
        if (tagNames == null || tagNames.length == 0) {
            return;
        }
        for (String tagName : tagNames) {
            addJoinParamToQuery(tagName, root, RepoConstant.TAGS_NAME, RepoConstant.ENTITY_NAME);
        }


    }

    @Override
    public List<String> getTableColumns() {
        return List.of(RepoConstant.ENTITY_ID,
                RepoConstant.ENTITY_NAME,
                RepoConstant.TAGS_NAME,
                RepoConstant.CERTIFICATE_DESCRIPTION,
                RepoConstant.CERTIFICATE_PRICE,
                RepoConstant.CERTIFICATE_DURATION,
                RepoConstant.CERTIFICATE_CREATE_DATE,
                RepoConstant.CERTIFICATE_UPDATE_DATE);
    }
}
