package by.epam.esm.dao;

import java.util.List;

public interface GiftCertificateTagDao {
    void deleteCertificateTags(Long certificateId);

    void addCertificateTags(Long certificateId, List<Long> tagsId);
}
