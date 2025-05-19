package com.moabom.backend.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.moabom.backend.main.service.MainPageService;

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