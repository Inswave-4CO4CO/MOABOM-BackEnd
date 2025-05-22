package com.moabom.backend.user.controller;


import com.moabom.backend.auth.util.JwtUtil;
import com.moabom.backend.user.exception.MyPageException;
import com.moabom.backend.user.model.*;
import com.moabom.backend.user.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    //보는중 리스트(5개씩)
    @GetMapping("/watching")
    public MyPagedResultDTO<MyWatchDTO> getWatchingList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "ottNames", defaultValue = "") List<String> ottNames,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return MyPageService.getWatchContentList(
                userDetails.getUsername(), ottNames, "ING", page);
    }

    //봤다 리스트(5개씩)
    @GetMapping("/watched")
    public MyPagedResultDTO<MyWatchDTO> getWatchedList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "ottNames", defaultValue = "") List<String> ottNames,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return MyPageService.getWatchContentList(
                userDetails.getUsername(), ottNames, "ED", page);
    }

    //장르별 시청통계
    @GetMapping("/stats")
    public List<MyStatsDTO> getStatsList(@AuthenticationPrincipal UserDetails userDetails){
        return MyPageService.getStatsList(userDetails.getUsername());
    }

    //나의 댓글 조회(5개씩)
    @GetMapping("/comments")
    public MyPagedResultDTO<MyReviewDTO> getReviewList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "page", defaultValue = "0") int page ){
        return MyPageService.getReviewList(userDetails.getUsername(), page);
    }

    //보고싶다 + 보는중 개수
    @GetMapping("/count")
    public MyWatchCountDTO getWatchCount(@AuthenticationPrincipal UserDetails userDetails){
        return MyPageService.getWatchCount(userDetails.getUsername());
    }
}
