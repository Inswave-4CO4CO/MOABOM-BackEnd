package com.moabom.backend.watch.service;

import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchService {
    private final WatchRepository watchRepository;

    @Autowired
    public WatchService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }


    //시청상태 추가
    public WatchEntity insert(WatchEntity watchEntity) {
        WatchId watchId = new WatchId(watchEntity.getUserId(), watchEntity.getContentId());

        // watchId로 이미 존재하는지 확인 후 IllegalStateException 던지기
        if (watchRepository.existsById(watchId)) {
            throw new IllegalStateException("[시청상태 추가] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 이미 존재합니다");
        }
        try {
            return watchRepository.save(watchEntity);
        } catch (Exception e) {
            // DB 저장 중 문제 발생 시 예외 던지기
            throw new RuntimeException("[시청상태 추가] 시청상태 추가를 실패했습니다", e);
        }
    }

    //시청상태 수정
    public WatchEntity update(WatchEntity watchEntity) {
        WatchId watchId = new WatchId(watchEntity.getUserId(), watchEntity.getContentId());

        // watchId로 존재하는지 확인, 없으면 예외 던지기
        if (!watchRepository.existsById(watchId)) {
            throw new IllegalArgumentException("[시청상태 수정] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 존재하지 않습니다");
        }
        try {
            return watchRepository.save(watchEntity);
        } catch (Exception e) {
            // DB 업데이트 중 문제 발생 시 예외 던지기
            throw new RuntimeException("[시청상태 수정] 시청상태 수정을 실패했습니다", e);
        }
    }

    //시청상태 삭제
    public void delete(WatchId watchId) {
        //삭제하려는 Watch 엔티티가 존재하는지 확인, 없으면 예외 던지기
        if (!watchRepository.existsById(watchId)) {
            throw new IllegalArgumentException("[시청상태 삭제] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 존재하지 않습니다");
        }
        try {
            watchRepository.deleteById(watchId);
        } catch (Exception e) {
            //DB 삭제 중 문제 발생 시 예외 던지기
            throw new RuntimeException("[시청상태 삭제] 시청상태 삭제를 실패했습니다", e);
        }
    }
}
