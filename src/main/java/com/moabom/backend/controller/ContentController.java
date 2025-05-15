package com.moabom.backend.controller;

import com.moabom.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/content/{id}")
    public Map<String, Object> getContentById(@PathVariable("id") int id) {
        Map<String, Object> contentDetailMap = new HashMap<>();
        contentDetailMap = contentService.getContentById(id);
        return contentDetailMap;
    }
}
