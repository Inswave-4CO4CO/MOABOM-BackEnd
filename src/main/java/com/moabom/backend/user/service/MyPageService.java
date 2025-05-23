package com.moabom.backend.user.service;

import com.moabom.backend.user.model.*;
import com.moabom.backend.user.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        List<String> defaultOttList = Arrays.asList("넷플릭스", "티빙", "Apple TV", "U+모바일tv", "디즈니+", "라프텔", "왓챠", "웨이브", "쿠팡플레이");

        String ottListForSql = (ottList == null || ottList.isEmpty() ? defaultOttList : ottList)
                .stream()
                .map(ott -> "'" + ott + "'")
                .collect(Collectors.joining(","));


        MyPagedResultDTO<MyWatchDTO> myContentList = watchRepository.getContentByUserAndOttList(userId, ottListForSql, type, page, 12);

        return myContentList;
    }


    //장르별 시청 통계 가져오기
    public List<MyStatsDTO>  getStatsList(String userId) {
        return watchRepository.getStatsByUserId(userId);
    }


    //리뷰 리스트 가져오기
    public MyPagedResultDTO<MyReviewDTO> getReviewList(String userId, int page) {
        return watchRepository.getReviewByUserId(userId, page, 8);
    }

    //보는중 + 보고싶다 총 개수
    public MyWatchCountDTO getWatchCount(String userId) {
        return watchRepository.getWatchCount(userId);
    }

}
