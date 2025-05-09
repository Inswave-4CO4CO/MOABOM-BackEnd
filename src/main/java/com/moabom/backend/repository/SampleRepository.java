package com.moabom.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moabom.backend.model.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
