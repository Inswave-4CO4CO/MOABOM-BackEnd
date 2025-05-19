package com.moabom.backend.content.repository;

import com.moabom.backend.content.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<ContentEntity, Integer> {
}
