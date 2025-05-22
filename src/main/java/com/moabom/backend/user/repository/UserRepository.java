package com.moabom.backend.user.repository;

import com.moabom.backend.auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);
}
