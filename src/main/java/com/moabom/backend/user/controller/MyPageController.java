package com.moabom.backend.user.controller;


import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.user.model.*;
import com.moabom.backend.user.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //보는중 리스트(5개씩)
    @GetMapping("/watching")
    public PagedResultDTO<MyWatchDTO> getWatchingList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestBody MyOttDTO ottList){
        String token = userIdAuth.startsWith("Bearer ") ? userIdAuth.substring(7).trim() : userIdAuth.trim();
        String userId = token.isEmpty() || token=="" ? "" : jwtUtil.extractUserId(token);

        return MyPageService.getWatchContentList(userId, ottList.getOttNames(), "ING", ottList.getPage());
    }

    //봤다 리스트(5개씩)
    @GetMapping("/watched")
    public PagedResultDTO<MyWatchDTO> getWatchedList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestBody MyOttDTO ottList){
        String token = userIdAuth.startsWith("Bearer ") ? userIdAuth.substring(7).trim() : userIdAuth.trim();
        String userId = token.isEmpty() || token=="" ? "" : jwtUtil.extractUserId(token);

        return MyPageService.getWatchContentList(userId, ottList.getOttNames(), "ED", ottList.getPage());
    }

    //장르별 시청통계
    @GetMapping("/stats")
    public List<MyStatsDTO> getStatsList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth){
        String token = userIdAuth.startsWith("Bearer ") ? userIdAuth.substring(7).trim() : userIdAuth.trim();
        String userId = token.isEmpty() || token=="" ? "" : jwtUtil.extractUserId(token);

        return MyPageService.getStatsList(userId);
    }

    //나의 댓글 조회(5개씩)
    @GetMapping("/comments")
    public PagedResultDTO<MyReviewDTO> getReviewList(@RequestHeader(value = "Authorization", defaultValue = "") String userIdAuth, @RequestParam(value = "page", defaultValue = "0") int page ){
        String token = userIdAuth.startsWith("Bearer ") ? userIdAuth.substring(7).trim() : userIdAuth.trim();
        String userId = token.isEmpty() || token=="" ? "" : jwtUtil.extractUserId(token);

        return MyPageService.getReviewList(userId, page);
    }
}
