package com.moabom.backend.controller.content;

import com.moabom.backend.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ContentController {
    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/content/{contentId}")
    public Map<String, Object> getContentById(@PathVariable("contentId") int contentId, @RequestParam("userId") String userId) {
        Map<String, Object> contentDetailMap = new HashMap<>();
        contentDetailMap = contentService.getContentById(contentId, userId);
        return contentDetailMap;
    }
}
