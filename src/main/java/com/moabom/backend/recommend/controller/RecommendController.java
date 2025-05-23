package com.moabom.backend.recommend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moabom.backend.recommend.model.RecommendRequestDto;
import com.moabom.backend.recommend.model.RecommendResponseDto;
import com.moabom.backend.recommend.service.RecommendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/ott")
    public RecommendResponseDto recommend(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RecommendRequestDto requestDto) {
        String userId = userDetails.getUsername();
        return recommendService.getOttRecommendations(userId, requestDto);
    }
}

