package by.epam.esm.dao.impl;

import by.epam.esm.constant.RepoConstant;
import by.epam.esm.dao.GiftCertificateTagDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteCertificateTags(Long certificateId) {
        Query query = entityManager.createNativeQuery(RepoConstant.DELETE_TAG_GIFT_CERTIFICATE)
                .setParameter(1, certificateId);
        query.executeUpdate();
    }


    @Override
    public void addCertificateTags(Long certificateId, List<Long> tagsId) {
        if (tagsId != null) {
            for (Long tagId : tagsId) {
                Query query = entityManager.createNativeQuery(RepoConstant.UPDATE_TAG_GIFT_CERTIFICATE)
                        .setParameter(1, certificateId)
                        .setParameter(2, tagId);
                query.executeUpdate();
            }
        }
    }
}
