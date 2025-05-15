package com.moabom.backend.repository;
import com.moabom.backend.model.CastAndCrewDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CastAndCrewRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CastAndCrewDTO> getCastInfoByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_cast_info_by_content_id(:contentId)", "CastAndCrewEntityMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }

    public List<CastAndCrewDTO> getCrewInfoByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_crew_info_by_content_id(:contentId)", "CastAndCrewEntityMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }
}