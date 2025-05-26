package com.moabom.backend.watch.service;

import com.moabom.backend.content.model.GenreDTO;
import com.moabom.backend.watch.model.StatsEntity;
import com.moabom.backend.watch.model.WatchDTO;
import com.moabom.backend.watch.model.WatchEntity;
import com.moabom.backend.watch.model.WatchId;
import com.moabom.backend.watch.repository.StatsRepository;
import com.moabom.backend.watch.repository.WatchRepository;
import com.moabom.backend.watch.type.WatchEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WatchService {
    private final WatchRepository watchRepository;
    private final StatsRepository statsRepository;

    @Autowired
    public WatchService(WatchRepository watchRepository, StatsRepository statsRepository) {
        this.watchRepository = watchRepository;
        this.statsRepository = statsRepository;
    }


    //시청상태 추가
    @Transactional
    public WatchEntity insert(WatchDTO watchDTO) {
        WatchEntity watchEntity = new WatchEntity();
        watchEntity.setUserId(watchDTO.getUserId());
        watchEntity.setContentId(watchDTO.getContentId());
        watchEntity.setType(watchDTO.getType());

        WatchId watchId = new WatchId(watchEntity.getUserId(), watchEntity.getContentId());

        if (watchRepository.existsById(watchId)) {
            throw new IllegalStateException(("[시청상태 추가] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 이미 존재합니다"));
        }

        WatchEntity savedWatch = watchRepository.save(watchEntity);

        if (watchEntity.getType() != WatchEnum.WANT) {
            for (GenreDTO genre : watchDTO.getGenre()) {
                statsRepository.findByUserIdAndGenreId(watchEntity.getUserId(), genre.getGenreId())
                        .ifPresentOrElse(
                                existing -> {
                                    existing.setCount(existing.getCount() + 1);
                                    statsRepository.save(existing);
                                },
                                () -> {
                                    StatsEntity newStats = new StatsEntity();
                                    newStats.setUserId(watchEntity.getUserId());
                                    newStats.setGenreId(genre.getGenreId());
                                    newStats.setCount(1);
                                    statsRepository.save(newStats);
                                }
                        );
            }
        }

        return savedWatch;
    }


    //시청상태 수정
    @Transactional
    public WatchEntity update(WatchDTO watchDTO) {
        WatchEntity watchEntity = new WatchEntity();
        watchEntity.setUserId(watchDTO.getUserId());
        watchEntity.setContentId(watchDTO.getContentId());
        watchEntity.setType(watchDTO.getType());

        WatchId watchId = new WatchId(watchEntity.getUserId(), watchEntity.getContentId());

        // watchId로 존재하는지 확인, 없으면 예외 던지기
        if (!watchRepository.existsById(watchId)) {
            throw new IllegalArgumentException("[시청상태 수정] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 존재하지 않습니다");
        }

        Optional<WatchEntity> watch = watchRepository.findById(watchId);


        if (watch.get().getType()==WatchEnum.WANT&&(watchEntity.getType()==WatchEnum.ED || watchEntity.getType()==WatchEnum.ING)) {
            for (GenreDTO genre : watchDTO.getGenre()) {
                statsRepository.findByUserIdAndGenreId(watchEntity.getUserId(), genre.getGenreId())
                        .ifPresentOrElse(
                                existing -> {
                                    existing.setCount(existing.getCount() + 1);
                                    statsRepository.save(existing);
                                },
                                () -> {
                                    StatsEntity newStats = new StatsEntity();
                                    newStats.setUserId(watchEntity.getUserId());
                                    newStats.setGenreId(genre.getGenreId());
                                    newStats.setCount(1);
                                    statsRepository.save(newStats);
                                }
                        );
            }
        }else if(watchEntity.getType()==WatchEnum.WANT&&(watch.get().getType()==WatchEnum.ED || watch.get().getType()==WatchEnum.ING)){
            for (GenreDTO genre : watchDTO.getGenre()) {
                statsRepository.findByUserIdAndGenreId(watchEntity.getUserId(), genre.getGenreId())
                        .ifPresent(existing -> {
                            int newCount = existing.getCount() - 1;

                            if (newCount <= 0) {
                                statsRepository.delete(existing);
                            } else {
                                existing.setCount(newCount);
                                statsRepository.save(existing);
                            }
                        });
            }
        }

        WatchEntity savedWatch = watchRepository.save(watchEntity);

        return savedWatch;
    }

    //시청상태 삭제
    @Transactional
    public void delete(WatchDTO watchDTO) {
        WatchEntity watchEntity = new WatchEntity();
        watchEntity.setUserId(watchDTO.getUserId());
        watchEntity.setContentId(watchDTO.getContentId());
        watchEntity.setType(watchDTO.getType());

        WatchId watchId = new WatchId(watchEntity.getUserId(), watchEntity.getContentId());

        //삭제하려는 Watch 엔티티가 존재하는지 확인, 없으면 예외 던지기
        if (!watchRepository.existsById(watchId)) {
            throw new IllegalArgumentException("[시청상태 삭제] watchId (userId=" + watchId.getUserId() + ", contentId=" + watchId.getContentId() + ") 가 존재하지 않습니다");
        }

        if(watchEntity.getType()!=WatchEnum.WANT){
            for (GenreDTO genre : watchDTO.getGenre()) {
                statsRepository.findByUserIdAndGenreId(watchEntity.getUserId(), genre.getGenreId())
                        .ifPresent(existing -> {
                            int newCount = existing.getCount() - 1;

                            if (newCount <= 0) {
                                statsRepository.delete(existing);
                            } else {
                                existing.setCount(newCount);
                                statsRepository.save(existing);
                            }
                        });
            }
        }

        watchRepository.deleteById(watchId);
    }
}
