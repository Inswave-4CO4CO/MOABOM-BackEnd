package com.moabom.backend.auth.repository;

import com.moabom.backend.auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
