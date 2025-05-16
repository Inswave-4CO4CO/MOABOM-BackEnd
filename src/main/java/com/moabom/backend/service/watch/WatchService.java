package com.moabom.backend.service.watch;

import com.moabom.backend.model.watch.WatchEntity;
import com.moabom.backend.model.watch.WatchId;
import com.moabom.backend.repository.watch.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchService {
    private final WatchRepository watchRepository;

    @Autowired
    public WatchService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    public void insert(WatchEntity watchEntity) {
        watchRepository.save(watchEntity);
    }

    public void update(WatchEntity watchEntity) {
        watchRepository.save(watchEntity);
    }

    public void delete(WatchId watchId) {
        watchRepository.deleteById(watchId);
    }
}
