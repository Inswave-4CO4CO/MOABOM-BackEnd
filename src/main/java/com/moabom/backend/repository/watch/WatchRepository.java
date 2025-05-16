package com.moabom.backend.repository.watch;

import com.moabom.backend.model.watch.WatchEntity;
import com.moabom.backend.model.watch.WatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends JpaRepository<WatchEntity, WatchId> {
}
