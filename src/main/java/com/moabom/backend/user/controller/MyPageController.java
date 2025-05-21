package com.moabom.backend.user.controller;


import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.user.exception.MyPageException;
import com.moabom.backend.user.model.*;
import com.moabom.backend.user.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MyPageController {
    private final MyPageService MyPageService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MyPageController(MyPageService MyPageService, JwtUtil jwtUtil){
        this.MyPageService = MyPageService;
        this.jwtUtil = jwtUtil;
    }

    //토큰 리턴하는 거
    private String extractUserIdOrThrow(String token) {
        String realToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        if (realToken.isEmpty()) {
            throw new MyPageException("유효하지 않은 사용자 토큰입니다.");
        }
        return jwtUtil.extractUserId(realToken);
    }

    //보는중 리스트(5개씩)
    @GetMapping("/watching")
    public MyPagedResultDTO<MyWatchDTO> getWatchingList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestBody MyOttDTO ottList){
        String userId = extractUserIdOrThrow(userIdAuth);
        return MyPageService.getWatchContentList(userId, ottList.getOttNames(), "ING", ottList.getPage());
    }

    //봤다 리스트(5개씩)
    @GetMapping("/watched")
    public MyPagedResultDTO<MyWatchDTO> getWatchedList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestBody MyOttDTO ottList){
        String userId = extractUserIdOrThrow(userIdAuth);
        return MyPageService.getWatchContentList(userId, ottList.getOttNames(), "ED", ottList.getPage());
    }

    //장르별 시청통계
    @GetMapping("/stats")
    public List<MyStatsDTO> getStatsList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth){
        String userId = extractUserIdOrThrow(userIdAuth);
        return MyPageService.getStatsList(userId);
    }

    //나의 댓글 조회(5개씩)
    @GetMapping("/comments")
    public MyPagedResultDTO<MyReviewDTO> getReviewList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestParam(value = "page", defaultValue = "0") int page ){
        String userId = extractUserIdOrThrow(userIdAuth);
        return MyPageService.getReviewList(userId, page);
    }

    //보고싶다 + 보는중 개수
    @GetMapping("/count")
    public MyWatchCountDTO getWatchCount(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth){
        String userId = extractUserIdOrThrow(userIdAuth);
        return MyPageService.getWatchCount(userId);
    }
}
