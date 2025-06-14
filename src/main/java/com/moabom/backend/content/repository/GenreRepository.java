package com.moabom.backend.content.repository;

import com.moabom.backend.content.model.GenreDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepository {

    @PersistenceContext
    private EntityManager em;

    public List<GenreDTO> getGenreByContentId(int contentId) {
        return em.createNativeQuery(
                        "CALL get_genres_by_content_id(:contentId)", "GenreEntityMapping")
                .setParameter("contentId", contentId)
                .getResultList();
    }
}
