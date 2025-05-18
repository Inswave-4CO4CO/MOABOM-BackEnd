package com.moabom.backend.content.repository;

import com.moabom.backend.content.model.CastDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CastRepository {
    @PersistenceContext
    private EntityManager em;

    public List<CastDTO> getCastInfoByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_cast_info_by_content_id(:contentId)", "CastDTOMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }
}
