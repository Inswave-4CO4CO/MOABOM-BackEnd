package com.moabom.backend.repository.content;

import com.moabom.backend.model.content.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {
}
