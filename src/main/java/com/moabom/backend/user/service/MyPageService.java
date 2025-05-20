package com.moabom.backend.user.service;

import com.moabom.backend.user.model.MyReviewDTO;
import com.moabom.backend.user.model.MyStatsDTO;
import com.moabom.backend.user.model.MyWatchDTO;
import com.moabom.backend.user.model.MyPagedResultDTO;
import com.moabom.backend.user.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {
    private final MyPageRepository watchRepository;

    @Autowired
    public MyPageService(MyPageRepository watchRepository){
        this.watchRepository = watchRepository;
    }

    //시청기록 '봤다'/'보는중' 에서 사용자 컨텐츠 목록 가져오기
    public MyPagedResultDTO<MyWatchDTO> getWatchContentList(String userId, List<String> ottList, String type, int page) {
        String ottListForSql = ottList.stream()
                .map(ott -> "'" + ott + "'")
                .collect(Collectors.joining(","));

        MyPagedResultDTO<MyWatchDTO> myContentList = watchRepository.getContentByUserAndOttList(userId, ottListForSql, type, page, 5);

        return myContentList;
    }


    //장르별 시청 통계 가져오기
    public List<MyStatsDTO>  getStatsList(String userId) {
        return watchRepository.getStatsByUserId(userId);
    }


    //리뷰 리스트 가져오기
    public MyPagedResultDTO<MyReviewDTO> getReviewList(String userId, int page) {
        return watchRepository.getReviewByUserId(userId, page, 5);
    }
}
