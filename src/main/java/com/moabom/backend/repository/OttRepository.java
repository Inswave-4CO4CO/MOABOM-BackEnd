package com.moabom.backend.repository;

import com.moabom.backend.model.OttDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OttRepository {

    @PersistenceContext
    private EntityManager em;

    public List<OttDTO> getOttInfoByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_ott_info_by_content_id(:contentId)", "OttEntityMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }
}
