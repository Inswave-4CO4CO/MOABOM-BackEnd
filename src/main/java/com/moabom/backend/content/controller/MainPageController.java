package com.moabom.backend.content.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.moabom.backend.content.service.MainPageService;

import java.util.*;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping
    public Map<String, Object> getMainContents() {
        return mainPageService.getMainContents();
    }
}