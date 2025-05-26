package com.moabom.backend.watch.repository;

import com.moabom.backend.watch.model.StatsEntity;
import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<StatsEntity, Integer> {
    Optional<StatsEntity> findByUserIdAndGenreId(String userId, Integer genreId);
}
