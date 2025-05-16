package com.moabom.backend.repository.content;

import com.moabom.backend.model.content.CrewDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CrewRepository {
    @PersistenceContext
    private EntityManager em;

    public List<CrewDTO> getCrewInfoByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_crew_info_by_content_id(:contentId)", "CrewDTOMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }
}