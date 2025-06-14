// SearchPageController.java
package com.moabom.backend.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moabom.backend.search.model.SearchContentDto;
import com.moabom.backend.search.model.SearchPersonDto;
import com.moabom.backend.search.model.SearchRequestDto;
import com.moabom.backend.search.model.SearchResultDto;
import com.moabom.backend.search.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchPageController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Object search(@ModelAttribute SearchRequestDto req) {
        Pageable pg = PageRequest.of(req.getPage(), req.getSize());
        SearchResultDto resp = new SearchResultDto();
        
        switch (req.getType()) {
            case "cast":
                Page<SearchPersonDto> castPage = searchService.searchCastByName(req.getKeyword(), pg);
                resp.setCast(castPage.getContent());
                resp.setHasNext(castPage.hasNext());
                return resp;
            case "crew":
                Page<SearchPersonDto> crewPage = searchService.searchCrewByName(req.getKeyword(), pg);
                resp.setCrew(crewPage.getContent());
                resp.setHasNext(crewPage.hasNext());
                return resp;

            default:
                Sort sortObj = switch (req.getSort()) {
                    case "latest" -> Sort.by(Sort.Direction.DESC, "releaseDate", "contentId");
                    case "oldest" -> Sort.by(Sort.Direction.ASC,  "releaseDate", "contentId");
                    default       -> Sort.by(Sort.Direction.DESC, "rating",      "contentId");
                };
                Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sortObj);
                Page<SearchContentDto> page = searchService.searchWithFilters(
                    req.getKeyword(),
                    req.getGenres(),
                    req.getOtts(),
                    req.getCategories(),
                    pageable
                );
                boolean isFirst = req.getPage() == 0
                    && req.getAfterRating() == null
                    && req.getAfterDate()   == null
                    && req.getAfterId()     == null;
                
                resp.setContent(page.getContent());
                resp.setHasNext(page.hasNext());
                if (isFirst) {
                    resp.setAllGenres(searchService.getAllGenres());
                    resp.setAllCategories(searchService.getAllCategories());
                }
                return resp;
        }
    }
}
